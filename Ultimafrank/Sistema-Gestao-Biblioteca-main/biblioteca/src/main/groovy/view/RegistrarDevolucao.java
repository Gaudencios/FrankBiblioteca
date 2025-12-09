package view;

import controller.EmprestimoController;
import model.EmprestimoModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RegistrarDevolucao {
    private JPanel PanelDevolucao;
    private JButton buttonAtualizarLista;
    private JTable tableDevolucao;
    private JButton devolucaoButton;
    private JTextField textBuscaEmprestimo;

    private EmprestimoController emprestimoController;
    private DefaultTableModel emprestimoTableModel;

    private EmprestimoModel emprestimoSelecionado = null;
    private List<EmprestimoModel> listaEmprestimosAtual = new ArrayList<>();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RegistrarDevolucao() {
        this.emprestimoController = new EmprestimoController();

        configurarTabela();


        carregarEmprestimosAtivos();

        devolucaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarDevolucao();
            }
        });


        buttonAtualizarLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarEmprestimosAtivos();
            }
        });

        tableDevolucao.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tableDevolucao.getSelectedRow() != -1) {
                    selecionarEmprestimo();
                }
            }
        });
    }



    private void configurarTabela() {
        String[] colunas = {"ID Empr.", "Livro", "Aluno", "Data Empréstimo", "Data Prevista"};
        emprestimoTableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableDevolucao.setModel(emprestimoTableModel);
    }


    private void carregarEmprestimosAtivos() {

        String nomeAluno = textBuscaEmprestimo.getText();


        this.listaEmprestimosAtual = emprestimoController.buscarEmprestimosAtivos(nomeAluno);

        emprestimoTableModel.setRowCount(0);
        emprestimoSelecionado = null;

        for (EmprestimoModel emprestimo : listaEmprestimosAtual) {
            emprestimoTableModel.addRow(new Object[]{
                    emprestimo.getId(),
                    emprestimo.getLivro().getTitulo(),
                    emprestimo.getAluno().getNome(),
                    emprestimo.getDataEmprestimo().format(dateFormatter),
                    emprestimo.getDataDevolucaoPrevista().format(dateFormatter)
            });
        }
    }

    private void selecionarEmprestimo() {
        try {
            int selectedRow = tableDevolucao.getSelectedRow();
            Long id = (Long) tableDevolucao.getValueAt(selectedRow, 0);

            for(EmprestimoModel emp : this.listaEmprestimosAtual){
                if(emp.getId().equals(id)){
                    this.emprestimoSelecionado = emp;
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao selecionar empréstimo: " + e.getMessage());
            this.emprestimoSelecionado = null;
        }
    }

    private void registrarDevolucao() {
        if (emprestimoSelecionado == null) {
            JOptionPane.showMessageDialog(PanelDevolucao, "Por favor, selecione um empréstimo da lista para devolver.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int avariaResult = JOptionPane.showConfirmDialog(
                PanelDevolucao,
                "O livro está sendo devolvido com avarias (danificado)?",
                "Verificação de Avarias",
                JOptionPane.YES_NO_OPTION
        );
        boolean comAvarias = (avariaResult == JOptionPane.YES_OPTION);


        String resultado = emprestimoController.registrarDevolucao(emprestimoSelecionado, comAvarias);

        JOptionPane.showMessageDialog(PanelDevolucao, resultado, "Devolução Registrada", JOptionPane.INFORMATION_MESSAGE);

        carregarEmprestimosAtivos();
    }
    public JPanel getPanelDevolucao() {
        return PanelDevolucao;
    }
}


