package br.com.luisfilipemota.controlegastospessoais.entity.recebidos.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service.RecebidosService;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service.dto.RecebidosDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import br.com.luisfilipemota.controlegastospessoais.util.converter.LocalDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javassist.NotFoundException;
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

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecebidosResource.class)
public class RecebidosResourceTest {
    @Autowired
    public MockMvc mvc;

    @MockBean
    RecebidosService recebidosService;

    private final UUID UUID_TEST  = UUID.randomUUID();
    private final UUID UUID_TEST_DIFERENTE  = UUID.randomUUID();

    @Test
    public void testPesquisaPorRecebidosComIdIgualUUIDTest() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Mockito.when(recebidosService.findById(UUID_TEST))
                .thenReturn(recebidosDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/recebidos/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isOk(), true);
    }

    @Test
    public void testPesquisaPorRecebidosRecebidosNaoEncontrado() throws Exception {

        UUID uuid = UUID.randomUUID();
        Mockito.when(recebidosService.findById(uuid))
                .thenThrow(NotFoundException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/recebidos/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isNotFound(), false);
    }

    @Test
    public void testSalvarRecebidos() throws Exception {
        GsonBuilder gsonBuilder = getGsonBuilder();

        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Mockito.when(recebidosService.save(Mockito.any(RecebidosDTO.class)))
                .thenReturn(recebidosDTO);

        String requestJson = getJson(gsonBuilder, recebidosDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/api/recebidos")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isCreated(), true);


    }

    @Test
    public void testSalvarRecebidosComUsuarioNaoEncontrado() throws Exception {
        GsonBuilder gsonBuilder = getGsonBuilder();

        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST_DIFERENTE);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Mockito.when(recebidosService.save(Mockito.any(RecebidosDTO.class)))
                .thenThrow(DataIntegrityViolationException.class);

        String requestJson = getJson(gsonBuilder, recebidosDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/api/recebidos")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isBadRequest(), false);
    }

    @Test
    public void testAtualizarRecebidosComRecebidosEncontrado() throws Exception {
        GsonBuilder gsonBuilder = getGsonBuilder();

        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        String requestJson = getJson(gsonBuilder, recebidosDTO);

        Mockito.when(recebidosService.update(Mockito.any(UUID.class), Mockito.any(RecebidosDTO.class)))
                .thenReturn(recebidosDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/recebidos/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isOk(), true);
    }

    @Test
    public void testAtualizarRecebidosNaoEncontrado() throws Exception {
        GsonBuilder gsonBuilder = getGsonBuilder();

        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);
        String requestJson = getJson(gsonBuilder, recebidosDTO);

        Mockito.when(recebidosService.update(Mockito.any(UUID.class), Mockito.any(RecebidosDTO.class)))
                .thenThrow(NotFoundException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/recebidos/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isNotFound(), false);
    }

    @Test
    public void testAtualizarRecebidosComUsuarioNaoEncontrado() throws Exception {
        GsonBuilder gsonBuilder = getGsonBuilder();

        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST_DIFERENTE);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);
        String requestJson = getJson(gsonBuilder, recebidosDTO);

        Mockito.when(recebidosService.update(Mockito.any(UUID.class), Mockito.any(RecebidosDTO.class)))
                .thenThrow(DataIntegrityViolationException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/recebidos/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isBadRequest(), false);
    }

    @Test
    public void testDeletarPorRecebidosComIdIgualUUIDTest() throws Exception {
        Mockito.doNothing().when(recebidosService).delete(Mockito.any(UUID.class));

        ResultActions result = mvc.perform(MockMvcRequestBuilders.delete("/api/recebidos/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertsRequest(result, status().isOk(), false);
    }

    @Test
    public void testDeletarPorRecebidosComRecebidosNaoEncontrado() throws Exception {

        doThrow(NotFoundException.class).when(recebidosService).delete(Mockito.any(UUID.class));

        ResultActions result = mvc.perform(MockMvcRequestBuilders.delete("/api/recebidos/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        assertsRequest(result, status().isNotFound(), false);
    }

    @Test
    public void testPesquisaTodosRecebidos() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);

        RecebidosDTO recebidosDTO = getRecebidosDTO(usuarioDTO);

        Mockito.when(recebidosService.findAll())
                .thenReturn(Collections.singletonList(recebidosDTO));

        mvc.perform(MockMvcRequestBuilders.get("/api/recebidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].usuario.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$[0].dataRecebido", is("2015-11-04 17:09:55")))
                .andExpect(jsonPath("$[0].mesRecebido", is(11)))
                .andExpect(jsonPath("$[0].anoRecebido", is(2021)))
                .andExpect(jsonPath("$[0].descricao", is("Descricao")))
                .andExpect(jsonPath("$[0].valor", is(100.0)));
    }

    private String getJson(GsonBuilder gsonBuilder, RecebidosDTO recebidosDTO) {
        Gson gson = gsonBuilder.create();
        return gson.toJson(recebidosDTO);
    }

    private void assertsRequest(ResultActions result, ResultMatcher status, Boolean isContent) throws Exception {

        if (isContent) {
            result.andExpect(status)
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.usuario.id", is(UUID_TEST.toString())))
                    .andExpect(jsonPath("$.dataRecebido", is("2015-11-04 17:09:55")))
                    .andExpect(jsonPath("$.mesRecebido", is(11)))
                    .andExpect(jsonPath("$.anoRecebido", is(2021)))
                    .andExpect(jsonPath("$.descricao", is("Descricao")))
                    .andExpect(jsonPath("$.valor", is(100.0)));
        } else {
            result.andExpect(status);
        }
    }

    private GsonBuilder getGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());
        return gsonBuilder;
    }

    private UsuarioDTO getUsuarioDTO(UUID uuid_test) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(uuid_test);
        return usuarioDTO;
    }

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
