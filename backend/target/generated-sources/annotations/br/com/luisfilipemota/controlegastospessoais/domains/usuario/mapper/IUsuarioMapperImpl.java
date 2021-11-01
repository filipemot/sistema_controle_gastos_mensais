package br.com.luisfilipemota.controlegastospessoais.domains.usuario.mapper;

import br.com.luisfilipemota.controlegastospessoais.domains.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.domains.usuario.service.dto.UsuarioDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-01T16:51:56-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class IUsuarioMapperImpl implements IUsuarioMapper {

    @Override
    public UsuarioDTO usuarioToUsuarioDto(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId( usuario.getId() );
        usuarioDTO.setNome( usuario.getNome() );
        usuarioDTO.setEmail( usuario.getEmail() );

        return usuarioDTO;
    }

    @Override
    public Usuario usuarioDtoToUsuario(UsuarioDTO usuarioDTO) {
        if ( usuarioDTO == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setId( usuarioDTO.getId() );
        usuario.setNome( usuarioDTO.getNome() );
        usuario.setEmail( usuarioDTO.getEmail() );

        return usuario;
    }
}
