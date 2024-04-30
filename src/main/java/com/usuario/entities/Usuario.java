package com.usuario.entities;

import com.usuario.dto.UsuarioDto;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "confirmacaoSenha")
    private String confirmacaoSenha;

    public Usuario(UsuarioDto dto) {
        new ModelMapper().map(dto, this);
    }

    public void dtoToEntity(UsuarioDto dto) {
        new ModelMapper().typeMap(UsuarioDto.class, Usuario.class)
                .addMappings(mapper -> mapper.skip(Usuario::setId))
                .map(dto, this);
    }
}
