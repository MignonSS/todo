package hello.todo.todo.mapper;

import hello.todo.todo.dto.TodoPatchDto;
import hello.todo.todo.dto.TodoPostDto;
import hello.todo.todo.dto.TodoResponseDto;
import hello.todo.todo.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {

    Todo todoPostDtoToTodo(TodoPostDto todoPostDto);

    Todo todoPatchDtoToTodo(TodoPatchDto todoPatchDto);

    TodoResponseDto todoToTodoResponseDto(Todo todo);

    List<TodoResponseDto> todosToTodoResponseDtos(List<Todo> todos);
}
