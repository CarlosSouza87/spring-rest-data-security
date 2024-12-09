package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LogoutServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LogoutService logoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testLogout_Success() {
        // Configuração
        String jwt = "validJwtToken";
        String authHeader = "Bearer " + jwt;
        Token token = new Token();
        token.setToken(jwt);
        token.setExpired(false);
        token.setRevoked(false);

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(tokenRepository.findByToken(jwt)).thenReturn(Optional.of(token));

        // Execução
        logoutService.logout(request, response, authentication);

        // Verificações
        verify(tokenRepository, times(1)).save(token);
        verify(tokenRepository).findByToken(jwt);
        assertTrue(token.isExpired());
        assertTrue(token.isRevoked());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testLogout_NoAuthorizationHeader() {
        // Configuração
        when(request.getHeader("Authorization")).thenReturn(null);

        // Execução
        logoutService.logout(request, response, authentication);

        // Verificações
        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any(Token.class));
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testLogout_InvalidAuthorizationHeader() {
        // Configuração
        String invalidAuthHeader = "InvalidHeader";
        when(request.getHeader("Authorization")).thenReturn(invalidAuthHeader);

        // Execução
        logoutService.logout(request, response, authentication);

        // Verificações
        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any(Token.class));
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testLogout_TokenNotFound() {
        // Configuração
        String jwt = "nonExistentToken";
        String authHeader = "Bearer " + jwt;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(tokenRepository.findByToken(jwt)).thenReturn(Optional.empty());

        // Execução
        logoutService.logout(request, response, authentication);

        // Verificações
        verify(tokenRepository, times(1)).findByToken(jwt);
        verify(tokenRepository, never()).save(any(Token.class));
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
