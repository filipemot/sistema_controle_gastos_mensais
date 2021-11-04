package br.com.luisfilipemota.controlegastospessoais.entity.conta.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.ContaService;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto.TipoContaDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContaResource.class)
public class ContaResourceTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    ContaService tipoContaService;

    @Test
    public void testPesquisaPorContaComIdIgual1() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);
        contaDTO.setUsuario(usuarioDTO);
        contaDTO.setTipoConta(tipoContaDTO);
        contaDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        contaDTO.setMesConta(11);
        contaDTO.setAnoConta(2021);
        contaDTO.setDescricao("Descricao");
        contaDTO.setValor(100.0);
        contaDTO.setNumeroParcela(1);
        contaDTO.setTotalParcelas(1);
        contaDTO.setRecorrente(false);

        Mockito.when(tipoContaService.findById(1L))
                .thenReturn(contaDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/conta/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.usuario.id", is(1)))
                .andExpect(jsonPath("$.tipoConta.id", is(1)))
                .andExpect(jsonPath("$.dataConta", is("2015-11-04 17:09:55")))
                .andExpect(jsonPath("$.mesConta", is(11)))
                .andExpect(jsonPath("$.anoConta", is(2021)))
                .andExpect(jsonPath("$.descricao", is("Descricao")))
                .andExpect(jsonPath("$.valor", is(100.0)))
                .andExpect(jsonPath("$.numeroParcela", is(1)))
                .andExpect(jsonPath("$.totalParcelas", is(1)))
                .andExpect(jsonPath("$.recorrente", is(false)));
    }

    @Test
    public void testPesquisaPorContaContaNaoEncontrada() throws Exception {
        Mockito.when(tipoContaService.findById(2L))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get("/api/conta/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSalvarConta() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(1L);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);
        contaDTO.setUsuario(usuarioDTO);
        contaDTO.setTipoConta(tipoContaDTO);
        contaDTO.setDataConta(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
        contaDTO.setMesConta(11);
        contaDTO.setAnoConta(2021);
        contaDTO.setDescricao("Descricao");
        contaDTO.setValor(100.0);
        contaDTO.setNumeroParcela(1);
        contaDTO.setTotalParcelas(1);
        contaDTO.setRecorrente(false);

        Mockito.when(tipoContaService.save(Mockito.any(ContaDTO.class)))
                .thenReturn(contaDTO);

        Gson gson = gsonBuilder.create();
        String requestJson = gson.toJson(contaDTO);

        gson.fromJson(requestJson, ContaDTO.class);

        mvc.perform(MockMvcRequestBuilders.post("/api/conta")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.usuario.id", is(1)))
                .andExpect(jsonPath("$.tipoConta.id", is(1)))
                .andExpect(jsonPath("$.dataConta", is("2015-11-04 17:09:55")))
                .andExpect(jsonPath("$.mesConta", is(11)))
                .andExpect(jsonPath("$.anoConta", is(2021)))
                .andExpect(jsonPath("$.descricao", is("Descricao")))
                .andExpect(jsonPath("$.valor", is(100.0)))
                .andExpect(jsonPath("$.numeroParcela", is(1)))
                .andExpect(jsonPath("$.totalParcelas", is(1)))
                .andExpect(jsonPath("$.recorrente", is(false)));

    }
}
