package com.usuario.dto;

import com.usuario.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDto {

    private Long id;

    @NotBlank(message = "Campo obrigatório")
    private String nome;

    @NotBlank(message = "Campo obrigatório")
    @Email(message = "E-mail deve ser válido")
    private String email;

    @NotBlank(message = "Campo obrigatório")
    private String senha;

    @NotBlank(message = "Campo obrigatório")
    private String confirmacaoSenha;

    public boolean isSenhaIguais() {
        return this.senha.equals(this.confirmacaoSenha);
    }

    public UsuarioDto(Usuario usuario) {
        new ModelMapper().map(usuario, this);
    }
}
