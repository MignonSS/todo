package hello.todo.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoPostDto {

    @NotBlank
    private String title;

    @Positive
    @NotNull
    private Integer todoOrder;

    @NotNull
    private Boolean completed;
}
