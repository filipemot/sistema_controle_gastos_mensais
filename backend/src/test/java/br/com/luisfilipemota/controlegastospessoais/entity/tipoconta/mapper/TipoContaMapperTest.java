package br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.mapper;

import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto.TipoContaDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class TipoContaMapperTest {

    @Test
    public void testMapperTipoContaToTipoContaDto() {
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID.randomUUID());
        tipoConta.setDescricao("Descricao");

        TipoContaDTO tipoContaDTO = TipoContaMapper.INSTANCE.tipoContaToTipoContaDto(tipoConta);
        asserts(tipoConta, tipoContaDTO);
    }


    @Test
    public void testMapperTipoContaDTOToTipoConta() {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID.randomUUID());
        tipoContaDTO.setDescricao("Descricao");

        TipoConta tipoConta = TipoContaMapper.INSTANCE.tipoContaDTOToTipoConta(tipoContaDTO);
        asserts(tipoConta, tipoContaDTO);
    }

    private void asserts(TipoConta tipoConta, TipoContaDTO tipoContaDTO) {
        assertThat(tipoContaDTO).isNotNull();
        assertThat(tipoContaDTO.getId()).isEqualTo(tipoConta.getId());
        assertThat(tipoContaDTO.getDescricao()).isEqualTo(tipoConta.getDescricao());
    }
}
