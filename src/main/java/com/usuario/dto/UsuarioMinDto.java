package com.usuario.dto;

import com.usuario.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioMinDto {

    private Long id;
    private String nome;
    private String email;

    public UsuarioMinDto(Usuario usuario) {
        new ModelMapper().map(usuario, this);
    }
}
