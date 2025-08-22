package com.productapp.restapiproduct;

import com.productapp.restapiproduct.controller.UserRestController;
import com.productapp.restapiproduct.entity.UserDTO;
import com.productapp.restapiproduct.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRestControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserRestController userRestController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        UserDTO dto = new UserDTO();
        dto.setUsername("john");
        dto.setPassword("test123");

        ResponseEntity<String> response = userRestController.registerUser(dto);

        verify(userService, times(1)).save(dto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", response.getBody());
    }

    @Test
    void testLogin_Success() {
        UserDTO dto = new UserDTO();
        dto.setUsername("john");
        dto.setPassword("test123");
        Authentication auth = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(true);

        ResponseEntity<String> response = userRestController.login(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Login successful!", response.getBody());
    }

    @Test
    void testLogin_Failure() {
        UserDTO dto = new UserDTO();
        dto.setUsername("john");
        dto.setPassword("wrongpassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Bad credentials") {
                });

        ResponseEntity<String> response = userRestController.login(dto);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void testGetUserById_Success() {
        UserDTO dto = new UserDTO();
        dto.setId(1);
        dto.setUsername("john");

        when(userService.findById(1)).thenReturn(dto);

        ResponseEntity<UserDTO> response = userRestController.getUserById(1);

        verify(userService, times(1)).findById(1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("john", response.getBody().getUsername());
    }

    @Test
    void testGetUserById_NotFound() {
        UserDTO dto = new UserDTO();
        dto.setId(1);
        dto.setUsername("john");

        when(userService.findById(1)).thenReturn(null);

        ResponseEntity<UserDTO> response = userRestController.getUserById(1);

        verify(userService, times(1)).findById(1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }
}
