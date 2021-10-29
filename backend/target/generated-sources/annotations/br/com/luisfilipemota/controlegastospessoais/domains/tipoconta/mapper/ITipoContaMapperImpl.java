package br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.mapper;

import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.dto.TipoContaDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-29T19:04:37-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class ITipoContaMapperImpl implements ITipoContaMapper {

    @Override
    public TipoContaDTO tipoContaToTipoContaDto(TipoConta tipoConta) {
        if ( tipoConta == null ) {
            return null;
        }

        TipoContaDTO tipoContaDTO = new TipoContaDTO();

        tipoContaDTO.setId( tipoConta.getId() );
        tipoContaDTO.setDescricao( tipoConta.getDescricao() );

        return tipoContaDTO;
    }

    @Override
    public TipoConta tipoContaDTOToTipoContaDto(TipoContaDTO tipoContaDto) {
        if ( tipoContaDto == null ) {
            return null;
        }

        TipoConta tipoConta = new TipoConta();

        tipoConta.setId( tipoContaDto.getId() );
        tipoConta.setDescricao( tipoContaDto.getDescricao() );

        return tipoConta;
    }
}
