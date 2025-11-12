package model;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="emprestimos")

public class EmprestimoModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="aluno_id",nullable = false)
    private AlunoModel aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="livro_id", nullable = false)
    private LivroModel livro;

    @Column(name="data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo;

    @Column(name="data_devolucao_prevista",nullable = false)
    private LocalDate dataDevolucaoPrevista;

    @Column(name="data_devolucao_real")
    private LocalDate dataDevolucaoReal;

    public EmprestimoModel() {}

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LivroModel getLivro() {
        return livro;
    }

    public void setLivro(LivroModel livro) {
        this.livro = livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public boolean isAtrasado(){
        return dataDevolucaoReal == null && LocalDate.now().isAfter(dataDevolucaoPrevista);
    }
}
