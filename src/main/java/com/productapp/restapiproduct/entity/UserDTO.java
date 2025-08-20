package com.productapp.restapiproduct.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {
    public interface SignupView {}  // marker interface
    public interface DefaultView {}

    private int id;

    @NotEmpty(message = "Username cannot be empty")
    @JsonView({DefaultView.class, SignupView.class})
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @JsonView(SignupView.class)
    private String password;
}
