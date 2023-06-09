package hello.todo.todo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity(name = "TODOS")
@Getter @Setter @ToString
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer todoOrder;

    @Column(nullable = false)
    private Boolean completed;

    public Boolean getCompleted() {
        return completed;
    }

}
