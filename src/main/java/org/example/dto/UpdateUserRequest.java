package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @Size(max = 50)
    private final String name;

    private Integer age;

    @Email
    @Size(max = 254)
    private String email;

    public UpdateUserRequest(String name, String email, Integer age) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getEmail() {
        if (email == null) {
            return null;
        }
        return email.trim().isEmpty() ? null : email;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
