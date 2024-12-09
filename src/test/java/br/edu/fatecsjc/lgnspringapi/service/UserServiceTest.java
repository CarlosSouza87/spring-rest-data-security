package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.dto.ChangePasswordRequestDTO;
import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    private ChangePasswordRequestDTO request = new ChangePasswordRequestDTO("oldPassword","newPassword123","newPassword123");

    private User user;
    private Principal connectedUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setPassword("encodedOldPassword");

        connectedUser = new UsernamePasswordAuthenticationToken(user, null);
    }

    @Test
    void testChangePassword_Success() {
        when(passwordEncoder.matches("oldPassword", "encodedOldPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword123")).thenReturn("encodedNewPassword");

        userService.changePassword(request, connectedUser);

        assertEquals("encodedNewPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testChangePassword_WrongCurrentPassword() {
        when(passwordEncoder.matches("oldPassword", "encodedOldPassword")).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                userService.changePassword(request, connectedUser)
        );

        assertEquals("Wrong password", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void testChangePassword_PasswordsDoNotMatch() {
        // Configurações
        String rawPassword = "mesmaSenha";
        String encodedPassword = "encodedMesmaSenha";

        request.setCurrentPassword(rawPassword);
        request.setNewPassword("novaSenha");
        request.setConfirmationPassword("senhaDiferente"); // Senhas não coincidem
        user.setPassword(encodedPassword);

        // Mockando o comportamento do PasswordEncoder
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // Execução e verificação
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                userService.changePassword(request, connectedUser)
        );

        assertEquals("Password are not the same", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

}
