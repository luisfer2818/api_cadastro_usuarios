package com.usuario.services;

import com.usuario.dto.UsuarioDto;
import com.usuario.dto.UsuarioMinDto;
import com.usuario.entities.Usuario;
import com.usuario.repositories.UsuarioRepository;
import com.usuario.services.exceptions.ArgumentNotValidException;
import com.usuario.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Transactional(readOnly = true)
    public UsuarioMinDto consultarPorId(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado"));
        return new UsuarioMinDto(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioMinDto> consultarTodos() {
        List<Usuario> result = repository.findAll();
        return result.stream().map(x -> new UsuarioMinDto(x)).toList();
    }

    @Transactional
    public UsuarioMinDto salvar(UsuarioDto dto) {
        this.validar(dto);
        this.criptografarSenha(dto);
        Usuario entity = new Usuario(dto);
        entity = repository.save(entity);
        return new UsuarioMinDto(entity);
    }

    private boolean isExisteEmail(String email) {
        return this.repository.findByEmail(email).isPresent();
    }

    private void validar(UsuarioDto dto) {

        if (!dto.isSenhaIguais()) {
            throw new ArgumentNotValidException("Por favor, note que suas senhas não coincidem. Por favor, ajuste sua senha para garantir que corresponda nos dois campos.");
        } else if (isExisteEmail(dto.getEmail())) {
            throw new ArgumentNotValidException("Desculpe-nos, este e-mail já está cadastrado em nosso sistema. Por favor, utilize um e-mail diferente para criar uma nova.");
        }
    }

    private void criptografarSenha(UsuarioDto dto) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        dto.setConfirmacaoSenha(passwordEncoder.encode(dto.getConfirmacaoSenha()));
    }

    @Transactional
    public UsuarioMinDto atualizar(Long id, UsuarioDto dto) {
        try {
            Usuario entity = repository.getReferenceById(id);
            entity.dtoToEntity(dto);
            entity = repository.save(entity);
            return new UsuarioMinDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        repository.deleteById(id);
    }
}
