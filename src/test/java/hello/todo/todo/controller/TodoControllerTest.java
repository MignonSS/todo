package hello.todo.todo.controller;

import hello.todo.todo.dto.TodoPostDto;
import hello.todo.todo.dto.TodoResponseDto;
import hello.todo.todo.entity.Todo;
import hello.todo.todo.mapper.TodoMapper;
import hello.todo.todo.service.TodoService;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static hello.todo.util.ApiDocumentUtils.getRequestPreProcessor;
import static hello.todo.util.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(TodoController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoMapper mapper;

    @MockBean
    private TodoService todoService;

    @Autowired
    private Gson gson;

    @Test
    public void postMemberTest() throws Exception {
        // given
        TodoPostDto post = new TodoPostDto("밥먹기", 1, false);
        String content = gson.toJson(post);

        Todo mockResultTodo = new Todo();
        mockResultTodo.setId(1L);
        mockResultTodo.setTitle(post.getTitle());
        mockResultTodo.setTodoOrder(post.getTodoOrder());
        mockResultTodo.setCompleted(post.getCompleted());

        TodoResponseDto response = new TodoResponseDto(mockResultTodo.getId(), mockResultTodo.getTitle(), mockResultTodo.getTodoOrder(), mockResultTodo.getCompleted());

        given(mapper.todoPostDtoToTodo(Mockito.any(TodoPostDto.class))).willReturn(mockResultTodo);
        given(todoService.createTodo(Mockito.any(Todo.class))).willReturn(mockResultTodo);
        given(mapper.todoToTodoResponseDto(Mockito.any(Todo.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/v1/todos")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );
        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockResultTodo.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.todoOrder").value(post.getTodoOrder()))
                .andExpect(jsonPath("$.completed").value(post.getCompleted()))
                .andDo(document("post-todo",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("할일내용"),
                                fieldWithPath("todoOrder").type(JsonFieldType.NUMBER).description("할일우선순위"),
                                fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료여부")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("할일아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("할일내용"),
                                fieldWithPath("todoOrder").type(JsonFieldType.NUMBER).description("할일우선순위"),
                                fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료여부")
                        )
                ));
    }

}