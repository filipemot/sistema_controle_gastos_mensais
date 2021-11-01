package br.com.luisfilipemota.controlegastospessoais.domains.usuario.mapper;

import br.com.luisfilipemota.controlegastospessoais.domains.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.domains.usuario.service.dto.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IUsuarioMapper {
    IUsuarioMapper INSTANCE = Mappers.getMapper( IUsuarioMapper.class );

    UsuarioDTO usuarioToUsuarioDto(Usuario usuario);

    Usuario usuarioDtoToUsuario(UsuarioDTO usuarioDTO);
}
