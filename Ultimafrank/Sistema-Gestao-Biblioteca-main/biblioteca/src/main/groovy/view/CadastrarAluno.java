package view;

import controller.AlunoController;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

public class CadastrarAluno {
    private JTextField textNome;
    private JTextField textSexo;
    private JTextField textEmail;
    private JButton buttonCadastroAluno;
    private JPanel PainelAluno;
    private JFormattedTextField TextCelular;

    private AlunoController alunoController;

    private static final String NOME_PLACEHOLDER = "Digite o nome completo";
    private static final String SEXO_PLACEHOLDER = "Homem ou Mulher";
    private static final String EMAIL_PLACEHOLDER = "exemplo@Email.com";
    private static final Color COR_PLACEHOLDER = Color.GRAY;
    private static final Color COR_NORMAL = Color.BLACK;

    public CadastrarAluno() {
        this.alunoController = new AlunoController();

        try {
            MaskFormatter mascaraCelular = new MaskFormatter("(##) #####-####");
            mascaraCelular.setPlaceholderCharacter('_');
            mascaraCelular.install(TextCelular);
        } catch (ParseException e) {
            System.err.println("Erro ao criar a máscara de celular: " + e.getMessage());
        }

        adicionarPlaceholder(textNome, NOME_PLACEHOLDER);
        adicionarPlaceholder(textSexo, SEXO_PLACEHOLDER);
        adicionarPlaceholder(textEmail, EMAIL_PLACEHOLDER);

        buttonCadastroAluno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarAluno();
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
    private void salvarAluno() {
        String nome = textNome.getText();
        String sexo = textSexo.getText();
        String celularComMascara = TextCelular.getText();
        String email = textEmail.getText();
        Long id = null;

        if (nome.equals(NOME_PLACEHOLDER)) {
            nome = "";
        }
        if (sexo.equals(SEXO_PLACEHOLDER)) {
            sexo = "";
        }
        if (email.equals(EMAIL_PLACEHOLDER)) {
            email = "";
        }

        if (nome.trim().isEmpty() || sexo.trim().isEmpty() || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(PainelAluno, "Nome, Sexo e E-mail são obrigatórios.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (celularComMascara.contains("_")) {
            JOptionPane.showMessageDialog(PainelAluno, "Por favor, preencha o número de celular completo.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String celularSemMascara = celularComMascara.replaceAll("[^0-9]", "");

        String mensagemConfirmacao = String.format(
                "Por favor, confirme os dados do aluno:\n\n" +
                        "Nome: %s\n" +
                        "Sexo: %s\n" +
                        "Celular: %s\n" +
                        "E-mail: %s\n\n" +
                        "Deseja salvar este aluno?",
                nome, sexo, celularComMascara, email
        );
        int resposta = JOptionPane.showConfirmDialog(
                PainelAluno,
                mensagemConfirmacao,
                "Confirmar Cadastro",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (resposta == JOptionPane.YES_OPTION) {
            boolean sucesso = alunoController.salvarAluno(id, nome, sexo, celularSemMascara, email);

            if (sucesso) {
                JOptionPane.showMessageDialog(PainelAluno, "Aluno salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(PainelAluno, "Erro ao salvar aluno.\nVerifique se os campos estão corretos ou se o e-mail já foi cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void limparCampos() {
        textNome.setText(NOME_PLACEHOLDER);
        textNome.setForeground(COR_PLACEHOLDER);

        textSexo.setText(SEXO_PLACEHOLDER);
        textSexo.setForeground(COR_PLACEHOLDER);

        textEmail.setText(EMAIL_PLACEHOLDER);
        textEmail.setForeground(COR_PLACEHOLDER);

        TextCelular.setValue(null);
    }

    public JPanel getPainelCadastroAluno() {
        return PainelAluno;
    }
}