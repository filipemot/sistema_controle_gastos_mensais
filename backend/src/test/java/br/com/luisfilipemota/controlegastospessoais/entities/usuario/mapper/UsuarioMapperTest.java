package br.com.luisfilipemota.controlegastospessoais.entities.usuario.mapper;

import br.com.luisfilipemota.controlegastospessoais.entities.usuario.mapper.UsuarioMapper;
import br.com.luisfilipemota.controlegastospessoais.entities.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.entities.usuario.service.dto.UsuarioDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class UsuarioMapperTest {

    @Test
    public void mapperUsuarioToUsuarioDto() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome");
        usuario.setEmail("Email");

        UsuarioDTO usuarioDTO = UsuarioMapper.INSTANCE.usuarioToUsuarioDto(usuario);
        assertThat(usuarioDTO).isNotNull();
        assertThat(usuarioDTO.getId()).isEqualTo(usuario.getId());
        assertThat(usuarioDTO.getNome()).isEqualTo(usuario.getNome());
        assertThat(usuarioDTO.getEmail()).isEqualTo(usuario.getEmail());
    }

    @Test
    public void mapperUsuarioDTOToUsuario() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");

        Usuario usuario = UsuarioMapper.INSTANCE.usuarioDtoToUsuario(usuarioDTO);
        assertThat(usuario).isNotNull();
        assertThat(usuarioDTO).isNotNull();
        assertThat(usuarioDTO.getId()).isEqualTo(usuario.getId());
        assertThat(usuarioDTO.getNome()).isEqualTo(usuario.getNome());
        assertThat(usuarioDTO.getEmail()).isEqualTo(usuario.getEmail());
    }

}
