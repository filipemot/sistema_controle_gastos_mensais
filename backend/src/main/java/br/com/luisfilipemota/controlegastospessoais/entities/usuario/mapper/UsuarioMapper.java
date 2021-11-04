package br.com.luisfilipemota.controlegastospessoais.entities.usuario.mapper;

import br.com.luisfilipemota.controlegastospessoais.entities.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.entities.usuario.service.dto.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper( UsuarioMapper.class );

    UsuarioDTO usuarioToUsuarioDto(Usuario usuario);

    Usuario usuarioDtoToUsuario(UsuarioDTO usuarioDTO);
}
