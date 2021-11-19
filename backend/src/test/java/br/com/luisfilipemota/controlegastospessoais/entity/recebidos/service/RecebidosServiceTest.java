package br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service;

import br.com.luisfilipemota.controlegastospessoais.ControlegastospessoaisApplication;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.mapper.RecebidosMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.model.Recebidos;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.repository.RecebidosRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service.dto.RecebidosDTO;
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
public class RecebidosServiceTest {

    @Autowired
    RecebidosService recebidosService;

    @MockBean
    RecebidosRepository recebidosRepository;

    @MockBean
    RecebidosMapper recebidosMapper;

    private final UUID UUID_TEST  = UUID.randomUUID();

    @Test
    public void testSalvarRecebidos() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesConta(11);
        recebidosDTO.setAnoConta(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);

        Recebidos recebidos = new Recebidos();
        recebidos.setId(UUID_TEST);
        recebidos.setUsuario(usuario);
        recebidos.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidos.setMesConta(11);
        recebidos.setAnoConta(2021);
        recebidos.setDescricao("Descricao");
        recebidos.setValor(100.0);

        Mockito.when(recebidosMapper.recebidosDTOToRecebidos(recebidosDTO))
                .thenReturn(recebidos);

        Mockito.when(recebidosRepository.save(recebidos))
                .thenReturn(recebidos);

        Mockito.when(recebidosMapper.recebidosToRecebidosDto(recebidos))
                .thenReturn(recebidosDTO);

