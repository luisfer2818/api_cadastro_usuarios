package com.usuario.controllers;

import com.usuario.dto.UsuarioDto;
import com.usuario.dto.UsuarioMinDto;
import com.usuario.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuarioController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consultarPorIdTest() {

        Long id = 1L;
        UsuarioMinDto expectedDto = new UsuarioMinDto();
        when(service.consultarPorId(id)).thenReturn(expectedDto);

        ResponseEntity<UsuarioMinDto> response = controller.consultarPorId(id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDto, response.getBody());
    }

    @Test
    void consultarTodosTest() {

        List<UsuarioMinDto> expectedList = Arrays.asList(new UsuarioMinDto(), new UsuarioMinDto());
        when(service.consultarTodos()).thenReturn(expectedList);

        ResponseEntity<List<UsuarioMinDto>> response = controller.consultarTodos();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedList, response.getBody());
    }

    @Test
    void salvarTest() {

        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        UsuarioMinDto expectedDto = new UsuarioMinDto();
        when(service.salvar(dto)).thenReturn(expectedDto);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        URI expectedUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        ResponseEntity<UsuarioMinDto> response = controller.salvar(dto);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(expectedDto, response.getBody());
        assertEquals(expectedUri, response.getHeaders().getLocation());
    }

    @Test
    void atualizarTest() {

        Long id = 1L;
        UsuarioDto dto = new UsuarioDto();
        UsuarioMinDto expectedDto = new UsuarioMinDto();
        when(service.atualizar(id, dto)).thenReturn(expectedDto);

        ResponseEntity<UsuarioMinDto> response = controller.atualizar(id, dto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDto, response.getBody());
    }

    @Test
    void deletarTest() {

        Long id = 1L;
        doNothing().when(service).deletar(id);

        ResponseEntity<Void> response = controller.deletar(id);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }
}