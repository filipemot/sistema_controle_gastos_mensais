package br.com.luisfilipemota.controlegastospessoais.entity.conta.mapper;

import br.com.luisfilipemota.controlegastospessoais.entity.conta.model.Conta;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto.TipoContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;


public class ContaMapperTest {

    @Test
    public void mapperContaToContaDto() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(1L);

        Conta conta = new Conta();
        conta.setId(1L);
        conta.setUsuario(usuario);
        conta.setTipoConta(tipoConta);
        conta.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        conta.setMesConta(11);
        conta.setAnoConta(2021);
        conta.setDescricao("Descricao");
        conta.setValor(100.0);
        conta.setNumeroParcela(1);
        conta.setTotalParcelas(1);
        conta.setRecorrente(false);

        ContaDTO contaDTO = ContaMapper.INSTANCE.contaToContaDto(conta);
        assertThat(contaDTO).isNotNull();
        assertThat(contaDTO.getId()).isEqualTo(conta.getId());
        assertThat(contaDTO.getUsuario().getId()).isEqualTo(conta.getUsuario().getId());
        assertThat(contaDTO.getTipoConta().getId()).isEqualTo(conta.getTipoConta().getId());
        assertThat(contaDTO.getDataConta()).isEqualTo(conta.getDataConta());
        assertThat(contaDTO.getMesConta()).isEqualTo(conta.getMesConta());
        assertThat(contaDTO.getAnoConta()).isEqualTo(conta.getAnoConta());
        assertThat(contaDTO.getDescricao()).isEqualTo(conta.getDescricao());
        assertThat(contaDTO.getValor()).isEqualTo(conta.getValor());
        assertThat(contaDTO.getNumeroParcela()).isEqualTo(conta.getNumeroParcela());
        assertThat(contaDTO.getTotalParcelas()).isEqualTo(conta.getTotalParcelas());
        assertThat(contaDTO.getRecorrente()).isEqualTo(conta.getRecorrente());
    }

    @Test
    public void mapperContaDTOToConta() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(1L);
        TipoContaDTO tipoConta = new TipoContaDTO();
        tipoConta.setId(1L);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);
        contaDTO.setUsuario(usuario);
        contaDTO.setTipoConta(tipoConta);
        contaDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        contaDTO.setMesConta(11);
        contaDTO.setAnoConta(2021);
        contaDTO.setDescricao("Descricao");
        contaDTO.setValor(100.0);
        contaDTO.setNumeroParcela(1);
        contaDTO.setTotalParcelas(1);
        contaDTO.setRecorrente(false);

        Conta conta = ContaMapper.INSTANCE.contaDTOToConta(contaDTO);
        assertThat(contaDTO).isNotNull();
        assertThat(contaDTO.getId()).isEqualTo(conta.getId());
        assertThat(contaDTO.getUsuario().getId()).isEqualTo(conta.getUsuario().getId());
        assertThat(contaDTO.getTipoConta().getId()).isEqualTo(conta.getTipoConta().getId());
        assertThat(contaDTO.getDataConta()).isEqualTo(conta.getDataConta());
        assertThat(contaDTO.getMesConta()).isEqualTo(conta.getMesConta());
        assertThat(contaDTO.getAnoConta()).isEqualTo(conta.getAnoConta());
        assertThat(contaDTO.getDescricao()).isEqualTo(conta.getDescricao());
        assertThat(contaDTO.getValor()).isEqualTo(conta.getValor());
        assertThat(contaDTO.getNumeroParcela()).isEqualTo(conta.getNumeroParcela());
        assertThat(contaDTO.getTotalParcelas()).isEqualTo(conta.getTotalParcelas());
        assertThat(contaDTO.getRecorrente()).isEqualTo(conta.getRecorrente());
    }

}
