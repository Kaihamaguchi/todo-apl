package com.example.todoapp.controller;

import java.time.LocalDate;
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

@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" })
// Vue.jsのデフォルトポート
@RestController
@RequestMapping("/api/todos") // APIのベースURLを設定
public class TodoController {

  @Autowired
  private TodoService todoService;

  /**
   * タスクの一覧を取得する
   * 
   * @return タスク一覧
   */
  @GetMapping
  public List<Todo> getAllTodos() {
    return todoService.getAllTodos();
  }

  /**
   * 新規タスクを追加する
   * 
   * @param todo タスクの入力値
   * @return 保存された入力値
   */
  @PostMapping
  public Todo addTodo(@RequestBody Todo todo) {
    return todoService.saveTodo(todo);
  }

  /**
   * タスクを削除する
   */
  @DeleteMapping("/{id}")
  public void deleteTodo(@PathVariable Integer id) {
    todoService.deleteTodoById(id);
  }

  /**
   * タスクを完了させる
   * 
   * @param id タスクのID
   * @return 更新されたタスク
   */
  @PutMapping("/complete/{id}")
  public Todo completeTodo(@PathVariable Integer id) {
    Todo todo = todoService.findById(id)
                           .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
    todo.setCompleted(!todo.isCompleted());
    return todoService.saveTodo(todo);
  }
  
  /**
   * 指定したIDのToDoの期限を更新するメソッド。
   *
   * @param id 更新するToDoのID
   * @param deadline 新しい期限
   * @return 更新されたToDo
   */
  @PutMapping("/deadline/{id}")
  public Todo updateDeadline(@PathVariable Integer id, @RequestBody LocalDate deadline) {
      Todo todo = todoService.findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
      todo.setDeadline(deadline);
      return todoService.saveTodo(todo);
  }

  /**
   * 期限が過ぎたToDoを取得するメソッド。
   *
   * @param date 現在の日付
   * @return 期限が過ぎたToDoのリスト
   */
  @GetMapping("/expired/{date}")
  public List<Todo> getExpiredTodos(@PathVariable LocalDate date) {
      return todoService.findExpiredTodos(date);
  }
}
