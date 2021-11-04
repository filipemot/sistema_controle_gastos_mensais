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

    @Test
    public void testPesquisaPorUsuarioComIdIgual1() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.findById(1L))
                .thenReturn(usuarioDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome", is("Nome")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("Email")));
    }

    @Test
    public void testPesquisaPorUsuarioComUsuarioNaoEncontrado() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.findById(2L))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get("/api/usuario/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSalvarUsuario() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
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
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("Email")));
    }

    @Test
    public void testAtualizarUsuarioComUsuarioEncontrado() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Gson gson = new Gson();
        String requestJson = gson.toJson(usuarioDTO);

        UsuarioDTO usuarioDTOUpdate = new UsuarioDTO();
        usuarioDTOUpdate.setId(1L);
        usuarioDTOUpdate.setNome("Nome");
        usuarioDTOUpdate.setEmail("Email");
        usuarioDTOUpdate.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.update(Mockito.any(Long.class), Mockito.any(UsuarioDTO.class)))
                .thenReturn(usuarioDTOUpdate);

        mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + 1L)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome", is("Nome")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("Email")));
    }

    @Test
    public void testAtualizarUsuarioComEmailExistente() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Gson gson = new Gson();
        String requestJson = gson.toJson(usuarioDTO);

        UsuarioDTO usuarioDTOUpdate = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.update(Mockito.any(Long.class), Mockito.any(UsuarioDTO.class)))
                .thenThrow(DataIntegrityViolationException.class);

        mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + 1L)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAtualizarUsuarioComEmailInvalido() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Gson gson = new Gson();
        String requestJson = gson.toJson(usuarioDTO);

        UsuarioDTO usuarioDTOUpdate = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.update(Mockito.any(Long.class), Mockito.any(UsuarioDTO.class)))
                .thenThrow(ConstraintViolationException.class);

        mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + 1L)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAtualizarUsuarioComUsuarioNaoEncontrado() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Gson gson = new Gson();
        String requestJson = gson.toJson(usuarioDTO);

        UsuarioDTO usuarioDTOUpdate = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.when(usuarioService.update(Mockito.any(Long.class), Mockito.any(UsuarioDTO.class)))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + 1L)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testDeletarPorUsuarioComIdIgual1() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        Mockito.doNothing().when(usuarioService).delete(Mockito.any(Long.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletarPorUsuarioComUsuarioNaoEncontrado() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");

        doThrow(NotFoundException.class).when(usuarioService).delete(Mockito.any(Long.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testPesquisaTodosUsuarios() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(1L);
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
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].email", is("Email")));
    }
}
