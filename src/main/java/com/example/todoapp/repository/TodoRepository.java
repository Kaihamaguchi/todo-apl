package com.example.todoapp.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todoapp.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
  List<Todo> findByDeadlineBefore(LocalDate date);
}