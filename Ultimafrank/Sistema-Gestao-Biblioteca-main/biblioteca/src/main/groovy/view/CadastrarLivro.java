package view;

import controller.LivroController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CadastrarLivro {
    private JPanel PainelCadastro;
    private JTextField textTitulo;
    private JTextField textTema;
    private JTextField textAutor;
    private JTextField textISBN;
    private JButton CadastroButton;
    private JTextField textPublicacao;
    private JTextField textQuantidade;
    private LivroController livroController;

    private static final String TITULO_PLACEHOLDER = "Titulo ";
    private static final String TEMA_PLACEHOLDER = "Tema/Gênero";
    private static final String AUTOR_PLACEHOLDER = "Nome do Autor";
    private static final String ISBN_PLACEHOLDER = "ISBN (10 ou 13 digitos)";
    private static final String PUBLICACAO_PLACEHOLDER = "dd/MM/yyyy";
    private static final String QUANTIDADE_PLACEHOLDER = "Quantidade disponivel";

    private static final Color COR_PLACEHOLDER = Color.GRAY;
    private static final Color COR_NORMAL = Color.BLACK;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CadastrarLivro() {
        this.livroController = new LivroController();

        adicionarPlaceholder(textTitulo, TITULO_PLACEHOLDER);
        adicionarPlaceholder(textTema, TEMA_PLACEHOLDER);
        adicionarPlaceholder(textAutor, AUTOR_PLACEHOLDER);
        adicionarPlaceholder(textISBN, ISBN_PLACEHOLDER);
        adicionarPlaceholder(textPublicacao, PUBLICACAO_PLACEHOLDER);
        adicionarPlaceholder(textQuantidade, QUANTIDADE_PLACEHOLDER);

        CadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarLivro();
            }
        });
    }
    public static void adicionarPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(COR_PLACEHOLDER);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(COR_NORMAL);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(COR_PLACEHOLDER);
                }
            }
        });
    }
    private void salvarLivro() {
        String titulo = textTitulo.getText();
        String tema = textTema.getText();
        String autor = textAutor.getText();
        String isbn = textISBN.getText();
        String dataStr = textPublicacao.getText();
        String qtdStr = textQuantidade.getText();

        Long id = null;
        LocalDate dataPublicacao;
        int qtdDisponivel;
        if (titulo.equals(TITULO_PLACEHOLDER)) titulo = "";
        if (tema.equals(TEMA_PLACEHOLDER)) tema = "";
        if (autor.equals(AUTOR_PLACEHOLDER)) autor = "";
        if (isbn.equals(ISBN_PLACEHOLDER)) isbn = "";
        if (dataStr.equals(PUBLICACAO_PLACEHOLDER)) dataStr = "";
        if (qtdStr.equals(QUANTIDADE_PLACEHOLDER)) qtdStr = "";

        if (titulo.trim().isEmpty() || autor.trim().isEmpty() || isbn.trim().isEmpty() || dataStr.trim().isEmpty() || qtdStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(PainelCadastro, "Todos os campos são obrigatórios.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            qtdDisponivel = Integer.parseInt(qtdStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(PainelCadastro, "A Quantidade deve ser um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            dataPublicacao = LocalDate.parse(dataStr, dateFormatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(PainelCadastro, " Formato da Data de Publicação inválido.\nUse o formato dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String mensagemConfirmacao = String.format(
                "Por favor, confirme os dados do livro:\n\n" +
                        "Título: %s\n" +
                        "Autor: %s\n" +
                        "Tema: %s\n" +
                        "ISBN: %s\n" +
                        "Publicação: %s\n" +
                        "Quantidade: %d\n\n" +
                        "Deseja salvar este livro?",
                titulo, autor, tema, isbn, dataStr, qtdDisponivel
        );

        int resposta = JOptionPane.showConfirmDialog(
                PainelCadastro,
                mensagemConfirmacao,
                "Confirmar Cadastro",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resposta == JOptionPane.YES_OPTION) {
            boolean sucesso = livroController.salvarLivro(id, titulo, tema, autor, isbn, dataPublicacao, qtdDisponivel);

            if (sucesso) {
                JOptionPane.showMessageDialog(PainelCadastro, "Livro salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(PainelCadastro, "Erro ao salvar livro.\nVerifique se os campos estão corretos ou se o ISBN já foi cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        textTitulo.setText(TITULO_PLACEHOLDER);
        textTitulo.setForeground(COR_PLACEHOLDER);

        textTema.setText(TEMA_PLACEHOLDER);
        textTema.setForeground(COR_PLACEHOLDER);

        textAutor.setText(AUTOR_PLACEHOLDER);
        textAutor.setForeground(COR_PLACEHOLDER);

        textISBN.setText(ISBN_PLACEHOLDER);
        textISBN.setForeground(COR_PLACEHOLDER);

        textPublicacao.setText(PUBLICACAO_PLACEHOLDER);
        textPublicacao.setForeground(COR_PLACEHOLDER);

        textQuantidade.setText(QUANTIDADE_PLACEHOLDER);
        textQuantidade.setForeground(COR_PLACEHOLDER);
    }

    public JPanel getPainelCadastro() {
        return PainelCadastro;
    }
}