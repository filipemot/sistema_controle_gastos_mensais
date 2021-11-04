package br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.mapper;

import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.mapper.TipoContaMapper;
import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.service.dto.TipoContaDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TipoContaMapperTest {

    @Test
    public void mapperTipoContaToTipoContaDto() {
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(1L);
        tipoConta.setDescricao("Descricao");

        TipoContaDTO tipoContaDTO = TipoContaMapper.INSTANCE.tipoContaToTipoContaDto(tipoConta);
        assertThat(tipoContaDTO).isNotNull();
        assertThat(tipoContaDTO.getId()).isEqualTo(tipoConta.getId());
        assertThat(tipoContaDTO.getDescricao()).isEqualTo(tipoConta.getDescricao());
    }

    @Test
    public void mapperTipoContaDTOToTipoConta() {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);
        tipoContaDTO.setDescricao("Descricao");

        TipoConta tipoConta = TipoContaMapper.INSTANCE.tipoContaDTOToTipoContaDto(tipoContaDTO);
        assertThat(tipoConta).isNotNull();
        assertThat(tipoContaDTO.getId()).isEqualTo(tipoConta.getId());
        assertThat(tipoContaDTO.getDescricao()).isEqualTo(tipoConta.getDescricao());
    }

}
