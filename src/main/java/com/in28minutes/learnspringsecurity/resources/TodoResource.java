package com.in28minutes.learnspringsecurity.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoResource {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public static final List<Todo> TODOS = List.of(new Todo("Akash", "Learn AWS"),
            new Todo("Akash", "Get AWS certified"));

    @GetMapping("/todos")
    public List<Todo> retrieveAllTodos() {
        return TODOS;
    }

    @GetMapping("/users/{username}/todos")
    @PreAuthorize("hasRole('USER') and #username == authentication.name")
    @PostAuthorize("returnObject.username == 'Akash'")
    public Todo retrieveTodosForSpecificUser(@PathVariable("username") String username) {
        return TODOS.get(0);
    }

    @PostMapping("/users/{username}/todos")
    public void createTodosForSpecificUser(@PathVariable("username") String username, @RequestBody Todo todo) {
        LOGGER.info("Create {} for {}", todo, username);
    }
}

record Todo (String username, String description) {}