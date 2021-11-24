package br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.TipoContaService;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto.TipoContaDTO;
import com.google.gson.Gson;
import javassist.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TipoContaResource.class)
public class TipoContaResourceTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TipoContaService tipoContaService;

    private final UUID UUID_TEST  = UUID.randomUUID();

    @Test
    public void testPesquisaPorTipoContaComIdIgualUUIDTest() throws Exception {
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        Mockito.when(tipoContaService.findById(UUID_TEST))
                .thenReturn(tipoContaDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/tipoconta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isOk(), true);
    }

    @Test
    public void testPesquisaPorTipoContaContaNaoEncontrada() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(tipoContaService.findById(uuid))
                .thenThrow(NotFoundException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/tipoconta/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        assertsRequest(result, status().isNotFound(), false);
    }

    @Test
    public void testSalvarTipoConta() throws Exception {
        TipoContaDTO tipoConta = getTipoContaDTO();
        String requestJson = getJson(tipoConta);

        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        Mockito.when(tipoContaService.save(Mockito.any(TipoContaDTO.class)))
                .thenReturn(tipoContaDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/api/tipoconta")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isCreated(), true);
    }

    @Test
    public void testAtualizarTipoContaContaEncontrada() throws Exception {
        TipoContaDTO tipoConta = getTipoContaDTO();
        String requestJson = getJson(tipoConta);

        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        Mockito.when(tipoContaService.update(Mockito.any(UUID.class), Mockito.any(TipoContaDTO.class)))
                .thenReturn(tipoContaDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/tipoconta/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isOk(), true);
    }

    @Test
    public void testAtualizarTipoContaNaoEncontrada() throws Exception {
        TipoContaDTO tipoConta = getTipoContaDTO();
        String requestJson = getJson(tipoConta);
        Mockito.when(tipoContaService.update(Mockito.any(UUID.class), Mockito.any(TipoContaDTO.class)))
                .thenThrow(NotFoundException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/tipoconta/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isNotFound(), false);
    }

    @Test
    public void testDeletarPorTipoContaComIdIgualUUIDTest() throws Exception {
        Mockito.doNothing().when(tipoContaService).delete(Mockito.any(UUID.class));

        ResultActions result = mvc.perform(MockMvcRequestBuilders.delete("/api/tipoconta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isOk(), false);
    }

    @Test
    public void testDeletarPorTipoContaComTipoContaNaoEncontrada() throws Exception {
        doThrow(NotFoundException.class).when(tipoContaService).delete(Mockito.any(UUID.class));

        ResultActions result = mvc.perform(MockMvcRequestBuilders.delete("/api/tipoconta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isNotFound(), false);
    }

    @Test
    public void testPesquisaTodosTiposContas() throws Exception {
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        Mockito.when(tipoContaService.findAll())
                .thenReturn(Collections.singletonList(tipoContaDTO));

        mvc.perform(MockMvcRequestBuilders.get("/api/tipoconta")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].descricao", is("Descricao")));
    }

    private void assertsRequest(ResultActions result, ResultMatcher status, Boolean isContent) throws Exception {

        if (isContent) {
            result.andExpect(status)
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.descricao", is("Descricao")))
                    .andExpect(jsonPath("$.id", is(UUID_TEST.toString())));
        } else {
            result.andExpect(status);
        }
    }

    @NotNull
    private TipoContaDTO getTipoContaDTO() {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(UUID_TEST);
        return tipoContaDTO;
    }

    private String getJson(TipoContaDTO tipoConta) {
        Gson gson = new Gson();
        return gson.toJson(tipoConta);
    }

}
