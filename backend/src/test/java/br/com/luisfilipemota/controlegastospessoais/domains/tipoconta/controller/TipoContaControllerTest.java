package br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.controller;

import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.TipoContaService;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.dto.TipoContaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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
    public void testSalvarTipoConta() throws Exception {
        TipoContaDTO tipoContaDTOSemId = new TipoContaDTO();
        tipoContaDTOSemId.setDescricao("Descricao");

        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setDescricao("Descricao");
        tipoContaDTO.setId(1L);

        Mockito.when(tipoContaService.save(tipoContaDTOSemId))
                .thenReturn(tipoContaDTO);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(tipoContaDTOSemId);


        mvc.perform(MockMvcRequestBuilders.post("/api/tipoconta")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
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
                .andExpect(status().isNoContent());
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
