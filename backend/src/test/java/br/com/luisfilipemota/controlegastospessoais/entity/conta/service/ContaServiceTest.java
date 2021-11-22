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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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

    private final UUID UUID_TEST  = UUID.randomUUID();

    @Test
    public void testSalvarConta() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

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

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID_TEST);

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
    public void testAtualizarContaComContaEncontrada() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

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

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID_TEST);

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
    public void testAtualizarContaComContaNaoEncontrada() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

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

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID_TEST);

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

        Mockito.when(contaRepository.findById(UUID_TEST))
                .thenReturn(Optional.<Conta>empty());

        Mockito.when(contaRepository.save(conta))
                .thenReturn(conta);

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        try {
            ContaDTO tipoContaSalva = contaService.update(UUID_TEST, contaDTO);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Conta não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
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

    @Test
    public void testDeletarContaComContaEncontrada() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

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

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID_TEST);

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
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

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

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID_TEST);

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
        Mockito.when(contaRepository.findById(UUID_TEST))
                .thenReturn(Optional.<Conta>empty());

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
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

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

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID_TEST);

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

        Mockito.when(contaRepository.findById(UUID_TEST))
                .thenReturn(Optional.of(conta));

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        ContaDTO contaSalva = contaService.findById(UUID_TEST);

        asserts(contaDTO, contaSalva);
    }

    @Test
    public void testPesquisaPorIdComContaNaoEncontrada() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

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

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID_TEST);

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

        Mockito.when(contaRepository.findById(UUID_TEST))
                .thenReturn(Optional.<Conta>empty());

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        try {
            ContaDTO contaSalva = contaService.findById(UUID_TEST);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Conta não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    @Test
    public void testPesquisaTodosContas() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

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

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID_TEST);

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

        Mockito.when(contaRepository.findAll())
                .thenReturn(Arrays.asList(conta));

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);


        List<ContaDTO> contaSalva = contaService.findAll();

        assertThat(contaSalva).isNotNull();
        assertThat(contaSalva.size()).isEqualTo(1);
        assertThat(contaSalva.get(0).getId()).isEqualTo(conta.getId());
        assertThat(contaSalva.get(0).getUsuario().getId()).isEqualTo(conta.getUsuario().getId());
        assertThat(contaSalva.get(0).getTipoConta().getId()).isEqualTo(conta.getTipoConta().getId());
        assertThat(contaSalva.get(0).getDataConta()).isEqualTo(conta.getDataConta());
        assertThat(contaSalva.get(0).getMesConta()).isEqualTo(conta.getMesConta());
        assertThat(contaSalva.get(0).getAnoConta()).isEqualTo(conta.getAnoConta());
        assertThat(contaSalva.get(0).getDescricao()).isEqualTo(conta.getDescricao());
        assertThat(contaSalva.get(0).getValor()).isEqualTo(conta.getValor());
        assertThat(contaSalva.get(0).getNumeroParcela()).isEqualTo(conta.getNumeroParcela());
        assertThat(contaSalva.get(0).getTotalParcelas()).isEqualTo(conta.getTotalParcelas());
        assertThat(contaSalva.get(0).getRecorrente()).isEqualTo(conta.getRecorrente());

    }


    @Test
    public void testPesquisaTodosContasPorTipoConta() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);
        tipoContaDTO.setDescricao("Descricao");

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

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID_TEST);
        tipoConta.setDescricao("Descricao");

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

        ContaTipoContaDTO contaSomatorioDTO = new ContaTipoContaDTO();
        contaSomatorioDTO.setContas(Arrays.asList(contaDTO));
        contaSomatorioDTO.setSomatorio(contaDTO.getValor());
        contaSomatorioDTO.setTipoContaId(UUID_TEST);
        contaSomatorioDTO.setNomeTipoConta(tipoConta.getDescricao());

        Mockito.when(contaRepository.findAllByTipoContaIdOrderByDataConta(Mockito.any(UUID.class)))
                .thenReturn(Arrays.asList(conta));

        Mockito.when(tipoContaRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(tipoConta));

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);


        ContaTipoContaDTO contaSalva = contaService.findAllByTipoConta(UUID_TEST);

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

    @Test
    public void testPesquisaTodosContasPorTipoContaAnoContaMesAno() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);
        tipoContaDTO.setDescricao("Descricao");

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

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(UUID_TEST);
        tipoConta.setDescricao("Descricao");

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

        ContaTipoContaDTO contaSomatorioDTO = new ContaTipoContaDTO();
        contaSomatorioDTO.setContas(Arrays.asList(contaDTO));
        contaSomatorioDTO.setSomatorio(contaDTO.getValor());
        contaSomatorioDTO.setTipoContaId(UUID_TEST);
        contaSomatorioDTO.setNomeTipoConta(tipoConta.getDescricao());

        Mockito.when(contaRepository.findAllByTipoContaIdAndMesContaAndAnoContaOrderByDataConta(UUID_TEST, 1, 1))
                .thenReturn(Arrays.asList(conta));

        Mockito.when(tipoContaRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(tipoConta));

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);


        ContaTipoContaDTO contaSalva = contaService.findAllByTipoContaIdAndMesContaAndAnoConta(UUID_TEST, 1, 1);

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
}
