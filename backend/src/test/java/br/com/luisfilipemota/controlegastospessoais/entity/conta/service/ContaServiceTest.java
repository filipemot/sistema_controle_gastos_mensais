package br.com.luisfilipemota.controlegastospessoais.entity.conta.service;

import br.com.luisfilipemota.controlegastospessoais.ControlegastospessoaisApplication;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.mapper.ContaMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.model.Conta;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.repository.ContaRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaTipoContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.repository.TipoContaRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto.TipoContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ControlegastospessoaisApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ContaServiceTest {

    @Autowired
    ContaService contaService;

    @MockBean
    TipoContaRepository tipoContaRepository;

    @MockBean
    ContaRepository contaRepository;

    @MockBean
    ContaMapper contaMapper;

    private final UUID UUID_TEST = UUID.randomUUID();

    private final UUID UUID_TEST_DIFERENTE = UUID.randomUUID();

    @Test
    public void testSalvarConta() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);

        Mockito.when(contaMapper.contaDTOToConta(contaDTO))
                .thenReturn(conta);

        Mockito.when(contaRepository.save(conta))
                .thenReturn(conta);

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        ContaDTO contaSalva = contaService.save(contaDTO);
        asserts(contaDTO, contaSalva);
    }

    @Test
    public void testSalvarContaUsuarioNaoEncontradoOuTipoContaNaoEncontrado() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST_DIFERENTE);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);

        Mockito.when(contaMapper.contaDTOToConta(contaDTO))
                .thenReturn(conta);

        Mockito.when(contaRepository.save(conta))
                .thenThrow(DataIntegrityViolationException.class);

        try {
            contaService.save(contaDTO);
        } catch (DataIntegrityViolationException e) {
            assertFalse(false);
        } catch (Exception e) {
            Assertions.fail();
        }

    }

    @Test
    public void testAtualizarContaComContaEncontrada() throws NotFoundException {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);

        Optional<Conta> contaOptional = Optional.of(conta);

        Mockito.when(contaRepository.findById(UUID_TEST))
                .thenReturn(contaOptional);

        Mockito.when(contaRepository.save(conta))
                .thenReturn(conta);

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        Mockito.when(contaMapper.contaDTOToConta(contaDTO))
                .thenReturn(conta);


        ContaDTO contaSalva = contaService.update(UUID_TEST, contaDTO);
        asserts(contaDTO, contaSalva);
    }

    @Test
    public void testAtualizarContaComTipoContaOuUsuarioNaoEncontrado() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST_DIFERENTE);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST_DIFERENTE);

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);

        Optional<Conta> contaOptional = Optional.of(conta);

        Mockito.when(contaRepository.findById(UUID_TEST))
                .thenReturn(contaOptional);

        Mockito.when(contaRepository.save(conta))
                .thenThrow(DataIntegrityViolationException.class);

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        Mockito.when(contaMapper.contaDTOToConta(contaDTO))
                .thenReturn(conta);

        try {
            contaService.update(UUID_TEST, contaDTO);
        } catch (DataIntegrityViolationException e) {
            assertTrue(true);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void testAtualizarContaComContaNaoEncontrada(){
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);

        Mockito.when(contaRepository.findById(UUID_TEST))
                .thenReturn(Optional.empty());

        Mockito.when(contaRepository.save(conta))
                .thenReturn(conta);

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        try {
            contaService.update(UUID_TEST, contaDTO);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Conta não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    @Test
    public void testDeletarContaComContaEncontrada() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);

        Mockito.when(contaRepository.findById(UUID_TEST))
                .thenReturn(Optional.of(conta));

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        try {
            contaService.delete(UUID_TEST);
        } catch (NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeletarContaComContaNaoEncontrada() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);
        Mockito.when(contaRepository.findById(UUID_TEST))
                .thenReturn(Optional.empty());

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        try {
            contaService.delete(UUID_TEST);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Conta não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    @Test
    public void testPesquisaPorIdComContaEncontrada() throws NotFoundException {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);

        Mockito.when(contaRepository.findById(UUID_TEST))
                .thenReturn(Optional.of(conta));

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        ContaDTO contaSalva = contaService.findById(UUID_TEST);

        asserts(contaDTO, contaSalva);
    }

    @Test
    public void testPesquisaPorIdComContaNaoEncontrada() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);

        Mockito.when(contaRepository.findById(UUID_TEST))
                .thenReturn(Optional.empty());

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        try {
            contaService.findById(UUID_TEST);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Conta não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    @Test
    public void testPesquisaTodosContas() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);

        Mockito.when(contaRepository.findAll())
                .thenReturn(Collections.singletonList(conta));

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);


        List<ContaDTO> contaSalva = contaService.findAll();

        asserts(contaDTO, contaSalva.get(0));
        assertThat(contaSalva).isNotNull();
        assertThat(contaSalva.size()).isEqualTo(1);

    }

    @Test
    public void testPesquisaTodosContasPorTipoConta() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);

        Mockito.when(contaRepository.findAllByTipoContaIdOrderByDataConta(Mockito.any(UUID.class)))
                .thenReturn(Collections.singletonList(conta));

        Mockito.when(tipoContaRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(tipoConta));

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        ContaTipoContaDTO contaSalva = contaService.findAllByTipoConta(UUID_TEST);

        asserts(tipoContaDTO, conta, contaSalva);
    }

    @Test
    public void testPesquisaTodosContasPorTipoContaAnoContaMesAno() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Usuario usuario = getUsuario();
        TipoConta tipoConta = getTipoConta();

        Conta conta = getConta(usuario, tipoConta);

        Mockito.when(contaRepository.findAllByTipoContaIdAndMesContaAndAnoContaOrderByDataConta(UUID_TEST, 1, 1))
                .thenReturn(Collections.singletonList(conta));

        Mockito.when(tipoContaRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(tipoConta));

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);


        ContaTipoContaDTO contaSalva = contaService.findAllByTipoContaIdAndMesContaAndAnoConta(UUID_TEST, 1, 1);

        asserts(tipoContaDTO, conta, contaSalva);

    }

    private void asserts(TipoContaDTO tipoContaDTO, Conta conta, ContaTipoContaDTO contaSalva) {
        assertThat(contaSalva).isNotNull();
        assertThat(contaSalva.getContas().size()).isEqualTo(1);
        assertThat(contaSalva.getSomatorio()).isEqualTo(conta.getValor());
        assertThat(contaSalva.getNomeTipoConta()).isEqualTo(tipoContaDTO.getDescricao());
        assertThat(contaSalva.getTipoContaId()).isEqualTo(tipoContaDTO.getId());
        assertThat(contaSalva.getContas().get(0).getId()).isEqualTo(conta.getId());
        assertThat(contaSalva.getContas().get(0).getUsuario().getId()).isEqualTo(conta.getUsuario().getId());
        assertThat(contaSalva.getContas().get(0).getTipoConta().getId()).isEqualTo(conta.getTipoConta().getId());
        assertThat(contaSalva.getContas().get(0).getDataConta()).isEqualTo(conta.getDataConta());
        assertThat(contaSalva.getContas().get(0).getMesConta()).isEqualTo(conta.getMesConta());
        assertThat(contaSalva.getContas().get(0).getAnoConta()).isEqualTo(conta.getAnoConta());
        assertThat(contaSalva.getContas().get(0).getDescricao()).isEqualTo(conta.getDescricao());
        assertThat(contaSalva.getContas().get(0).getValor()).isEqualTo(conta.getValor());
        assertThat(contaSalva.getContas().get(0).getNumeroParcela()).isEqualTo(conta.getNumeroParcela());
        assertThat(contaSalva.getContas().get(0).getTotalParcelas()).isEqualTo(conta.getTotalParcelas());
        assertThat(contaSalva.getContas().get(0).getRecorrente()).isEqualTo(conta.getRecorrente());
    }

    private Conta getConta(Usuario usuario, TipoConta tipoConta) {
        Conta conta = new Conta();
        conta.setId(UUID_TEST);
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
        return conta;
    }

    private TipoConta getTipoConta() {
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID_TEST);
        tipoConta.setDescricao("Descricao");
        return tipoConta;
    }

    private TipoContaDTO getTipoContaDTO() {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);
        tipoContaDTO.setDescricao("Descricao");
        return tipoContaDTO;
    }

    private Usuario getUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        return usuario;
    }

    private ContaDTO getContaDTO(UsuarioDTO usuarioDTO, TipoContaDTO tipoContaDTO) {
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(UUID_TEST);
        contaDTO.setUsuario(usuarioDTO);
        contaDTO.setTipoConta(tipoContaDTO);
        contaDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        contaDTO.setMesConta(11);
        contaDTO.setAnoConta(2021);
        contaDTO.setDescricao("Descricao");
        contaDTO.setValor(100.0);
        contaDTO.setNumeroParcela(1);
        contaDTO.setTotalParcelas(1);
        contaDTO.setRecorrente(false);
        return contaDTO;
    }

    private UsuarioDTO getUsuarioDTO(UUID uuid_test) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(uuid_test);
        return usuarioDTO;
    }

    private void asserts(ContaDTO contaDTO, ContaDTO conta) {
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
