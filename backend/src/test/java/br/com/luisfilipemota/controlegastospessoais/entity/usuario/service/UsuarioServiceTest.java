package br.com.luisfilipemota.controlegastospessoais.entity.usuario.service;

import br.com.luisfilipemota.controlegastospessoais.ControlegastospessoaisApplication;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.mapper.UsuarioMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.repository.UsuarioRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest(classes = ControlegastospessoaisApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @MockBean
    UsuarioRepository usuarioRepository;

    @MockBean
    UsuarioMapper usuarioMapper;

    private final UUID UUID_TEST  = UUID.randomUUID();

    @Test
    public void testSalvarUsuario() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = getSenha(usuarioDTO.getSenhaUsuario());

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        usuario.setNome("Nome");
        usuario.setEmail(senhaHash);


        Mockito.when(usuarioMapper.usuarioDtoToUsuario(usuarioDTO))
                .thenReturn(usuario);

        Mockito.when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        UsuarioDTO usuarioSalvo = usuarioService.save(usuarioDTO);
        asserts(usuarioDTO, usuarioSalvo);
    }

    @Test
    public void testAtualizarUsuarioComUsuarioEncontrado() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = getSenha(usuarioDTO.getSenhaUsuario());

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        usuario.setNome("Nome");
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Optional<Usuario> usuarioOptional = Optional.of(usuario);


        Mockito.when(usuarioRepository.findById(UUID_TEST))
                .thenReturn(usuarioOptional);

        Mockito.when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        UsuarioDTO tipoContaSalva = usuarioService.update(UUID_TEST, usuarioDTO);
        asserts(usuarioDTO, tipoContaSalva);
    }


    @Test
    public void testAtualizarUsuarioComUsuarioNaoEncontrado() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = getSenha(usuarioDTO.getSenhaUsuario());

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        usuario.setNome("Nome");
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findById(UUID_TEST))
                .thenReturn(Optional.empty());

        Mockito.when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        try {
            usuarioService.update(UUID_TEST, usuarioDTO);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Usuario não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    private void asserts(UsuarioDTO usuarioDTO, UsuarioDTO usuarioDTOSalvo) {
        assertThat(usuarioDTO).isNotNull();
        assertThat(usuarioDTO).isNotNull();
        assertThat(usuarioDTO.getId()).isEqualTo(usuarioDTOSalvo.getId());
        assertThat(usuarioDTO.getNome()).isEqualTo(usuarioDTOSalvo.getNome());
        assertThat(usuarioDTO.getEmail()).isEqualTo(usuarioDTOSalvo.getEmail());
    }

    @Test
    public void testDeletarUsuarioComUsuarioEncontrado() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = getSenha(usuarioDTO.getSenhaUsuario());

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        usuario.setNome("Nome");
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findById(UUID_TEST))
                .thenReturn(Optional.of(usuario));

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        try {
            usuarioService.delete(UUID_TEST);
        } catch (NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeletarTipoContaComContaNaoEncontrada() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = getSenha(usuarioDTO.getSenhaUsuario());

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        usuario.setNome("Nome");
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findById(UUID_TEST))
                .thenReturn(Optional.empty());

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        try {
            usuarioService.delete(UUID_TEST);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Usuario não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    @Test
    public void testPesquisaPorIdComTipoContaEncontrada() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = getSenha(usuarioDTO.getSenhaUsuario());

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        usuario.setNome("Nome");
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findById(UUID_TEST))
                .thenReturn(Optional.of(usuario));

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        UsuarioDTO tipoContaSalva = usuarioService.findById(UUID_TEST);

        asserts(usuarioDTO, tipoContaSalva);
    }

    @Test
    public void testPesquisaPorIdComUsuarioNaoEncontrado() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = getSenha(usuarioDTO.getSenhaUsuario());

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        usuario.setNome("Nome");
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findById(UUID_TEST))
                .thenReturn(Optional.empty());

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        try {
            usuarioService.findById(UUID_TEST);
            fail("Falha");
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Usuario não encontrado");
        } catch (Exception e) {
            fail("Falha");
        }
    }

    @Test
    public void testPesquisaTodosTiposContas() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = getSenha(usuarioDTO.getSenhaUsuario());

        Usuario usuario = new Usuario();
        usuario.setId(UUID_TEST);
        usuario.setNome("Nome");
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findAll())
                .thenReturn(Collections.singletonList(usuario));

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);


        List<UsuarioDTO> usuarioSalvo = usuarioService.findAll();

        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.size()).isEqualTo(1);
        assertThat(usuarioSalvo.get(0).getId()).isEqualTo(usuario.getId());
        assertThat(usuarioSalvo.get(0).getNome()).isEqualTo(usuario.getNome());
        assertThat(usuarioSalvo.get(0).getEmail()).isEqualTo(usuario.getEmail());
    }

    private String getSenha(String texto){

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
