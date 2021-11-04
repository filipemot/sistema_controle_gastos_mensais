package br.com.luisfilipemota.controlegastospessoais.entity.conta.mapper;

import br.com.luisfilipemota.controlegastospessoais.entity.conta.model.Conta;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContaMapper {
    ContaMapper INSTANCE = Mappers.getMapper( ContaMapper.class );

    ContaDTO contaToContaDto(Conta conta);

    Conta contaDTOToConta(ContaDTO contaDto);
}
