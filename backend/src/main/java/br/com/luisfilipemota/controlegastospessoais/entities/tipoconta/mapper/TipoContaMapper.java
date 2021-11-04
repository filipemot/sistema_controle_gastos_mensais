package br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.mapper;

import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.service.dto.TipoContaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TipoContaMapper {
    TipoContaMapper INSTANCE = Mappers.getMapper( TipoContaMapper.class );

    TipoContaDTO tipoContaToTipoContaDto(TipoConta tipoConta);

    TipoConta tipoContaDTOToTipoContaDto(TipoContaDTO tipoContaDto);
}
