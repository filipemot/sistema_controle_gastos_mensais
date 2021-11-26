package br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto;

import javax.validation.constraints.Email;
import java.util.UUID;

public class UsuarioDTO {

    private UUID id;

    private String nome;

    @Email(message = "E-mail inválido")
    private String email;

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    private String senhaUsuario;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
