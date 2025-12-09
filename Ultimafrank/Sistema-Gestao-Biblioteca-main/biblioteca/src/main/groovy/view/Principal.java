package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Principal extends JFrame {
    private JPanel jpPrincipal;
    private JMenuBar jmenuBar = new JMenuBar();

    public Principal(){

        this.setTitle("Sistema de Gestão de Biblioteca");
        jpPrincipal = new JPanel();
        this.setContentPane(jpPrincipal);
        CriaMenu();
        this.setJMenuBar(jmenuBar);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void CriaMenu(){

        JMenu menuCadastros = new JMenu("Cadastros");
        JMenuItem cadastrarLivro = new JMenuItem("Livros");
        JMenuItem cadastrarUsuario = new JMenuItem("Usuários");

        menuCadastros.add(cadastrarLivro);
        menuCadastros.add(cadastrarUsuario);

        JMenu menuOperacoes = new JMenu("Operações");
        JMenuItem emprestimoLivro = new JMenuItem("Fazer Empréstimo");
        JMenuItem devolucaoLivro = new JMenuItem("Registrar Devolução");
        JMenuItem listarEmprestados = new JMenuItem("Listar Livros Emprestados");

        JMenu menuApagar = new JMenu("Apagar");
        JMenuItem excluirLivro = new JMenuItem("apagar Livro");
        JMenuItem excluiraluno = new JMenuItem("Apagar aluno");

        menuApagar.add(excluiraluno);
        menuApagar.add(excluirLivro);



        menuOperacoes.add(emprestimoLivro);
        menuOperacoes.add(devolucaoLivro);
        menuOperacoes.addSeparator();
        menuOperacoes.add(listarEmprestados);




        jmenuBar.add(menuCadastros);
        jmenuBar.add(menuOperacoes);
        jmenuBar.add(menuApagar);


        cadastrarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frameCadastro = new JFrame("Cadastro e Consulta de Livros");
                frameCadastro.setContentPane(new CadastrarLivro().getPainelCadastro());
                frameCadastro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameCadastro.setSize(800, 600);
                frameCadastro.setLocationRelativeTo(null);
                frameCadastro.setVisible(true);
            }
        });

        cadastrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastrarAluno telaCadastroAluno = new CadastrarAluno();

                JFrame frameCadastro = new JFrame("Cadastro de Alunos");
                frameCadastro.setContentPane(telaCadastroAluno.getPainelCadastroAluno());

                frameCadastro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameCadastro.setSize(800, 600);
                frameCadastro.setLocationRelativeTo(null);
                frameCadastro.setVisible(true);
            }
        });

        emprestimoLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            EmprestarLivro telaEmprestarLivro = new EmprestarLivro();
            JFrame frameEmprestimo = new JFrame("Emprestimo de Livros");
            frameEmprestimo.setContentPane(telaEmprestarLivro.getPanelEmprestimo());

            frameEmprestimo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameEmprestimo.setSize(800, 600);
            frameEmprestimo.setLocationRelativeTo(null);
            frameEmprestimo.setVisible(true);
            }
        });


        devolucaoLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrarDevolucao telaRegistrarDevolucao= new RegistrarDevolucao();
                JFrame frameRegistrarDevolucao = new JFrame("Registrar Devolucao");
                frameRegistrarDevolucao.setContentPane(telaRegistrarDevolucao.getPanelDevolucao());
                frameRegistrarDevolucao.setSize(800, 600);
                frameRegistrarDevolucao.setLocationRelativeTo(null);
                frameRegistrarDevolucao.setVisible(true);

            }
        });

        listarEmprestados.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               ListaLivros telaListaLivros = new ListaLivros();
               JFrame frameListaLivros = new JFrame("Relatório de Livros Emprestados e Disponiveis");

               frameListaLivros.setContentPane(telaListaLivros.getPaneListar());
               frameListaLivros.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
               frameListaLivros.setSize(800, 600);
               frameListaLivros.setLocationRelativeTo(null);
               frameListaLivros.setVisible(true);

           }

        });
        excluiraluno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExcluirAluno telaexcluiraluno = new ExcluirAluno();
                JFrame frameexcluiraluno = new JFrame("Excluir alunos");
                frameexcluiraluno.setContentPane(telaexcluiraluno.getPanel());
                frameexcluiraluno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameexcluiraluno.setSize(800, 600);
                frameexcluiraluno.setLocationRelativeTo(null);
                frameexcluiraluno.setVisible(true);

            }
            });

             excluirLivro.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Excluirlivro telaApagarLivro = new Excluirlivro();
                    JFrame frameApagarLivro = new JFrame("Apagar Livros");

                    frameApagarLivro.setContentPane(telaApagarLivro.getPanel());
                    frameApagarLivro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameApagarLivro.setSize(800, 600);
                    frameApagarLivro.setLocationRelativeTo(null);
                    frameApagarLivro.setVisible(true);
                }
            });


        }

        }






