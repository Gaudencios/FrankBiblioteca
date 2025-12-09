package view;

import javax.swing.*;
import controller.AlunoController;

import model.AlunoModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ExcluirAluno {
    private JPanel JPAlunoEX;
    private JTextField ExText;
    private JButton buscarAButton;
    private JTable tableAlunoE;
    private JButton excluirAButton;
    private  AlunoController alunoController;
    private DefaultTableModel tableModel;

    public ExcluirAluno() {
        alunoController = new AlunoController();
        configurarTabela();
        carregarAlunosNaTabela();


        excluirAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirAlunoSelecionado();
            }
        });

        buscarAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarAlunos();
            }
        });
    }

    private void configurarTabela() {
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nome", "E-mail", "Celular"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableAlunoE.setModel(tableModel);

    }


    private void preencherTabela(List<AlunoModel> alunos) {
        tableModel.setRowCount(0);
        if (alunos == null) return;

        for (AlunoModel aluno : alunos) {
            tableModel.addRow(new Object[]{
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getEmail(),
                    aluno.getCelular()
            });
        }
    }



    private void carregarAlunosNaTabela() {
        List<AlunoModel> alunos = alunoController.listarTodosAlunos();
        preencherTabela(alunos);
    }


    private void buscarAlunos() {
        String nome = ExText.getText();
        List<AlunoModel> alunos = alunoController.buscarAlunosPorNome(nome);
        preencherTabela(alunos);
    }


    private void excluirAlunoSelecionado() {
        int linhaSelecionada = tableAlunoE.getSelectedRow();


        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(JPAlunoEX,
                    "Por favor, selecione um aluno na tabela.",
                    "Nenhum Aluno Selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long id = (Long) tableModel.getValueAt(linhaSelecionada, 0);
        String nome = (String) tableModel.getValueAt(linhaSelecionada, 1);



        Object[] opcoes = {"Sim, excluir", "Não, cancelar"};

        int confirmacao = JOptionPane.showOptionDialog(
                JPAlunoEX,

                "Tem certeza que deseja excluir o(a) aluno(a): " + nome + "?\n(O histórico de empréstimos dele(a) também será apagado)",
                "Confirmar Exclusão",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opcoes,
                opcoes[1]
        );


        if (confirmacao == 0) {

            boolean sucesso = alunoController.excluirAluno(id);

            if (sucesso) {

                JOptionPane.showMessageDialog(JPAlunoEX,
                        "Aluno e seu histórico foram excluídos com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                buscarAlunos();

            } else {

                JOptionPane.showMessageDialog(JPAlunoEX,
                        "Não foi possível excluir o aluno.\nVerifique se ele possui empréstimos ATIVOS (pendentes).",
                        "Erro ao Excluir",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public JPanel getPanel() {
        return JPAlunoEX;
    }



}






