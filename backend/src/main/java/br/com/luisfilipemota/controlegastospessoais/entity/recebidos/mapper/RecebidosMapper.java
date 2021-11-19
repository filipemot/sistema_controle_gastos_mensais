package br.com.luisfilipemota.controlegastospessoais.entity.recebidos.mapper;

import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.model.Recebidos;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service.dto.RecebidosDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecebidosMapper {
    RecebidosMapper INSTANCE = Mappers.getMapper( RecebidosMapper.class );

    RecebidosDTO recebidosToRecebidosDto(Recebidos conta);

    Recebidos recebidosDTOToRecebidos(RecebidosDTO contaDto);
}
