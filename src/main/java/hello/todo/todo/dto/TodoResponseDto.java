package hello.todo.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto {

    private long id;
    private String title;
    private int todoOrder;
    private boolean completed;

}
