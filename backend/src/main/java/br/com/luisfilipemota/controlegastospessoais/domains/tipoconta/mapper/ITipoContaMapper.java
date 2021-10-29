package br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.mapper;

import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.dto.TipoContaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ITipoContaMapper {
    ITipoContaMapper INSTANCE = Mappers.getMapper( ITipoContaMapper.class );

    TipoContaDTO tipoContaToTipoContaDto(TipoConta tipoConta);

    TipoConta tipoContaDTOToTipoContaDto(TipoContaDTO tipoContaDto);
}
