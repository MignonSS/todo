package hello.todo.todo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TodoPatchDto {
    private Long id;

    @NotEmpty
    private String title;

    private Integer todoOrder;
    private Boolean completed;
}
