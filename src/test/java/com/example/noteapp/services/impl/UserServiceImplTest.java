package com.example.noteapp.services.impl;

import com.example.noteapp.models.dto.UserDto;
import com.example.noteapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto(1, "user");
    }

    @Test
    void addUser() {
        UserDto result = userService.addUser(userDto);
        assertThat(result).isEqualTo(userDto);
    }

    @Test
    void getUser() {
        UserDto result = userService.addUser(userDto);
        assertThat(result).isEqualTo(userService.getUser(userDto.getId()));
    }

}