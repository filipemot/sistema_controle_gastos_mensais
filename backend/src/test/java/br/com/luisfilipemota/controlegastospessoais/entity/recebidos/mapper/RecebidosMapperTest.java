package br.com.luisfilipemota.controlegastospessoais.entity.recebidos.mapper;

import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.model.Recebidos;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service.dto.RecebidosDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RecebidosMapperTest {

    @Test
    public void testMapperRecebidosToRecebidosDto() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());

        Recebidos recebidos = new Recebidos();
        recebidos.setId(UUID.randomUUID());
        recebidos.setUsuario(usuario);
        recebidos.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidos.setMesConta(11);
        recebidos.setAnoConta(2021);
        recebidos.setDescricao("Descricao");
        recebidos.setValor(100.0);

        RecebidosDTO recebidosDTO = RecebidosMapper.INSTANCE.recebidosToRecebidosDto(recebidos);
        assertThat(recebidosDTO).isNotNull();
        assertThat(recebidosDTO.getId()).isEqualTo(recebidos.getId());
        assertThat(recebidosDTO.getUsuario().getId()).isEqualTo(recebidos.getUsuario().getId());
        assertThat(recebidosDTO.getDataConta()).isEqualTo(recebidos.getDataConta());
        assertThat(recebidosDTO.getMesConta()).isEqualTo(recebidos.getMesConta());
        assertThat(recebidosDTO.getAnoConta()).isEqualTo(recebidos.getAnoConta());
        assertThat(recebidosDTO.getDescricao()).isEqualTo(recebidos.getDescricao());
        assertThat(recebidosDTO.getValor()).isEqualTo(recebidos.getValor());
    }

    @Test
    public void testMapperRecebidosDTOToRecebidos() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(UUID.randomUUID());

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID.randomUUID());
        recebidosDTO.setUsuario(usuario);
        recebidosDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesConta(11);
        recebidosDTO.setAnoConta(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Recebidos recebidos = RecebidosMapper.INSTANCE.recebidosDTOToRecebidos(recebidosDTO);
        assertThat(recebidosDTO).isNotNull();
        assertThat(recebidosDTO.getId()).isEqualTo(recebidos.getId());
        assertThat(recebidosDTO.getUsuario().getId()).isEqualTo(recebidos.getUsuario().getId());
        assertThat(recebidosDTO.getDataConta()).isEqualTo(recebidos.getDataConta());
        assertThat(recebidosDTO.getMesConta()).isEqualTo(recebidos.getMesConta());
        assertThat(recebidosDTO.getAnoConta()).isEqualTo(recebidos.getAnoConta());
        assertThat(recebidosDTO.getDescricao()).isEqualTo(recebidos.getDescricao());
        assertThat(recebidosDTO.getValor()).isEqualTo(recebidos.getValor());
    }

}
