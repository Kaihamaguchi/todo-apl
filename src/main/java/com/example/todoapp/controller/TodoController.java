package com.example.todoapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"})
   // Vue.jsのデフォルトポート
@RestController
@RequestMapping("/api/todos")  // APIのベースURLを設定
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        System.out.println("Received Todo: " + todo); // 受け取ったデータをログに出力
        return todoService.saveTodo(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Integer id) {
        todoService.deleteTodoById(id);
    }

    @PutMapping("/complete/{id}")
    public Todo completeTodo(@PathVariable Integer id) {
        Todo todo = todoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        todo.setCompleted(!todo.isCompleted());
        return todoService.saveTodo(todo);
    }
}
