package com.akul.taskslist.web.dto.user;

import com.akul.taskslist.web.validation.OnCreate;
import com.akul.taskslist.web.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
@Schema(description = "User DTO")
public class UserDto {

    @Schema(description = "User id", example = "1")
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "User name", example = "John Doe")
    @NotNull(message = "Name must be not null.", groups = {OnUpdate.class,
            OnCreate.class})
    @Length(max = 255, message = "Name length must be smaller then 255.",
            groups = {OnUpdate.class, OnCreate.class})
    private String name;

    @Schema(description = "User email", example = "johndoe@gmail.com")
    @NotNull(message = "Username must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Username length must be smaller then 255.",
            groups = {OnUpdate.class, OnCreate.class})
    private String username;

    @Schema(description = "User password",
            example = "$2a$10$GHTOeyLt.avNCzWQCZbbPeC587aAHF9mglDgVd5WC0BhrApG9bcf2")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private String password;

    @Schema(description = "User password confirmation",
            example = "$2a$10$GHTOeyLt.avNCzWQCZbbPeC587aAHF9mglDgVd5WC0BhrApG9bcf2")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password conformation must be not null.",
            groups = {OnUpdate.class})
    private String passwordConfirmation;

}
