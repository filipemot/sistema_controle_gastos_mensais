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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecebidosResource.class)
public class RecebidosResourceTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    RecebidosService recebidosService;

    private final UUID UUID_TEST  = UUID.randomUUID();
    private final UUID UUID_TEST_DIFERENTE  = UUID.randomUUID();

    @Test
    public void testPesquisaPorRecebidosComIdIgualUUIDTest() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataRecebido(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesRecebido(11);
        recebidosDTO.setAnoRecebido(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Mockito.when(recebidosService.findById(UUID_TEST))
                .thenReturn(recebidosDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/recebidos/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.usuario.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.dataRecebido", is("2015-11-04 17:09:55")))
                .andExpect(jsonPath("$.mesRecebido", is(11)))
                .andExpect(jsonPath("$.anoRecebido", is(2021)))
                .andExpect(jsonPath("$.descricao", is("Descricao")))
                .andExpect(jsonPath("$.valor", is(100.0)));
    }

    @Test
    public void testPesquisaPorRecebidosRecebidosNaoEncontrado() throws Exception {

        UUID uuid = UUID.randomUUID();
        Mockito.when(recebidosService.findById(uuid))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get("/api/recebidos/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSalvarRecebidos() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataRecebido(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesRecebido(11);
        recebidosDTO.setAnoRecebido(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Mockito.when(recebidosService.save(Mockito.any(RecebidosDTO.class)))
                .thenReturn(recebidosDTO);

        Gson gson = gsonBuilder.create();
        String requestJson = gson.toJson(recebidosDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/recebidos")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.usuario.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.dataRecebido", is("2015-11-04 17:09:55")))
                .andExpect(jsonPath("$.mesRecebido", is(11)))
                .andExpect(jsonPath("$.anoRecebido", is(2021)))
                .andExpect(jsonPath("$.descricao", is("Descricao")))
                .andExpect(jsonPath("$.valor", is(100.0)));

    }

    @Test
    public void testSalvarRecebidosComUsuarioNaoEncontrado() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST_DIFERENTE);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataRecebido(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesRecebido(11);
        recebidosDTO.setAnoRecebido(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Mockito.when(recebidosService.save(Mockito.any(RecebidosDTO.class)))
                .thenThrow(DataIntegrityViolationException.class);

        Gson gson = gsonBuilder.create();
        String requestJson = gson.toJson(recebidosDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/recebidos")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testAtualizarRecebidosComRecebidosEncontrado() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataRecebido(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesRecebido(11);
        recebidosDTO.setAnoRecebido(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Gson gson = gsonBuilder.create();
        String requestJson = gson.toJson(recebidosDTO);

        Mockito.when(recebidosService.update(Mockito.any(UUID.class), Mockito.any(RecebidosDTO.class)))
                .thenReturn(recebidosDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/recebidos/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.usuario.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.dataRecebido", is("2015-11-04 17:09:55")))
                .andExpect(jsonPath("$.mesRecebido", is(11)))
                .andExpect(jsonPath("$.anoRecebido", is(2021)))
                .andExpect(jsonPath("$.descricao", is("Descricao")))
                .andExpect(jsonPath("$.valor", is(100.0)));
    }

    @Test
    public void testAtualizarRecebidosNaoEncontrado() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataRecebido(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesRecebido(11);
        recebidosDTO.setAnoRecebido(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);
        Gson gson = gsonBuilder.create();
        String requestJson = gson.toJson(recebidosDTO);

        Mockito.when(recebidosService.update(Mockito.any(UUID.class), Mockito.any(RecebidosDTO.class)))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.put("/api/recebidos/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAtualizarRecebidosComUsuarioNaoEncontrado() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST_DIFERENTE);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataRecebido(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesRecebido(11);
        recebidosDTO.setAnoRecebido(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);
        Gson gson = gsonBuilder.create();
        String requestJson = gson.toJson(recebidosDTO);

        Mockito.when(recebidosService.update(Mockito.any(UUID.class), Mockito.any(RecebidosDTO.class)))
                .thenThrow(DataIntegrityViolationException.class);

        mvc.perform(MockMvcRequestBuilders.put("/api/recebidos/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletarPorRecebidosComIdIgualUUIDTest() throws Exception {
        Mockito.doNothing().when(recebidosService).delete(Mockito.any(UUID.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/recebidos/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletarPorRecebidosComRecebidosNaoEncontrado() throws Exception {

        doThrow(NotFoundException.class).when(recebidosService).delete(Mockito.any(UUID.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/recebidos/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPesquisaTodosRecebidos() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);

        RecebidosDTO recebidosDTO = new RecebidosDTO();
        recebidosDTO.setId(UUID_TEST);
        recebidosDTO.setUsuario(usuarioDTO);
        recebidosDTO.setDataRecebido(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        recebidosDTO.setMesRecebido(11);
        recebidosDTO.setAnoRecebido(2021);
        recebidosDTO.setDescricao("Descricao");
        recebidosDTO.setValor(100.0);

        Mockito.when(recebidosService.findAll())
                .thenReturn(Arrays.asList(recebidosDTO));

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

}
