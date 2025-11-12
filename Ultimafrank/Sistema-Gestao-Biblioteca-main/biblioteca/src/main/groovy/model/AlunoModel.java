package model;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="alunos")

public class AlunoModel extends PessoaModel implements Serializable {

    public AlunoModel() {

        super();
    }

}
