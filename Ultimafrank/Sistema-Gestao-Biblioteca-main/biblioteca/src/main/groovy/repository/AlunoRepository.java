package repository;

import model.AlunoModel;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class AlunoRepository {
    public AlunoModel salvar(AlunoModel aluno) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            if (aluno.getId() == null) {
                em.persist(aluno);
            } else {
                aluno = em.merge(aluno);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao salvar o aluno: " + e.getMessage());
            e.printStackTrace();

            throw e;
        } finally {
            em.close();
        }
        return aluno;
    }
    public AlunoModel buscarPorId(Long id) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        AlunoModel aluno = null;
        try {
            aluno = em.find(AlunoModel.class, id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar aluno por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return aluno;
    }
    public List<AlunoModel> listarTodos() {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        List<AlunoModel> alunos = null;
        try {
            TypedQuery<AlunoModel> query = em.createQuery("SELECT a FROM AlunoModel a", AlunoModel.class);
            alunos = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar todos os alunos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return alunos;
    }
    public void excluir(Long id) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            AlunoModel aluno = em.find(AlunoModel.class, id);
            if (aluno != null) {
                em.remove(aluno);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao excluir o aluno: " + e.getMessage());
            e.printStackTrace();

            throw e;
        } finally {
            em.close();
        }
    }
    public List<AlunoModel> buscarPorNome(String nome) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        List<AlunoModel> alunos = null;
        try {
            String jpql = "SELECT a FROM AlunoModel a WHERE a.nome LIKE :nome";
            TypedQuery<AlunoModel> query = em.createQuery(jpql, AlunoModel.class);
            query.setParameter("nome", "%" + nome + "%"); // Usa LIKE
            alunos = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar alunos por nome: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return alunos;
    }
    public AlunoModel buscarPorEmail(String email) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        AlunoModel aluno = null;
        try {
            String jpql = "SELECT a FROM AlunoModel a WHERE a.email = :email";
            TypedQuery<AlunoModel> query = em.createQuery(jpql, AlunoModel.class);
            query.setParameter("email", email);
            List<AlunoModel> alunos = query.getResultList();
            if (alunos != null && !alunos.isEmpty()) {
                aluno = alunos.get(0);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar aluno por e-mail: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return aluno;
    }
}