        RecebidosDTO recebidosSalvo = recebidosService.save(recebidosDTO);
        asserts(recebidosDTO, recebidosSalvo);
    }

    @Test
    public void testAtualizarRecebidosComRecebidosEncontrado() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesConta(11);
        recebidosDTO.setAnoConta(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);

        Recebidos recebidos = new Recebidos();
        recebidos.setId(UUID_TEST);
        recebidos.setUsuario(usuario);
        recebidos.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidos.setMesConta(11);
        recebidos.setAnoConta(2021);
        recebidos.setDescricao("Descricao");
        recebidos.setValor(100.0);

        Optional<Recebidos> recebidosOptional = Optional.of(recebidos);

        Mockito.when(recebidosRepository.findById(UUID_TEST))
                .thenReturn(recebidosOptional);

        Mockito.when(recebidosRepository.save(recebidos))
                .thenReturn(recebidos);

        Mockito.when(recebidosMapper.recebidosToRecebidosDto(recebidos))
                .thenReturn(recebidosDTO);

        Mockito.when(recebidosMapper.recebidosDTOToRecebidos(recebidosDTO))
                .thenReturn(recebidos);


        RecebidosDTO recebidosSalvo = recebidosService.update(UUID_TEST, recebidosDTO);
        asserts(recebidosDTO, recebidosSalvo);
    }


    @Test
    public void testAtualizarRecibosComRecibosNaoEncontrado() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesConta(11);
        recebidosDTO.setAnoConta(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);

        Recebidos recebidos = new Recebidos();
        recebidos.setId(UUID_TEST);
        recebidos.setUsuario(usuario);
        recebidos.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidos.setMesConta(11);
        recebidos.setAnoConta(2021);
        recebidos.setDescricao("Descricao");
        recebidos.setValor(100.0);

        Mockito.when(recebidosRepository.findById(UUID_TEST))
                .thenReturn(Optional.<Recebidos>empty());

        Mockito.when(recebidosRepository.save(recebidos))
                .thenReturn(recebidos);

        Mockito.when(recebidosMapper.recebidosToRecebidosDto(recebidos))
                .thenReturn(recebidosDTO);

        try {
            recebidosService.update(UUID_TEST, recebidosDTO);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Recebidos não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    private void asserts(RecebidosDTO recebidosDTO, RecebidosDTO recebidos) {
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
    public void testDeletarRecibosComRecibosEncontrada() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesConta(11);
        recebidosDTO.setAnoConta(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);

        Recebidos recebidos = new Recebidos();
        recebidos.setId(UUID_TEST);
        recebidos.setUsuario(usuario);
        recebidos.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidos.setMesConta(11);
        recebidos.setAnoConta(2021);
        recebidos.setDescricao("Descricao");
        recebidos.setValor(100.0);

        Mockito.when(recebidosRepository.findById(UUID_TEST))
                .thenReturn(Optional.of(recebidos));

        Mockito.when(recebidosMapper.recebidosToRecebidosDto(recebidos))
                .thenReturn(recebidosDTO);

        try {
            recebidosService.delete(UUID_TEST);
        } catch (NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeletarRecibosComRecibosNaoEncontrada() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesConta(11);
        recebidosDTO.setAnoConta(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);

        Recebidos recebidos = new Recebidos();
        recebidos.setId(UUID_TEST);
        recebidos.setUsuario(usuario);
        recebidos.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidos.setMesConta(11);
        recebidos.setAnoConta(2021);
        recebidos.setDescricao("Descricao");
        recebidos.setValor(100.0);
        Mockito.when(recebidosRepository.findById(UUID_TEST))
                .thenReturn(Optional.<Recebidos>empty());

        Mockito.when(recebidosMapper.recebidosToRecebidosDto(recebidos))
                .thenReturn(recebidosDTO);

        try {
            recebidosService.delete(UUID_TEST);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Recebidos não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    @Test
    public void testPesquisaPorIdComRecibosEncontrado() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesConta(11);
        recebidosDTO.setAnoConta(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);

        Recebidos recebidos = new Recebidos();
        recebidos.setId(UUID_TEST);
        recebidos.setUsuario(usuario);
        recebidos.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidos.setMesConta(11);
        recebidos.setAnoConta(2021);
        recebidos.setDescricao("Descricao");
        recebidos.setValor(100.0);

        Mockito.when(recebidosRepository.findById(UUID_TEST))
                .thenReturn(Optional.of(recebidos));

        Mockito.when(recebidosMapper.recebidosToRecebidosDto(recebidos))
                .thenReturn(recebidosDTO);

        RecebidosDTO recibosSalvo = recebidosService.findById(UUID_TEST);

        asserts(recebidosDTO, recibosSalvo);
    }

    @Test
    public void testPesquisaPorIdComRecibosNaoEncontrado() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesConta(11);
        recebidosDTO.setAnoConta(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);

        Recebidos recebidos = new Recebidos();
        recebidos.setId(UUID_TEST);
        recebidos.setUsuario(usuario);
        recebidos.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidos.setMesConta(11);
        recebidos.setAnoConta(2021);
        recebidos.setDescricao("Descricao");
        recebidos.setValor(100.0);

        Mockito.when(recebidosRepository.findById(UUID_TEST))
                .thenReturn(Optional.<Recebidos>empty());

        Mockito.when(recebidosMapper.recebidosToRecebidosDto(recebidos))
                .thenReturn(recebidosDTO);

        try {
            recebidosService.findById(UUID_TEST);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Recebidos não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    @Test
    public void testPesquisaTodosRecibos() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesConta(11);
        recebidosDTO.setAnoConta(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);

        Recebidos recebidos = new Recebidos();
        recebidos.setId(UUID_TEST);
        recebidos.setUsuario(usuario);
        recebidos.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidos.setMesConta(11);
        recebidos.setAnoConta(2021);
        recebidos.setDescricao("Descricao");
        recebidos.setValor(100.0);

        Mockito.when(recebidosRepository.findAll())
                .thenReturn(Arrays.asList(recebidos));

        Mockito.when(recebidosMapper.recebidosToRecebidosDto(recebidos))
                .thenReturn(recebidosDTO);


        List<RecebidosDTO> listaRecebidos = recebidosService.findAll();

        assertThat(listaRecebidos).isNotNull();
        assertThat(listaRecebidos.size()).isEqualTo(1);
        assertThat(listaRecebidos.get(0).getId()).isEqualTo(recebidos.getId());
        assertThat(listaRecebidos.get(0).getUsuario().getId()).isEqualTo(recebidos.getUsuario().getId());
        assertThat(listaRecebidos.get(0).getDataConta()).isEqualTo(recebidos.getDataConta());
        assertThat(listaRecebidos.get(0).getMesConta()).isEqualTo(recebidos.getMesConta());
        assertThat(listaRecebidos.get(0).getAnoConta()).isEqualTo(recebidos.getAnoConta());
        assertThat(listaRecebidos.get(0).getDescricao()).isEqualTo(recebidos.getDescricao());
        assertThat(listaRecebidos.get(0).getValor()).isEqualTo(recebidos.getValor());
    }
}
