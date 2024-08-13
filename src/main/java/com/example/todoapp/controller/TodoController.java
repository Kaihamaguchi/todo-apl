package com.example.todoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String index() {
        return "redirect:/index.html";
    }


    @PostMapping("/add")
    public String addTodo(@ModelAttribute Todo todo) {
        todoService.saveTodo(todo);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Integer id) {
        todoService.deleteTodoById(id);
        return "redirect:/";
    }

    @PostMapping("/complete/{id}")
    public String completeTodo(@PathVariable Integer id) {
        Todo todo = todoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        todo.setCompleted(!todo.isCompleted());
        todoService.saveTodo(todo);
        return "redirect:/";
    }
}
