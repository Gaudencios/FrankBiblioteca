package repository;

import model.EmprestimoModel;
import model.AlunoModel;
import model.LivroModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.time.LocalDate;

public class EmprestimoRepository {

    public EmprestimoRepository() {
    }
    public EmprestimoModel salvar(EmprestimoModel emprestimo) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            if (emprestimo.getId() == null) {
                em.persist(emprestimo);
            } else {
                emprestimo = em.merge(emprestimo);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao salvar o empréstimo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return emprestimo;
    }

    public List<EmprestimoModel> buscarAtivosPorAluno(AlunoModel aluno) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        List<EmprestimoModel> emprestimos = null;
        try {
            String jpql = "SELECT e FROM EmprestimoModel e JOIN FETCH e.aluno a JOIN FETCH e.livro l WHERE e.aluno = :aluno AND e.dataDevolucaoReal IS NULL";
            TypedQuery<EmprestimoModel> query = em.createQuery(jpql, EmprestimoModel.class);
            query.setParameter("aluno", aluno);
            emprestimos = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar empréstimos ativos por aluno: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return emprestimos;
    }

    public List<EmprestimoModel> listarAtrasados() {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        List<EmprestimoModel> atrasados = null;
        try {
            String jpql = "SELECT e FROM EmprestimoModel e JOIN FETCH e.aluno a JOIN FETCH e.livro l WHERE e.dataDevolucaoPrevista < :hoje AND e.dataDevolucaoReal IS NULL";
            TypedQuery<EmprestimoModel> query = em.createQuery(jpql, EmprestimoModel.class);
            query.setParameter("hoje", LocalDate.now());
            atrasados = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar empréstimos atrasados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return atrasados;
    }
    public List<EmprestimoModel> listarTodosAtivos() {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        List<EmprestimoModel> ativos = null;
        try {
            String jpql = "SELECT e FROM EmprestimoModel e JOIN FETCH e.aluno a JOIN FETCH e.livro l WHERE e.dataDevolucaoReal IS NULL";
            TypedQuery<EmprestimoModel> query = em.createQuery(jpql, EmprestimoModel.class);
            ativos = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar todos os empréstimos ativos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return ativos;
    }
    public List<EmprestimoModel> buscarAtivosPorNomeAluno(String nomeAluno) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        List<EmprestimoModel> emprestimos = null;
        try {
            String jpql = "SELECT e FROM EmprestimoModel e JOIN FETCH e.aluno a JOIN FETCH e.livro l WHERE e.dataDevolucaoReal IS NULL AND a.nome LIKE :nomeAluno";
            TypedQuery<EmprestimoModel> query = em.createQuery(jpql, EmprestimoModel.class);
            query.setParameter("nomeAluno", "%" + nomeAluno + "%");
            emprestimos = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar empréstimos ativos por nome de aluno: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return emprestimos;
    }
    public List<EmprestimoModel> buscarAtivosPorLivro(LivroModel livro) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        List<EmprestimoModel> emprestimos = null;
        try {

            String jpql = "SELECT e FROM EmprestimoModel e WHERE e.livro = :livro AND e.dataDevolucaoReal IS NULL";
            TypedQuery<EmprestimoModel> query = em.createQuery(jpql, EmprestimoModel.class);
            query.setParameter("livro", livro);
            emprestimos = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar empréstimos ativos por livro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return emprestimos;
    }
}