package br.com.luisfilipemota.controlegastospessoais.entity.conta.service;

import br.com.luisfilipemota.controlegastospessoais.ControlegastospessoaisApplication;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.mapper.ContaMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.model.Conta;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.repository.ContaRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto.TipoContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import javassist.NotFoundException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest(classes = ControlegastospessoaisApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ContaServiceTest {

    @Autowired
    ContaService contaService;

    @MockBean
    ContaRepository contaRepository;

    @MockBean
    ContaMapper contaMapper;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testSalvarConta() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);
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
        usuarioDTO.setId(1L);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);
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

        Optional<Conta> contaOptional = Optional.of(conta);


        Mockito.when(contaRepository.findById(1L))
                .thenReturn(contaOptional);

        Mockito.when(contaRepository.save(conta))
                .thenReturn(conta);

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        Mockito.when(contaMapper.contaDTOToConta(contaDTO))
                .thenReturn(conta);


        ContaDTO contaSalva = contaService.update(1L, contaDTO);
        asserts(contaDTO, contaSalva);
    }


    @Test
    public void testAtualizarContaComContaNaoEncontrada() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);
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

        Mockito.when(contaRepository.findById(1L))
                .thenReturn(Optional.<Conta>empty());

        Mockito.when(contaRepository.save(conta))
                .thenReturn(conta);

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        try {
            ContaDTO tipoContaSalva = contaService.update(1L, contaDTO);
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
        usuarioDTO.setId(1L);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);
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

        Mockito.when(contaRepository.findById(1L))
                .thenReturn(Optional.of(conta));

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        try {
            contaService.delete(1L);
        } catch (NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeletarContaComContaNaoEncontrada() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);
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
        Mockito.when(contaRepository.findById(1L))
                .thenReturn(Optional.<Conta>empty());

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        try {
            contaService.delete(1L);
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
        usuarioDTO.setId(1L);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);
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

        Mockito.when(contaRepository.findById(1L))
                .thenReturn(Optional.of(conta));

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        ContaDTO contaSalva = contaService.findById(1L);

        asserts(contaDTO, contaSalva);
    }

    @Test
    public void testPesquisaPorIdComContaNaoEncontrada() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);
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

        Mockito.when(contaRepository.findById(1L))
                .thenReturn(Optional.<Conta>empty());

        Mockito.when(contaMapper.contaToContaDto(conta))
                .thenReturn(contaDTO);

        try {
            ContaDTO contaSalva = contaService.findById(1L);
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
        usuarioDTO.setId(1L);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);
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
}
