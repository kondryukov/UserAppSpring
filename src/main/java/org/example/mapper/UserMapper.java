package org.example.mapper;

import org.example.domain.User;
import org.example.dto.CreateUserRequest;
import org.example.dto.UpdateUserRequest;
import org.example.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public final class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User fromCreate(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        return user;
    }

    public void applyUpdate(UpdateUserRequest request, User user) {
        if (request.getName() != null) user.setName(request.getName());
        if (request.getAge() != null) user.setAge(request.getAge());
    }
}
