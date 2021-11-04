package br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.service;

import br.com.luisfilipemota.controlegastospessoais.ControlegastospessoaisApplication;
import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.mapper.TipoContaMapper;
import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.repository.TipoContaRepository;
import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.service.TipoContaService;
import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.service.dto.TipoContaDTO;
import javassist.NotFoundException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest(classes = ControlegastospessoaisApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TipoContaServiceTest {

    @Autowired
    TipoContaService tipoContaService;

    @MockBean
    TipoContaRepository tipoContaRepository;

    @MockBean
    TipoContaMapper tipoContaMapper;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testSalvarTipoConta() {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(1L);
        tipoConta.setDescricao("Descricao");

        Mockito.when(tipoContaMapper.tipoContaDTOToTipoContaDto(tipoContaDTO))
                .thenReturn(tipoConta);

        Mockito.when(tipoContaRepository.save(tipoConta))
                .thenReturn(tipoConta);

        Mockito.when(tipoContaMapper.tipoContaToTipoContaDto(tipoConta))
                .thenReturn(tipoContaDTO);

        TipoContaDTO tipoContaSalva = tipoContaService.save(tipoContaDTO);
        asserts(tipoContaDTO, tipoContaSalva);
    }

    @Test
    public void testAtualizarTipoContaComContaEncontrada() throws NotFoundException {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(1L);
        tipoConta.setDescricao("Descricao");

        Optional<TipoConta> tipoContaOptional = Optional.of(tipoConta);


        Mockito.when(tipoContaRepository.findById(1L))
                .thenReturn(tipoContaOptional);

        Mockito.when(tipoContaRepository.save(tipoConta))
                .thenReturn(tipoConta);

        Mockito.when(tipoContaMapper.tipoContaToTipoContaDto(tipoConta))
                .thenReturn(tipoContaDTO);

        TipoContaDTO tipoContaSalva = tipoContaService.update(1L, tipoContaDTO);
        asserts(tipoContaDTO, tipoContaSalva);
    }


    @Test
    public void testAtualizarTipoContaComTipoContaNaoEncontrada() throws NotFoundException {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(1L);
        tipoConta.setDescricao("Descricao");

        Mockito.when(tipoContaRepository.findById(1L))
                .thenReturn(Optional.<TipoConta>empty());

        Mockito.when(tipoContaRepository.save(tipoConta))
                .thenReturn(tipoConta);

        Mockito.when(tipoContaMapper.tipoContaToTipoContaDto(tipoConta))
                .thenReturn(tipoContaDTO);

        try {
            TipoContaDTO tipoContaSalva = tipoContaService.update(1L, tipoContaDTO);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Tipo de Conta não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    private void asserts(TipoContaDTO tipoContaDTO, TipoContaDTO tipoContaSalva) {
        assertThat(tipoContaSalva).isNotNull();
        assertThat(tipoContaSalva.getId()).isNotNull();
        assertThat(tipoContaSalva.getDescricao()).isEqualTo(tipoContaDTO.getDescricao());
    }

    @Test
    public void testDeletarTipoContaComContaEncontrada() throws NotFoundException {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(1L);
        tipoConta.setDescricao("Descricao");

        Mockito.when(tipoContaRepository.findById(1L))
                .thenReturn(Optional.of(tipoConta));

        Mockito.when(tipoContaMapper.tipoContaToTipoContaDto(tipoConta))
                .thenReturn(tipoContaDTO);

        try {
            tipoContaService.delete(1L);
        } catch (NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeletarTipoContaComTipoContaNaoEncontrada() {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(1L);
        tipoConta.setDescricao("Descricao");

        Mockito.when(tipoContaRepository.findById(1L))
                .thenReturn(Optional.<TipoConta>empty());

        Mockito.when(tipoContaMapper.tipoContaToTipoContaDto(tipoConta))
                .thenReturn(tipoContaDTO);

        try {
            tipoContaService.delete(1L);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Tipo de Conta não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    @Test
    public void testPesquisaPorIdComTipoContaEncontrada() throws NotFoundException {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(1L);
        tipoConta.setDescricao("Descricao");

        Mockito.when(tipoContaRepository.findById(1L))
                .thenReturn(Optional.of(tipoConta));

        Mockito.when(tipoContaMapper.tipoContaToTipoContaDto(tipoConta))
                .thenReturn(tipoContaDTO);

        TipoContaDTO tipoContaSalva = tipoContaService.findById(1L);

        asserts(tipoContaDTO, tipoContaSalva);
    }

    @Test
    public void testPesquisaPorIdComTipoContaNaoEncontrada() {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(1L);
        tipoConta.setDescricao("Descricao");

        Mockito.when(tipoContaRepository.findById(1L))
                .thenReturn(Optional.<TipoConta>empty());

        Mockito.when(tipoContaMapper.tipoContaToTipoContaDto(tipoConta))
                .thenReturn(tipoContaDTO);

        try {
            TipoContaDTO tipoContaSalva = tipoContaService.findById(1L);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Tipo de Conta não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    @Test
    public void testPesquisaTodosTiposContas() {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(1L);
        tipoConta.setDescricao("Descricao");

        Mockito.when(tipoContaRepository.findAll())
                .thenReturn(Arrays.asList(tipoConta));

        Mockito.when(tipoContaMapper.tipoContaToTipoContaDto(tipoConta))
                .thenReturn(tipoContaDTO);


        List<TipoContaDTO> tipoContaSalva = tipoContaService.findAll();

        assertThat(tipoContaSalva).isNotNull();
        assertThat(tipoContaSalva.size()).isEqualTo(1);
        assertThat(tipoContaSalva.get(0).getDescricao()).isEqualTo(tipoContaDTO.getDescricao());

    }
}
