package com.usuario.services;

import com.usuario.dto.UsuarioDto;
import com.usuario.dto.UsuarioMinDto;
import com.usuario.entities.Usuario;
import com.usuario.repositories.UsuarioRepository;
import com.usuario.services.exceptions.ArgumentNotValidException;
import com.usuario.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consultarPorIdComSucesso() {

        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(usuario));

        UsuarioMinDto result = service.consultarPorId(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void consultarPorIdComFalha() {

        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.consultarPorId(id));
    }

    @Test
    void consultarTodos() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        List<Usuario> usuarios = List.of(usuario);
        when(repository.findAll()).thenReturn(usuarios);

        List<UsuarioMinDto> results = service.consultarTodos();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void salvarComSucesso() {

        UsuarioDto dto = new UsuarioDto();
        dto.setEmail("test@example.com");
        dto.setSenha("password");
        dto.setConfirmacaoSenha("password");
        when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getSenha())).thenReturn("encodedPassword");
        when(passwordEncoder.encode(dto.getConfirmacaoSenha())).thenReturn("encodedPassword");
        when(repository.save(any(Usuario.class))).thenReturn(new Usuario());

        UsuarioMinDto result = service.salvar(dto);

        assertNotNull(result);
    }

    @Test
    void salvarComFalha() {

        UsuarioDto dto = new UsuarioDto();
        dto.setEmail("test@example.com");
        dto.setSenha("password");
        dto.setConfirmacaoSenha("differentPassword");
        assertThrows(ArgumentNotValidException.class, () -> service.salvar(dto));
    }

    @Test
    void atualizarComSucesso() {

        Long id = 1L;
        UsuarioDto dto = new UsuarioDto();
        Usuario usuario = new Usuario();
        when(repository.getReferenceById(id)).thenReturn(usuario);
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioMinDto result = service.atualizar(id, dto);

        assertNotNull(result);
    }

    @Test
    void atualizarComFalha() {

        Long id = 1L;
        UsuarioDto dto = new UsuarioDto();
        when(repository.getReferenceById(id)).thenThrow(EntityNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> service.atualizar(id, dto));
    }

    @Test
    void deletarComSucesso() {

        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        assertDoesNotThrow(() -> service.deletar(id));
    }

    @Test
    void deletarComFalha() {

        Long id = 1L;
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deletar(id));
    }
}