package controller;

import model.AlunoModel;
import model.EmprestimoModel;
import model.LivroModel;
import repository.EmprestimoRepository;
import repository.LivroRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class EmprestimoController {

    private EmprestimoRepository emprestimoRepository;
    private LivroRepository livroRepository;

    public static final int PRAZO_DEVOLUCAO_DIAS = 14;
    public static final int LIMITE_EMPRESTIMOS_ALUNO = 5;
    public static final double VALOR_MULTA_POR_DIA = 1.50;


    public static final double VALOR_MULTA_POR_AVARIA = 25.00;

    public EmprestimoController() {
        this.emprestimoRepository = new EmprestimoRepository();
        this.livroRepository = new LivroRepository();
    }

    public String realizarEmprestimo(AlunoModel aluno, LivroModel livro) {
        if (aluno == null || livro == null) {
            return "Aluno ou Livro não selecionado.";
        }

        List<EmprestimoModel> emprestimosAtivos = emprestimoRepository.buscarAtivosPorAluno(aluno);
        if (emprestimosAtivos.size() >= LIMITE_EMPRESTIMOS_ALUNO) {
            return "Aluno já atingiu o limite de " + LIMITE_EMPRESTIMOS_ALUNO + " livros emprestados.";
        }

        if (livro.getQtdDisponivel() <= 0) {
            return " Livro indisponível no estoque.";
        }

        try {
            livro.setQtdDisponivel(livro.getQtdDisponivel() - 1);
            livroRepository.salvar(livro);

            EmprestimoModel novoEmprestimo = new EmprestimoModel();
            novoEmprestimo.setAluno(aluno);
            novoEmprestimo.setLivro(livro);
            novoEmprestimo.setDataEmprestimo(LocalDate.now());

            LocalDate dataPrevista = LocalDate.now().plusDays(PRAZO_DEVOLUCAO_DIAS);
            novoEmprestimo.setDataDevolucaoPrevista(dataPrevista);
            novoEmprestimo.setDataDevolucaoReal(null);

            emprestimoRepository.salvar(novoEmprestimo);

            return "Empréstimo realizado com sucesso! Devolver até: " + dataPrevista;

        } catch (Exception e) {
            try {

                livro.setQtdDisponivel(livro.getQtdDisponivel() + 1);
                livroRepository.salvar(livro);
            } catch (Exception eRevert) {
                System.err.println("Falha ao salvar empréstimo E falha ao reverter estoque! " + eRevert.getMessage());
            }
            return "Erro crítico ao tentar salvar o empréstimo: " + e.getMessage();
        }
    }

    public String registrarDevolucao(EmprestimoModel emprestimo) {
        return registrarDevolucao(emprestimo, false);
    }


    public String registrarDevolucao(EmprestimoModel emprestimo, boolean comAvarias) {
        if (emprestimo == null) {
            return "Nenhum empréstimo selecionado.";
        }
        if (emprestimo.getDataDevolucaoReal() != null) {
            return "Este empréstimo já foi devolvido.";
        }

        try {
            double multaAtraso = calcularMulta(emprestimo.getDataDevolucaoPrevista(), LocalDate.now());
            double multaAvaria = 0.0;

            if (comAvarias) {
                multaAvaria = VALOR_MULTA_POR_AVARIA;
            }
            double multaTotal = multaAtraso + multaAvaria;


            emprestimo.setDataDevolucaoReal(LocalDate.now());
            emprestimoRepository.salvar(emprestimo);


            LivroModel livro = emprestimo.getLivro();
            LivroModel livroDoBanco = livroRepository.buscarPorId(livro.getId());
            if (livroDoBanco != null) {
                livroDoBanco.setQtdDisponivel(livroDoBanco.getQtdDisponivel() + 1);
                livroRepository.salvar(livroDoBanco);
            } else {
                System.err.println("Aviso: Livro ID " + livro.getId() + " não encontrado para devolver ao estoque.");
            }


            StringBuilder mensagem = new StringBuilder("Devolução registrada. ");
            if (multaTotal == 0) {
                mensagem.append("Obrigado!");
            } else {
                if (multaAtraso > 0) {
                    mensagem.append(String.format("Multa por atraso: R$ %.2f. ", multaAtraso));
                }
                if (multaAvaria > 0) {
                    mensagem.append(String.format("Multa por avaria: R$ %.2f. ", multaAvaria));
                }
                mensagem.append(String.format("Total da multa: R$ %.2f.", multaTotal));
            }
            return mensagem.toString();

        } catch (Exception e) {
            try {
                // Tenta reverter a devolução
                emprestimo.setDataDevolucaoReal(null);
                emprestimoRepository.salvar(emprestimo);
            } catch (Exception eRevert) {
                System.err.println("Falha ao registrar devolução E falha ao reverter! " + eRevert.getMessage());
            }
            return "erro ao tentar registrar a devolução: " + e.getMessage();
        }
    }

    private double calcularMulta(LocalDate dataPrevista, LocalDate dataDevolucao) {
        if (dataDevolucao.isAfter(dataPrevista)) {
            long diasAtraso = ChronoUnit.DAYS.between(dataPrevista, dataDevolucao);
            return diasAtraso * VALOR_MULTA_POR_DIA;
        }
        return 0.0;
    }


    public List<EmprestimoModel> listarTodosEmprestimosAtivos() {
        return emprestimoRepository.listarTodosAtivos();
    }


    public List<EmprestimoModel> buscarEmprestimosAtivos(String nomeAluno) {
        if (nomeAluno == null || nomeAluno.trim().isEmpty()) {
            return emprestimoRepository.listarTodosAtivos();
        } else {

            return emprestimoRepository.buscarAtivosPorNomeAluno(nomeAluno);
        }
    }

    public List<EmprestimoModel> listarEmprestimosAtrasados() {
        return emprestimoRepository.listarAtrasados();
    }
}