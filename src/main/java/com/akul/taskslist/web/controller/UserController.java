package com.akul.taskslist.web.controller;

import com.akul.taskslist.domain.task.Task;
import com.akul.taskslist.domain.user.User;
import com.akul.taskslist.service.TaskService;
import com.akul.taskslist.service.UserService;
import com.akul.taskslist.web.dto.task.TaskDto;
import com.akul.taskslist.web.dto.user.UserDto;
import com.akul.taskslist.web.mappers.TaskMapper;
import com.akul.taskslist.web.mappers.UserMapper;
import com.akul.taskslist.web.validation.OnCreate;
import com.akul.taskslist.web.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    private final UserService userService;
    private final TaskService taskService;


    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto getById(@PathVariable final Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id)")
    public UserDto update(@Validated(OnUpdate.class)
                          @RequestBody final UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updUser = userService.update(user);
        return userMapper.toDto(updUser);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void deleteUser(@PathVariable final Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Get all tasks user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public List<TaskDto> getTasksById(@PathVariable final Long id) {
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{id}/tasks")
    @Operation(summary = "Create task for user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public TaskDto createTask(@PathVariable final Long id,
                              @Validated(OnCreate.class)
                              @RequestBody final TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);

    }

}
