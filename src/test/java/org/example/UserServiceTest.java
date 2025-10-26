package org.example;

import org.example.dto.CreateUserRequest;
import org.example.dto.UpdateUserRequest;
import org.example.exception.types.ConflictException;
import org.example.exception.types.NotFoundException;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper mapper;
    @InjectMocks
    UserService service;

    @Test
    void createThrowsConflictWhenEmailExists() {
        when(userRepository.existsUserByEmail("name@mail.ru")).thenReturn(true);

        RuntimeException exception = Assertions.assertThrows(ConflictException.class,
                () -> service.createUser(new CreateUserRequest("name", "name@mail.ru", 1)));
        assertThat(exception.getMessage()).isEqualTo("Email already in use");
    }

    @Test
    void updateThrowsNotFoundWhenUserMissing() {
        when(userRepository.findById(42L)).thenReturn(Optional.empty());
        RuntimeException exception = Assertions.assertThrows(NotFoundException.class,
                () -> service.updateUser(42L, new UpdateUserRequest("name", "name@mail.ru", 1)));
        assertThat(exception.getMessage()).isEqualTo("User not found");
    }
}
