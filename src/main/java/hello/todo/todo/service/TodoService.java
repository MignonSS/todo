package hello.todo.todo.service;

import hello.todo.exception.BusinessLogicException;
import hello.todo.exception.ExceptionCode;
import hello.todo.todo.entity.Todo;
import hello.todo.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Todo todo) {
        Todo findTodo = findVerifiedTodo(todo.getId());

        Optional.ofNullable(todo.getTitle()).ifPresent(findTodo::setTitle);
        Optional.ofNullable(todo.getTodoOrder()).ifPresent(findTodo::setTodoOrder);
        Optional.ofNullable(todo.getCompleted()).ifPresent(findTodo::setCompleted);

        return todoRepository.save(findTodo);
    }

    @Transactional(readOnly = true)
    public Todo findTodo(long id) {
        return findVerifiedTodo(id);
    }

    @Transactional(readOnly = true)
    public List<Todo> findTodos() {
        return todoRepository.findAll();
    }

    public void deleteTodo(long id) {
        Todo findTodo = findVerifiedTodo(id);
        todoRepository.delete(findTodo);
    }

    public Todo findVerifiedTodo(long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TODO_NOT_FOUND));
    }
}