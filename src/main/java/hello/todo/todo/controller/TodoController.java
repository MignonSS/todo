package hello.todo.todo.controller;

import hello.todo.todo.dto.TodoPatchDto;
import hello.todo.todo.dto.TodoPostDto;
import hello.todo.todo.entity.Todo;
import hello.todo.todo.mapper.TodoMapper;
import hello.todo.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/todos")
@RequiredArgsConstructor
@Validated
public class TodoController {

    private final TodoService todoService;
    private final TodoMapper mapper;

    @PostMapping
    public ResponseEntity postTodo(@Valid @RequestBody TodoPostDto todoPostDto) {

        Todo createdTodo = todoService.createTodo(mapper.todoPostDtoToTodo(todoPostDto));
        return new ResponseEntity(mapper.todoToTodoResponseDto(createdTodo), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchTodo(@Valid @RequestBody TodoPatchDto todoPatchDto,
                                    @PathVariable @Positive long id) {

        todoPatchDto.setId(id);
        Todo updatedTodo = todoService.updateTodo(mapper.todoPatchDtoToTodo(todoPatchDto));
        return new ResponseEntity(mapper.todoToTodoResponseDto(updatedTodo), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTodo(@PathVariable @Positive long id) {

        Todo findTodo = todoService.findTodo(id);

        System.out.println(findTodo);
        return new ResponseEntity(mapper.todoToTodoResponseDto(findTodo), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTodos() {

        List<Todo> todos = todoService.findTodos();
        return new ResponseEntity(mapper.todosToTodoResponseDtos(todos), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable @Positive long id) {
        todoService.deleteTodo(id);
    }
}
