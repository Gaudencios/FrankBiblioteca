package view;


import controller.EmprestimoController;
import controller.LivroController;
import model.EmprestimoModel;
import model.LivroModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListaLivros {
    private JPanel PaneListar;
    private JTable tableDisponiveis;
    private JTable tableEmprestados;


    private LivroController livroController;
    private EmprestimoController emprestimoController;


    private DefaultTableModel disponiveisTableModel;
    private DefaultTableModel emprestadosTableModel;


    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ListaLivros() {
        this.livroController = new LivroController();
        this.emprestimoController = new EmprestimoController();


        configurarTabelas();


        carregarLivrosDisponiveis();
        carregarLivrosEmprestados();
    }

    private void configurarTabelas() {

        String[] colunasDisponiveis = {"ID Livro", "Título", "Autor", "Qtd. Disponível"};
        disponiveisTableModel = new DefaultTableModel(colunasDisponiveis, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableDisponiveis.setModel(disponiveisTableModel);

        String[] colunasEmprestados = {"ID Empr.", "Livro", "Aluno", "Data Empréstimo", "Data Prevista"};
        emprestadosTableModel = new DefaultTableModel(colunasEmprestados, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableEmprestados.setModel(emprestadosTableModel);
    }


    private void carregarLivrosDisponiveis() {
        List<LivroModel> livros = livroController.listarLivrosDisponiveis();


        disponiveisTableModel.setRowCount(0);

        for (LivroModel livro : livros) {
            disponiveisTableModel.addRow(new Object[]{
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getQtdDisponivel()
            });
        }
    }


    private void carregarLivrosEmprestados() {

        List<EmprestimoModel> emprestimos = emprestimoController.listarTodosEmprestimosAtivos();


        emprestadosTableModel.setRowCount(0);

        for (EmprestimoModel emprestimo : emprestimos) {
            emprestadosTableModel.addRow(new Object[]{
                    emprestimo.getId(),
                    emprestimo.getLivro().getTitulo(),
                    emprestimo.getAluno().getNome(),
                    emprestimo.getDataEmprestimo().format(dateFormatter),
                    emprestimo.getDataDevolucaoPrevista().format(dateFormatter)
            });
        }
    }

    public JPanel getPaneListar() {
        return PaneListar;
    }
}