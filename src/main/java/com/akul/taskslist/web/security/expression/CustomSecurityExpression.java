package com.akul.taskslist.web.security.expression;

import com.akul.taskslist.domain.user.Role;
import com.akul.taskslist.service.UserService;
import com.akul.taskslist.web.security.JwtEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@AllArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;

    public boolean canAccessUser(final Long id) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        JwtEntity jwtEntity = (JwtEntity) authentication
                .getPrincipal();
        Long userId = jwtEntity.getId();

        return userId.equals(id) || hasAnyRole(authentication,
                Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(final Authentication authentication,
                               final Role... roles) {
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                    role.name());
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }

    public boolean canAccessTask(final Long taskId) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        JwtEntity jwtEntity = (JwtEntity) authentication
                .getPrincipal();
        Long userId = jwtEntity.getId();
        return userService.isTaskOwner(
                userId, taskId) || hasAnyRole(authentication,
                Role.ROLE_ADMIN);
    }

}
