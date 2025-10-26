package org.example.dto;

import java.util.Date;

public record UserResponse(
        Long id,
        String name,
        String email,
        Integer age,
        Date createdAt,
        Date updatedAt
) {
}
