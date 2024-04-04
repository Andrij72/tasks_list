package com.akul.taskslist.service;

import com.akul.taskslist.domain.user.User;

public interface UserService {

    User getByUsername(String username);

    boolean isTaskOwner(Long userId, Long taskId);

    User update(User user);

    User create(User user);

    void delete(Long id);

    User getById(Long id);
}
