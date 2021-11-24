package br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service;

import br.com.luisfilipemota.controlegastospessoais.ControlegastospessoaisApplication;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.mapper.RecebidosMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.model.Recebidos;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.repository.RecebidosRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service.dto.RecebidosDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import javassist.NotFoundException;
import org.jetbrains.annotations.NotNull;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    private final UUID UUID_TEST = UUID.randomUUID();
    private final UUID UUID_TEST_DIFERENTE = UUID.randomUUID();

    @Test
    public void testSalvarRecebidos() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Usuario usuario = getUsuario();

        Recebidos recebidos = getRecebidos(usuario);

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
    public void testSalvarRecebidosUsuarioNaoEncontrado() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST_DIFERENTE);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Usuario usuario = getUsuario();

        Recebidos recebidos = getRecebidos(usuario);

        Mockito.when(recebidosMapper.recebidosDTOToRecebidos(recebidosDTO))
                .thenReturn(recebidos);

        Mockito.when(recebidosRepository.save(recebidos))
                .thenThrow(DataIntegrityViolationException.class);

        Mockito.when(recebidosMapper.recebidosToRecebidosDto(recebidos))
                .thenReturn(recebidosDTO);

        try {
            recebidosService.save(recebidosDTO);
        } catch (DataIntegrityViolationException e) {
            assertTrue(true);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void testAtualizarRecebidosComRecebidosEncontrado() throws NotFoundException {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Usuario usuario = getUsuario();

        Recebidos recebidos = getRecebidos(usuario);

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
    public void testAtualizarRecibosComRecibosNaoEncontrado() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Usuario usuario = getUsuario();

        Recebidos recebidos = getRecebidos(usuario);

        Mockito.when(recebidosRepository.findById(UUID_TEST))
                .thenReturn(Optional.empty());

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

    @Test
    public void testDeletarRecibosComRecibosEncontrada() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Usuario usuario = getUsuario();

        Recebidos recebidos = getRecebidos(usuario);

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
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Usuario usuario = getUsuario();

        Recebidos recebidos = getRecebidos(usuario);
        Mockito.when(recebidosRepository.findById(UUID_TEST))
                .thenReturn(Optional.empty());

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
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Usuario usuario = getUsuario();

        Recebidos recebidos = getRecebidos(usuario);

        Mockito.when(recebidosRepository.findById(UUID_TEST))
                .thenReturn(Optional.of(recebidos));

        Mockito.when(recebidosMapper.recebidosToRecebidosDto(recebidos))
                .thenReturn(recebidosDTO);

        RecebidosDTO recibosSalvo = recebidosService.findById(UUID_TEST);

        asserts(recebidosDTO, recibosSalvo);
    }

    @Test
    public void testPesquisaPorIdComRecibosNaoEncontrado() {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Usuario usuario = getUsuario();

        Recebidos recebidos = getRecebidos(usuario);

        Mockito.when(recebidosRepository.findById(UUID_TEST))
                .thenReturn(Optional.empty());

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
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Usuario usuario = getUsuario();

        Recebidos recebidos = getRecebidos(usuario);

        Mockito.when(recebidosRepository.findAll())
                .thenReturn(Collections.singletonList(recebidos));

        Mockito.when(recebidosMapper.recebidosToRecebidosDto(recebidos))
                .thenReturn(recebidosDTO);

        List<RecebidosDTO> listaRecebidos = recebidosService.findAll();

        asserts(recebidosDTO, listaRecebidos.get(0));

        assertThat(listaRecebidos).isNotNull();
        assertThat(listaRecebidos.size()).isEqualTo(1);
    }
    @NotNull
    private UsuarioDTO getUsuarioDTO(UUID uuid_test) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(uuid_test);
        return usuarioDTO;
    }

    @NotNull
    private Usuario getUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        return usuario;
    }

    private void asserts(RecebidosDTO recebidosDTO, RecebidosDTO recebidos) {
        assertThat(recebidosDTO).isNotNull();
        assertThat(recebidosDTO.getId()).isEqualTo(recebidos.getId());
        assertThat(recebidosDTO.getUsuario().getId()).isEqualTo(recebidos.getUsuario().getId());
        assertThat(recebidosDTO.getDataRecebido()).isEqualTo(recebidos.getDataRecebido());
        assertThat(recebidosDTO.getMesRecebido()).isEqualTo(recebidos.getMesRecebido());
        assertThat(recebidosDTO.getAnoRecebido()).isEqualTo(recebidos.getAnoRecebido());
        assertThat(recebidosDTO.getDescricao()).isEqualTo(recebidos.getDescricao());
        assertThat(recebidosDTO.getValor()).isEqualTo(recebidos.getValor());
    }
    
    @NotNull
    private Recebidos getRecebidos(Usuario usuario) {
        Recebidos recebidos = new Recebidos();
        recebidos.setId(UUID_TEST);
        recebidos.setUsuario(usuario);
        recebidos.setDataRecebido(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidos.setMesRecebido(11);
        recebidos.setAnoRecebido(2021);
        recebidos.setDescricao("Descricao");
        recebidos.setValor(100.0);
        return recebidos;
    }

    @NotNull
    private RecebidosDTO getRecebidosDTO(UsuarioDTO usuarioDTO) {
        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataRecebido(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesRecebido(11);
        recebidosDTO.setAnoRecebido(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);
        return recebidosDTO;
    }
}
