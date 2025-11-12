package repository;

import model.LivroModel;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class LivroRepository {

    public LivroModel salvar(LivroModel livro) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            if (livro.getId() == null) {
                em.persist(livro);
            } else {
                livro = em.merge(livro);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao salvar o livro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return livro;
    }

    public LivroModel buscarPorId(Long id) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        LivroModel livro = null;
        try {
            livro = em.find(LivroModel.class, id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar livro por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return livro;
    }

    public List<LivroModel> listarTodos() {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        List<LivroModel> livros = null;
        try {
            TypedQuery<LivroModel> query = em.createQuery("SELECT l FROM LivroModel l", LivroModel.class);
            livros = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar todos os livros: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return livros;
    }

    public void excluir(Long id) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            LivroModel livro = em.find(LivroModel.class, id);
            if (livro != null) {
                em.remove(livro);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao excluir o livro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public List<LivroModel> listarDisponiveis() {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        List<LivroModel> livros = null;
        try {
            String jpql = "SELECT l FROM LivroModel l WHERE l.qtdDisponivel > 0";
            TypedQuery<LivroModel> query = em.createQuery(jpql, LivroModel.class);
            livros = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar livros disponíveis: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return livros;
    }

    public List<LivroModel> buscarPorTitulo(String titulo) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        List<LivroModel> livros = null;
        try {
            String jpql = "SELECT l FROM LivroModel l WHERE l.titulo LIKE :titulo";
            TypedQuery<LivroModel> query = em.createQuery(jpql, LivroModel.class);
            query.setParameter("titulo", "%" + titulo + "%");
            livros = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar livros por título: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return livros;
    }


    public LivroModel buscarPorIsbn(String isbn) {
        EntityManager em = Conexao.getEntityManagerFactory().createEntityManager();
        LivroModel livro = null;
        try {

            String jpql = "SELECT l FROM LivroModel l WHERE l.isbn = :isbn";
            TypedQuery<LivroModel> query = em.createQuery(jpql, LivroModel.class);
            query.setParameter("isbn", isbn);


            List<LivroModel> livros = query.getResultList();
            if (livros != null && !livros.isEmpty()) {
                livro = livros.get(0);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar livro por ISBN: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return livro;
    }
}