package br.com.luisfilipemota.controlegastospessoais.entity.usuario.service;

import br.com.luisfilipemota.controlegastospessoais.ControlegastospessoaisApplication;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.mapper.UsuarioMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.repository.UsuarioRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import com.google.common.hash.Hashing;
import javassist.NotFoundException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testSalvarUsuario() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = Hashing.sha256()
                .hashString(usuarioDTO.getSenhaUsuario(), StandardCharsets.UTF_8)
                .toString();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome");
        usuario.setId(1L);
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
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = Hashing.sha256()
                .hashString(usuarioDTO.getSenhaUsuario(), StandardCharsets.UTF_8)
                .toString();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome");
        usuario.setId(1L);
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Optional<Usuario> usuarioOptional = Optional.of(usuario);


        Mockito.when(usuarioRepository.findById(1L))
                .thenReturn(usuarioOptional);

        Mockito.when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        UsuarioDTO tipoContaSalva = usuarioService.update(1L, usuarioDTO);
        asserts(usuarioDTO, tipoContaSalva);
    }


    @Test
    public void testAtualizarUsuarioComUsuarioNaoEncontrado() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = Hashing.sha256()
                .hashString(usuarioDTO.getSenhaUsuario(), StandardCharsets.UTF_8)
                .toString();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome");
        usuario.setId(1L);
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findById(1L))
                .thenReturn(Optional.<Usuario>empty());

        Mockito.when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        try {
            UsuarioDTO usuarioSalvo = usuarioService.update(1L, usuarioDTO);
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
    public void testDeletarUsuarioComUsuarioEncontrado() throws NotFoundException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = Hashing.sha256()
                .hashString(usuarioDTO.getSenhaUsuario(), StandardCharsets.UTF_8)
                .toString();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome");
        usuario.setId(1L);
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        try {
            usuarioService.delete(1L);
        } catch (NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeletarTipoContaComContaNaoEncontrada() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = Hashing.sha256()
                .hashString(usuarioDTO.getSenhaUsuario(), StandardCharsets.UTF_8)
                .toString();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome");
        usuario.setId(1L);
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findById(1L))
                .thenReturn(Optional.<Usuario>empty());

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        try {
            usuarioService.delete(1L);
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
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = Hashing.sha256()
                .hashString(usuarioDTO.getSenhaUsuario(), StandardCharsets.UTF_8)
                .toString();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome");
        usuario.setId(1L);
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        UsuarioDTO tipoContaSalva = usuarioService.findById(1L);

        asserts(usuarioDTO, tipoContaSalva);
    }

    @Test
    public void testPesquisaPorIdComUsuarioNaoEncontrado() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = Hashing.sha256()
                .hashString(usuarioDTO.getSenhaUsuario(), StandardCharsets.UTF_8)
                .toString();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome");
        usuario.setId(1L);
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findById(1L))
                .thenReturn(Optional.<Usuario>empty());

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);

        try {
            UsuarioDTO usuarioSalva = usuarioService.findById(1L);
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
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        String senhaHash = Hashing.sha256()
                .hashString(usuarioDTO.getSenhaUsuario(), StandardCharsets.UTF_8)
                .toString();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome");
        usuario.setId(1L);
        usuario.setSenha(senhaHash);
        usuario.setEmail("Email");

        Mockito.when(usuarioRepository.findAll())
                .thenReturn(Arrays.asList(usuario));

        Mockito.when(usuarioMapper.usuarioToUsuarioDto(usuario))
                .thenReturn(usuarioDTO);


        List<UsuarioDTO> usuarioSalvo = usuarioService.findAll();

        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.size()).isEqualTo(1);
        assertThat(usuarioSalvo.get(0).getId()).isEqualTo(usuario.getId());
        assertThat(usuarioSalvo.get(0).getNome()).isEqualTo(usuario.getNome());
        assertThat(usuarioSalvo.get(0).getEmail()).isEqualTo(usuario.getEmail());
    }
}
