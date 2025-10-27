package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @Size(max = 50)
    private String name;

    private Integer age;

    @Size(max = 254)
    private String email;

    public UpdateUserRequest(String name, String email, Integer age) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
