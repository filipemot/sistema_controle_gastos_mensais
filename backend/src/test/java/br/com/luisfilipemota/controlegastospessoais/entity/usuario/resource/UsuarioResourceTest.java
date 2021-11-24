package br.com.luisfilipemota.controlegastospessoais.entity.usuario.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.UsuarioService;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import com.google.gson.Gson;
import javassist.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioResource.class)
public class UsuarioResourceTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    UsuarioService usuarioService;

    private final UUID UUID_TEST  = UUID.randomUUID();


    @Test
    public void testPesquisaPorUsuarioComIdIgualUUIDTest() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDto();

        Mockito.when(usuarioService.findById(UUID_TEST))
                .thenReturn(usuarioDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/usuario/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON));

        assertsRequest(result, status().isOk(), true);
    }

    @Test
    public void testPesquisaPorUsuarioComUsuarioNaoEncontrado() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(usuarioService.findById(uuid))
                .thenThrow(NotFoundException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/usuario/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isNotFound(), false);
    }

    @Test
    public void testSalvarUsuario() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDto();

        String requestJson = getJson(usuarioDTO);

        Mockito.when(usuarioService.save(Mockito.any(UsuarioDTO.class)))
                .thenReturn(usuarioDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/api/usuario")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isCreated(), true);
    }

    @Test
    public void testAtualizarUsuarioComUsuarioEncontrado() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDto();

        String requestJson = getJson(usuarioDTO);

        UsuarioDTO usuarioDTOUpdate = getUsuarioDto();

        Mockito.when(usuarioService.update(Mockito.any(UUID.class), Mockito.any(UsuarioDTO.class)))
                .thenReturn(usuarioDTOUpdate);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isOk(), true);
    }


    @Test
    public void testAtualizarUsuarioComEmailExistente() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDTO();

        String requestJson = getJson(usuarioDTO);

        Mockito.when(usuarioService.update(Mockito.any(UUID.class), Mockito.any(UsuarioDTO.class)))
                .thenThrow(DataIntegrityViolationException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isBadRequest(), false);
    }

    @Test
    public void testAtualizarUsuarioComEmailInvalido() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDTO();

        String requestJson = getJson(usuarioDTO);

        Mockito.when(usuarioService.update(Mockito.any(UUID.class), Mockito.any(UsuarioDTO.class)))
                .thenThrow(ConstraintViolationException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isBadRequest(), false);
    }

    @Test
    public void testAtualizarUsuarioComUsuarioNaoEncontrado() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDTO();

        String requestJson = getJson(usuarioDTO);

        Mockito.when(usuarioService.update(Mockito.any(UUID.class), Mockito.any(UsuarioDTO.class)))
                .thenThrow(NotFoundException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/usuario/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isNotFound(), false);
    }


    @Test
    public void testDeletarPorUsuarioComIdIgualUUIDTest() throws Exception {
        Mockito.doNothing().when(usuarioService).delete(Mockito.any(UUID.class));

        ResultActions result = mvc.perform(MockMvcRequestBuilders.delete("/api/usuario/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isOk(), false);
    }

    @Test
    public void testDeletarPorUsuarioComUsuarioNaoEncontrado() throws Exception {
        doThrow(NotFoundException.class).when(usuarioService).delete(Mockito.any(UUID.class));

        ResultActions result = mvc.perform(MockMvcRequestBuilders.delete("/api/usuario/"+ UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        assertsRequest(result, status().isNotFound(), false);
    }

    @Test
    public void testPesquisaTodosUsuarios() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDTO();

        Mockito.when(usuarioService.findAll())
                .thenReturn(Collections.singletonList(usuarioDTO));

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

    private void assertsRequest(ResultActions result, ResultMatcher status, Boolean isContent) throws Exception {

        if (isContent) {
            result.andExpect(status)
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.nome", is("Nome")))
                    .andExpect(jsonPath("$.id", is(UUID_TEST.toString())))
                    .andExpect(jsonPath("$.email", is("Email")));
        } else {
            result.andExpect(status);
        }
    }

    @NotNull
    private UsuarioDTO getUsuarioDTO() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");
        return usuarioDTO;
    }

    private String getJson(UsuarioDTO usuarioDTO) {
        Gson gson = new Gson();
        return gson.toJson(usuarioDTO);
    }

    @NotNull
    private UsuarioDTO getUsuarioDto() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("Email");
        usuarioDTO.setSenhaUsuario("Senha");
        return usuarioDTO;
    }
}
