package view;

import controller.AlunoController;
import controller.LivroController;
import controller.EmprestimoController;
import model.AlunoModel;
import model.LivroModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmprestarLivro {
    private JPanel PanelEmprestimo;
    private JTextField textBuscarAluno;
    private JButton buscarAlunoButton;
    private JTable tableAluno;
    private JTextField textBuscarLivro;
    private JButton buttonBuscarLivro;
    private JTable tableLivro;
    private JButton buttonRealizarEmprestimo;

    private AlunoController alunoController;
    private LivroController livroController;
    private EmprestimoController emprestimoController;

    private DefaultTableModel alunoTableModel;
    private DefaultTableModel livroTableModel;

    private AlunoModel alunoSelecionado = null;
    private LivroModel livroSelecionado = null;

    public EmprestarLivro() {
        this.alunoController = new AlunoController();
        this.livroController = new LivroController();
        this.emprestimoController = new EmprestimoController();

        configurarTabelas();

        textBuscarAluno.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e){
                buscarAlunos();
            }
            @Override
            public void removeUpdate(DocumentEvent e){
                buscarAlunos();
            }
            @Override
            public void changedUpdate(DocumentEvent e){
                buscarAlunos();
            }
        });

        textBuscarLivro.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e){
                buscarLivros();
            }
            @Override
            public void removeUpdate(DocumentEvent e){
                buscarLivros();
            }
            @Override
            public void changedUpdate(DocumentEvent e){}
        });


        buscarAlunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarAlunos();
            }
        });


        buttonBuscarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLivros();
            }
        });


        buttonRealizarEmprestimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarEmprestimo();
            }
        });


        tableAluno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tableAluno.getSelectedRow() != -1) {
                    selecionarAluno();
                }
            }
        });


        tableLivro.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tableLivro.getSelectedRow() != -1) {
                    selecionarLivro();
                }
            }
        });
    }

    private void configurarTabelas() {
        String[] colunasAluno = {"ID", "Nome", "Email", "Celular"};
        alunoTableModel = new DefaultTableModel(colunasAluno, 0);
        tableAluno.setModel(alunoTableModel);

        String[] colunasLivro = {"ID", "Título", "Autor", "Qtd. Disp."};
        livroTableModel = new DefaultTableModel(colunasLivro, 0);
        tableLivro.setModel(livroTableModel);
    }

    private void buscarAlunos() {
        String nome = textBuscarAluno.getText();
        List<AlunoModel> alunos = alunoController.buscarAlunosPorNome(nome);

        alunoTableModel.setRowCount(0);
        alunoSelecionado = null;

        for (AlunoModel aluno : alunos) {
            alunoTableModel.addRow(new Object[]{
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getEmail(),
                    aluno.getCelular()
            });
        }
    }

    private void buscarLivros() {
        String titulo = textBuscarLivro.getText();
        List<LivroModel> livros = livroController.buscarLivrosPorTitulo(titulo);

        livroTableModel.setRowCount(0);
        livroSelecionado = null;

        for (LivroModel livro : livros) {
            livroTableModel.addRow(new Object[]{
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getQtdDisponivel()
            });
        }
    }
    private void selecionarAluno() {
        try {
            int selectedRow = tableAluno.getSelectedRow();
            Long id = (Long) tableAluno.getValueAt(selectedRow, 0);
            this.alunoSelecionado = alunoController.buscarAlunoPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao selecionar aluno: " + e.getMessage());
            this.alunoSelecionado = null;
        }
    }
    private void selecionarLivro() {
        try {
            int selectedRow = tableLivro.getSelectedRow();
            Long id = (Long) tableLivro.getValueAt(selectedRow, 0);
            this.livroSelecionado = livroController.buscarLivroPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao selecionar livro: " + e.getMessage());
            this.livroSelecionado = null;
        }
    }

    private void realizarEmprestimo() {
        if (alunoSelecionado == null) {
            JOptionPane.showMessageDialog(PanelEmprestimo, "Por favor, selecione um aluno da lista.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (livroSelecionado == null) {
            JOptionPane.showMessageDialog(PanelEmprestimo, "Por favor, selecione um livro da lista.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultado = emprestimoController.realizarEmprestimo(alunoSelecionado, livroSelecionado);

        if (resultado.startsWith("Erro:")) {
            JOptionPane.showMessageDialog(PanelEmprestimo, resultado, "Falha no Empréstimo", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(PanelEmprestimo, resultado, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparSelecao();
            buscarLivros();
        }
    }
    private void limparSelecao() {
        this.alunoSelecionado = null;
        this.livroSelecionado = null;
        tableAluno.clearSelection();
        tableLivro.clearSelection();
    }

    public JPanel getPanelEmprestimo() {
        return PanelEmprestimo;
    }
}