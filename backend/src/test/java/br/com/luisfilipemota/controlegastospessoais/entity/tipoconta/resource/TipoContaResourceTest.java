package br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.TipoContaService;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto.TipoContaDTO;
import com.google.gson.Gson;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TipoContaResource.class)
public class TipoContaResourceTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    TipoContaService tipoContaService;

    private final UUID UUID_TEST  = UUID.randomUUID();

    @Test
    public void testPesquisaPorTipoContaComIdIgualUUIDTest() throws Exception {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(UUID_TEST);

        Mockito.when(tipoContaService.findById(UUID_TEST))
                .thenReturn(tipoContaDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/tipoconta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao", is("Descricao")));
    }


    @Test
    public void testPesquisaPorTipoContaContaNaoEncontrada() throws Exception {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(UUID_TEST);

        UUID uuid = UUID.randomUUID();

        Mockito.when(tipoContaService.findById(uuid))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get("/api/tipoconta/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSalvarTipoConta() throws Exception {
        TipoContaDTO tipoConta = new TipoContaDTO();
        tipoConta.setDescricao("Descricao");
        Gson gson = new Gson();
        String requestJson = gson.toJson(tipoConta);

        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(UUID_TEST);

        Mockito.when(tipoContaService.save(Mockito.any(TipoContaDTO.class)))
                .thenReturn(tipoContaDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/tipoconta")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao", is("Descricao")));

    }

    @Test
    public void testAtualizarTipoContaContaEncontrada() throws Exception {
        TipoContaDTO tipoConta = new TipoContaDTO();
        tipoConta.setDescricao("Descricao");
        tipoConta.setId(UUID_TEST);
        Gson gson = new Gson();
        String requestJson = gson.toJson(tipoConta);

        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(UUID_TEST);

        Mockito.when(tipoContaService.update(Mockito.any(UUID.class), Mockito.any(TipoContaDTO.class)))
                .thenReturn(tipoContaDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/tipoconta/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao", is("Descricao")))
                .andExpect(jsonPath("$.id", is(UUID_TEST.toString())));
    }

    @Test
    public void testAtualizarTipoContaNaoEncontrada() throws Exception {
        TipoContaDTO tipoConta = new TipoContaDTO();
        tipoConta.setDescricao("Descricao");
        tipoConta.setId(UUID_TEST);
        Gson gson = new Gson();
        String requestJson = gson.toJson(tipoConta);

        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(UUID_TEST);

        Mockito.when(tipoContaService.update(Mockito.any(UUID.class), Mockito.any(TipoContaDTO.class)))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.put("/api/tipoconta/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testDeletarPorTipoContaComIdIgualUUIDTest() throws Exception {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(UUID_TEST);

        Mockito.doNothing().when(tipoContaService).delete(Mockito.any(UUID.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/tipoconta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletarPorTipoContaComTipoContaNaoEncontrada() throws Exception {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(UUID_TEST);

        doThrow(NotFoundException.class).when(tipoContaService).delete(Mockito.any(UUID.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/tipoconta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testPesquisaTodosTiposContas() throws Exception {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(UUID_TEST);

        Mockito.when(tipoContaService.findAll())
                .thenReturn(Arrays.asList(tipoContaDTO));

        mvc.perform(MockMvcRequestBuilders.get("/api/tipoconta")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].descricao", is("Descricao")));
    }
}
