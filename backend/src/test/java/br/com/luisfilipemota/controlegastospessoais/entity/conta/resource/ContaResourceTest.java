package br.com.luisfilipemota.controlegastospessoais.entity.conta.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.ContaService;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaSomatorioDTO;
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
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContaResource.class)
public class ContaResourceTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    ContaService contaService;

    private final UUID UUID_TEST  = UUID.randomUUID();

    @Test
    public void testPesquisaPorContaComIdIgualUUIDTest() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(UUID_TEST);
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

        Mockito.when(contaService.findById(UUID_TEST))
                .thenReturn(contaDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/conta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.usuario.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.tipoConta.id", is(UUID_TEST.toString())))
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

        UUID uuid = UUID.randomUUID();
        Mockito.when(contaService.findById(uuid))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get("/api/conta/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSalvarConta() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(UUID_TEST);
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

        Mockito.when(contaService.save(Mockito.any(ContaDTO.class)))
                .thenReturn(contaDTO);

        Gson gson = gsonBuilder.create();
        String requestJson = gson.toJson(contaDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/conta")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.usuario.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.tipoConta.id", is(UUID_TEST.toString())))
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
    public void testAtualizarContaComContaEncontrada() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(UUID_TEST);
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
        Gson gson = gsonBuilder.create();
        String requestJson = gson.toJson(contaDTO);

        Mockito.when(contaService.update(Mockito.any(UUID.class), Mockito.any(ContaDTO.class)))
                .thenReturn(contaDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/conta/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.usuario.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.tipoConta.id", is(UUID_TEST.toString())))
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
    public void testAtualizarContaNaoEncontrada() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(UUID_TEST);
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
        Gson gson = gsonBuilder.create();
        String requestJson = gson.toJson(contaDTO);

        Mockito.when(contaService.update(Mockito.any(UUID.class), Mockito.any(ContaDTO.class)))
                .thenThrow(NotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.put("/api/conta/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletarPorContaComIdIgualUUIDTest() throws Exception {
        Mockito.doNothing().when(contaService).delete(Mockito.any(UUID.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/conta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletarPorContaComContaNaoEncontrada() throws Exception {

        doThrow(NotFoundException.class).when(contaService).delete(Mockito.any(UUID.class));

        mvc.perform(MockMvcRequestBuilders.delete("/api/conta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPesquisaTodosContas() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(UUID_TEST);
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

        Mockito.when(contaService.findAll())
                .thenReturn(Arrays.asList(contaDTO));

        mvc.perform(MockMvcRequestBuilders.get("/api/conta")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].usuario.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$[0].tipoConta.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$[0].dataConta", is("2015-11-04 17:09:55")))
                .andExpect(jsonPath("$[0].mesConta", is(11)))
                .andExpect(jsonPath("$[0].anoConta", is(2021)))
                .andExpect(jsonPath("$[0].descricao", is("Descricao")))
                .andExpect(jsonPath("$[0].valor", is(100.0)))
                .andExpect(jsonPath("$[0].numeroParcela", is(1)))
                .andExpect(jsonPath("$[0].totalParcelas", is(1)))
                .andExpect(jsonPath("$[0].recorrente", is(false)));
    }

    @Test
    public void testPesquisaTodosContasPorTipoConta() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(UUID_TEST);
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

        ContaSomatorioDTO contaSomatorioDTO = new ContaSomatorioDTO();
        contaSomatorioDTO.setContas(Arrays.asList(contaDTO));
        contaSomatorioDTO.setSomatorio(contaDTO.getValor());

        Mockito.when(contaService.findAllByTipoConta(UUID_TEST))
                .thenReturn(contaSomatorioDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/conta/tipoconta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.contas", hasSize(1)))
                .andExpect(jsonPath("$.somatorio", is(contaDTO.getValor())))
                .andExpect(jsonPath("$.contas[0].usuario.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.contas[0].tipoConta.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.contas[0].dataConta", is("2015-11-04 17:09:55")))
                .andExpect(jsonPath("$.contas[0].mesConta", is(11)))
                .andExpect(jsonPath("$.contas[0].anoConta", is(2021)))
                .andExpect(jsonPath("$.contas[0].descricao", is("Descricao")))
                .andExpect(jsonPath("$.contas[0].valor", is(100.0)))
                .andExpect(jsonPath("$.contas[0].numeroParcela", is(1)))
                .andExpect(jsonPath("$.contas[0].totalParcelas", is(1)))
                .andExpect(jsonPath("$.contas[0].recorrente", is(false)));
    }

}
