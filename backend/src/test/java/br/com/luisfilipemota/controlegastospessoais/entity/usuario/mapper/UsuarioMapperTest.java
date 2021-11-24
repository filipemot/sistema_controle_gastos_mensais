package br.com.luisfilipemota.controlegastospessoais.entity.usuario.mapper;

import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class UsuarioMapperTest {

    @Test
    public void testMapperUsuarioToUsuarioDto() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome("Nome");
        usuario.setEmail("Email");

        UsuarioDTO usuarioDTO = UsuarioMapper.INSTANCE.usuarioToUsuarioDto(usuario);
        asserts(usuarioDTO, usuario);
    }

    @Test
    public void testMapperUsuarioDTOToUsuario() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID.randomUUID());
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");

        Usuario usuario = UsuarioMapper.INSTANCE.usuarioDtoToUsuario(usuarioDTO);
        asserts(usuarioDTO, usuario);
    }

    private void asserts(UsuarioDTO usuarioDTO, Usuario usuario) {
        assertThat(usuario).isNotNull();
        assertThat(usuarioDTO).isNotNull();
        assertThat(usuarioDTO.getId()).isEqualTo(usuario.getId());
        assertThat(usuarioDTO.getNome()).isEqualTo(usuario.getNome());
        assertThat(usuarioDTO.getEmail()).isEqualTo(usuario.getEmail());
    }

}
