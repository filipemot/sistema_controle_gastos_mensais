package br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.controller;

import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.controller.TipoContaController;
import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.service.TipoContaService;
import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.service.dto.TipoContaDTO;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TipoContaController.class)
public class TipoContaControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    TipoContaService tipoContaService;

    @Test
    public void testPesquisaPorTipoContaComIdIgual1() throws Exception {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        Mockito.when(tipoContaService.findById(1L))
                .thenReturn(tipoContaDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/tipoconta/1")
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
        tipoContaDTO.setId(1L);

        Mockito.when(tipoContaService.findById(2L))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get("/api/tipoconta/2")
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
        tipoContaDTO.setId(1L);

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
        tipoConta.setId(1L);
        Gson gson = new Gson();
        String requestJson = gson.toJson(tipoConta);

        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        Mockito.when(tipoContaService.update(Mockito.any(Long.class), Mockito.any(TipoContaDTO.class)))
                .thenReturn(tipoContaDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/tipoconta/" + 1L)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao", is("Descricao")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testAtualizarTipoContaNãoEncontrada() throws Exception {
        TipoContaDTO tipoConta = new TipoContaDTO();
        tipoConta.setDescricao("Descricao");
        tipoConta.setId(1L);
        Gson gson = new Gson();
        String requestJson = gson.toJson(tipoConta);

        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        Mockito.when(tipoContaService.update(Mockito.any(Long.class), Mockito.any(TipoContaDTO.class)))
                        .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.put("/api/tipoconta/" + 1L)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testDeletarPorTipoContaComIdIgual1() throws Exception {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        Mockito.doNothing().when(tipoContaService).delete(Mockito.any(Long.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/tipoconta/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletarPorTipoContaComTipoContaNaoEncontrada() throws Exception {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        doThrow(NotFoundException.class).when(tipoContaService).delete(Mockito.any(Long.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/tipoconta/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testPesquisaTodosTiposContas() throws Exception {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

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
