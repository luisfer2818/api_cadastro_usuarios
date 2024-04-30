package com.usuario.controllers;

import com.usuario.dto.UsuarioDto;
import com.usuario.dto.UsuarioMinDto;
import com.usuario.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioMinDto> consultarPorId(@PathVariable Long id) {
        UsuarioMinDto dto = service.consultarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioMinDto>> consultarTodos() {
        List<UsuarioMinDto> dto = service.consultarTodos();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UsuarioMinDto> salvar(@Valid @RequestBody UsuarioDto dto) {
        UsuarioMinDto minDto = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(minDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioMinDto> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioDto dto) {
        UsuarioMinDto minDto = service.atualizar(id, dto);
        return ResponseEntity.ok(minDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
