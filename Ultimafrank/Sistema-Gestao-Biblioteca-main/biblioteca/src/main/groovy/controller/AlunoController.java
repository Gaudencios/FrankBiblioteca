package controller;

import model.AlunoModel;
import repository.AlunoRepository;
import repository.EmprestimoRepository;
import model.EmprestimoModel;

import java.util.List;

public class AlunoController {

    private AlunoRepository alunoRepository;
    private EmprestimoRepository emprestimoRepository;

    public AlunoController() {
        this.alunoRepository = new AlunoRepository();
        this.emprestimoRepository = new EmprestimoRepository();
    }
    public boolean salvarAluno(Long id, String nome, String sexo, String celular, String email) {
        if (nome == null || nome.trim().isEmpty()) {
            System.err.println("  nome não pode estar vazio.");
            return false;}
        if (sexo == null || sexo.trim().isEmpty()) {
            System.err.println("  sexo não pode estar vazio.");
            return false;}
        if (celular == null || celular.trim().isEmpty()) {
            System.err.println(" celular não pode estar vazio.");
            return false;}
        if (email == null || email.trim().isEmpty()) {
            System.err.println(" e-mail não pode estar vazio.");
            return false;}
        AlunoModel aluno = new AlunoModel();
        aluno.setId(id);
        aluno.setNome(nome);
        aluno.setSexo(sexo);
        aluno.setCelular(celular);
        aluno.setEmail(email);
        try {
            alunoRepository.salvar(aluno);
            return true;}
        catch (Exception e) {
            System.err.println("Erro ao salvar aluno : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirAluno(Long id) {
        if (id == null) {
            System.err.println("Erro: ID do aluno não pode ser nulo para exclusão.");
            return false;
        }

        try {
            AlunoModel aluno = alunoRepository.buscarPorId(id);
            if (aluno == null) {
                System.err.println("Erro: Aluno com ID " + id + " não encontrado.");
                return false;
            }

            List<EmprestimoModel> emprestimosAtivos = emprestimoRepository.buscarAtivosPorAluno(aluno);
            if (emprestimosAtivos != null && !emprestimosAtivos.isEmpty()) {
                System.err.println("Erro: Não é possível excluir. Aluno possui " + emprestimosAtivos.size() + " empréstimo(s) ativo(s).");
                return false;
            }


            List<EmprestimoModel> historicoEmprestimos = emprestimoRepository.buscarTodosPorAluno(aluno);
            if (historicoEmprestimos != null) {
                for (EmprestimoModel emprestimoAntigo : historicoEmprestimos) {

                    emprestimoRepository.excluir(emprestimoAntigo.getId());
                }
            }

            alunoRepository.excluir(id);
            return true;

        } catch (Exception e) {

            System.err.println("Erro ao excluir aluno (possível falha ao limpar histórico): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public AlunoModel buscarAlunoPorId(Long id) {
        return alunoRepository.buscarPorId(id);
    }

    public List<AlunoModel> listarTodosAlunos() {
        return alunoRepository.listarTodos();
    }

    public List<AlunoModel> buscarAlunosPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return listarTodosAlunos();
        }
        return alunoRepository.buscarPorNome(nome);
    }
    public boolean verificarLimiteDeEmprestimos(AlunoModel aluno) {
        if (aluno == null || aluno.getId() == null) {
            return false;
        }
        int emprestimosAtivos = emprestimoRepository.buscarAtivosPorAluno(aluno).size();
        return emprestimosAtivos < 5;
    }
}



