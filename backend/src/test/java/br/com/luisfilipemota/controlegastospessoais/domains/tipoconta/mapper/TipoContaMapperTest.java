package br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.mapper;

import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.dto.TipoContaDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TipoContaMapperTest {

    @Test
    public void mapperTipoContaToTipoContaDto() {
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(1L);
        tipoConta.setDescricao("Descricao");

        TipoContaDTO tipoContaDTO = ITipoContaMapper.INSTANCE.tipoContaToTipoContaDto(tipoConta);
        assertThat(tipoContaDTO).isNotNull();
        assertThat(tipoContaDTO.getId()).isEqualTo(tipoConta.getId());
        assertThat(tipoContaDTO.getDescricao()).isEqualTo(tipoConta.getDescricao());
    }

    @Test
    public void mapperTipoContaDTOToTipoConta() {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);
        tipoContaDTO.setDescricao("Descricao");

        TipoConta tipoConta = ITipoContaMapper.INSTANCE.tipoContaDTOToTipoContaDto(tipoContaDTO);
        assertThat(tipoConta).isNotNull();
        assertThat(tipoContaDTO.getId()).isEqualTo(tipoConta.getId());
        assertThat(tipoContaDTO.getDescricao()).isEqualTo(tipoConta.getDescricao());
    }

}
