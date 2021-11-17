package br.com.luisfilipemota.controlegastospessoais.entity.usuario.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.UsuarioService;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import com.google.gson.Gson;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioResource.class)
public class UsuarioResourceTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    UsuarioService usuarioService;

    private final UUID UUID_TEST  = UUID.randomUUID();


    @Test
    public void testPesquisaPorUsuarioComIdIgualUUIDTest() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.findById(UUID_TEST))
                .thenReturn(usuarioDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/usuario/" + UUID_TEST.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome", is("Nome")))
                .andExpect(jsonPath("$.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.email", is("Email")));
    }

    @Test
    public void testPesquisaPorUsuarioComUsuarioNaoEncontrado() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        UUID uuid = UUID.randomUUID();

        Mockito.when(usuarioService.findById(uuid))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get("/api/usuario/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSalvarUsuario() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Gson gson = new Gson();
        String requestJson = gson.toJson(usuarioDTO);

        Mockito.when(usuarioService.save(Mockito.any(UsuarioDTO.class)))
                .thenReturn(usuarioDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/usuario")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome", is("Nome")))
                .andExpect(jsonPath("$.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.email", is("Email")));
    }

    @Test
    public void testAtualizarUsuarioComUsuarioEncontrado() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Gson gson = new Gson();
        String requestJson = gson.toJson(usuarioDTO);

        UsuarioDTO usuarioDTOUpdate = new UsuarioDTO();
        usuarioDTOUpdate.setId(UUID_TEST);
        usuarioDTOUpdate.setNome("Nome");
        usuarioDTOUpdate.setEmail("Email");
        usuarioDTOUpdate.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.update(Mockito.any(UUID.class), Mockito.any(UsuarioDTO.class)))
                .thenReturn(usuarioDTOUpdate);

        mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome", is("Nome")))
                .andExpect(jsonPath("$.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.email", is("Email")));
    }

    @Test
    public void testAtualizarUsuarioComEmailExistente() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Gson gson = new Gson();
        String requestJson = gson.toJson(usuarioDTO);

        UsuarioDTO usuarioDTOUpdate = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.update(Mockito.any(UUID.class), Mockito.any(UsuarioDTO.class)))
                .thenThrow(DataIntegrityViolationException.class);

        mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAtualizarUsuarioComEmailInvalido() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Gson gson = new Gson();
        String requestJson = gson.toJson(usuarioDTO);

        UsuarioDTO usuarioDTOUpdate = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.update(Mockito.any(UUID.class), Mockito.any(UsuarioDTO.class)))
                .thenThrow(ConstraintViolationException.class);

        mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAtualizarUsuarioComUsuarioNaoEncontrado() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Gson gson = new Gson();
        String requestJson = gson.toJson(usuarioDTO);

        UsuarioDTO usuarioDTOUpdate = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.update(Mockito.any(UUID.class), Mockito.any(UsuarioDTO.class)))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testDeletarPorUsuarioComIdIgualUUIDTest() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.doNothing().when(usuarioService).delete(Mockito.any(UUID.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/usuario/" + UUID_TEST.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletarPorUsuarioComUsuarioNaoEncontrado() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        doThrow(NotFoundException.class).when(usuarioService).delete(Mockito.any(UUID.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/usuario/"+UUID_TEST.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testPesquisaTodosUsuarios() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.findAll())
                .thenReturn(Arrays.asList(usuarioDTO));

        mvc.perform(MockMvcRequestBuilders.get("/api/usuario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Nome")))
                .andExpect(jsonPath("$[0].id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$[0].email", is("Email")));
    }
}
