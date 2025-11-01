package todo_mongo.todo_mongodb_app.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import todo_mongo.todo_mongodb_app.model.Todo;
import todo_mongo.todo_mongodb_app.repository.TodoRepository;
import todo_mongo.todo_mongodb_app.utils.ResponseHandler;

@RestController
@RequestMapping("/api/v1/springboot/todoApp")
@CrossOrigin(origins = "*")
// @CrossOrigin(origins = "http://localhost:3000")

public class TodoController {

    @Autowired // ✅ Add this line
    private TodoRepository todoRepository;

    // ✅ Get all todos
    // @GetMapping("/todo")
    // public List<Todo> getAllTodos() {
    //     return todoRepository.findAll(); // * without response entity
    // }
    // ** Response entity : allows us to return a response with a status code and a body. 
    @GetMapping("/todo")
    public ResponseEntity<Map<String, Object>> getAllTodos() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Todo> todoData = todoRepository.findAll();

            // ✅ Success
            response.put("status", "success");
            response.put("message", "Todos fetched successfully");
            response.put("count", todoData.size());
            response.put("data", todoData);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            // ❌ Error handler
            response.put("status", "error");
            response.put("message", "Failed to fetch todos: " + e.getMessage());
            response.put("data", Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ create todo
    @PostMapping("/todo")
    public ResponseEntity<Map<String, Object>> createTodo(@Valid @RequestBody Todo todo, BindingResult result) {
        try {
            // ✅ If validation fails
            if (result.hasErrors()) {
                String errorMessage = result.getFieldError().getDefaultMessage();
                return ResponseHandler.generateResponse(
                        "error",
                        errorMessage,
                        Collections.emptyList(),
                        HttpStatus.BAD_REQUEST);
            }

            Todo createdTodo = todoRepository.save(todo);

            if (createdTodo != null) {
                return ResponseHandler.generateResponse(
                        "success",
                        "Todo created successfully",
                        createdTodo,
                        HttpStatus.CREATED);
            } else {
                return ResponseHandler.generateResponse(
                        "false",
                        "Failed to create todo",
                        Collections.emptyList(),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    "error",
                    "Failed to create todo: " + e.getMessage(),
                    Collections.emptyList(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ update todo
    @PutMapping("/todo/{id}")
    public ResponseEntity<Map<String, Object>> updateTodo(@PathVariable String id, @Valid @RequestBody Todo todo,
            BindingResult result) {
        try {

            Todo updatedTodo = todoRepository.findById(id).orElse(null);

            if (updatedTodo == null) {
                return ResponseHandler.generateResponse(
                        "error",
                        "Todo not found",
                        Collections.emptyList(),
                        HttpStatus.NOT_FOUND);
            } else {
                if (todo.getTitle() != null) {
                    updatedTodo.setTitle(todo.getTitle());
                }
                if (todo.getDescription() != null) {
                    updatedTodo.setDescription(todo.getDescription());
                }
                updatedTodo.setDone(todo.isDone());

                todoRepository.save(updatedTodo);

                return ResponseHandler.generateResponse(
                        "success",
                        "Todo updated successfully",
                        updatedTodo,
                        HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    "error",
                    "Failed to update todo: " + e.getMessage(),
                    Collections.emptyList(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // ✅ delete todo
    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Map<String, Object>> deleteTodo(@PathVariable String id) {
        try {
            Todo deletedTodo = todoRepository.findById(id).orElse(null);

            if (deletedTodo == null) {
                return ResponseHandler.generateResponse(
                        "error",
                        "Todo not found",
                        Collections.emptyList(),
                        HttpStatus.NOT_FOUND);
            } else {
                todoRepository.deleteById(id);

                return ResponseHandler.generateResponse(
                        "success",
                        "Todo deleted successfully",
                        deletedTodo,
                        HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    "error",
                    "Failed to delete todo: " + e.getMessage(),
                    Collections.emptyList(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
