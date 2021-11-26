package br.com.luisfilipemota.controlegastospessoais.entity.conta.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.ContaService;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaTipoContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.TodasContaTipoContaDTO;
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

@WebMvcTest(ContaResource.class)
public class ContaResourceTest {
    @Autowired
    public MockMvc mvc;

    @MockBean
    ContaService contaService;

    private final UUID UUID_TEST = UUID.randomUUID();
    private final UUID UUID_TEST_DIFERENTE = UUID.randomUUID();


    @Test
    public void testPesquisaPorContaComIdIgualUUIDTest() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Mockito.when(contaService.findById(UUID_TEST))
                .thenReturn(contaDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/conta/" + UUID_TEST)
                .contentType(MediaType.APPLICATION_JSON));

        assertsRequest(result, status().isOk(), true);
    }

    @Test
    public void testPesquisaPorContaContaNaoEncontrada() throws Exception {

        UUID uuid = UUID.randomUUID();
        Mockito.when(contaService.findById(uuid))
                .thenThrow(NotFoundException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/conta/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isNotFound(), false);
    }

    @Test
    public void testSalvarConta() throws Exception {
        GsonBuilder gsonBuilder = getGsonBuilder();

        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Mockito.when(contaService.save(Mockito.any(ContaDTO.class)))
                .thenReturn(contaDTO);

        String requestJson = getJson(gsonBuilder, contaDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/api/conta")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));

        assertsRequest(result, status().isCreated(), true);
    }

    @Test
    public void testSalvarContaComUsuarioNaoEncontradoOuTipoContaNaoEncontrado() throws Exception {
        GsonBuilder gsonBuilder = getGsonBuilder();

        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Mockito.when(contaService.save(Mockito.any(ContaDTO.class)))
                .thenThrow(DataIntegrityViolationException.class);

        String requestJson = getJson(gsonBuilder, contaDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/api/conta")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isBadRequest(), false);
    }

    @Test
    public void testAtualizarContaComContaEncontrada() throws Exception {
        GsonBuilder gsonBuilder = getGsonBuilder();

        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);
        String requestJson = getJson(gsonBuilder, contaDTO);

        Mockito.when(contaService.update(Mockito.any(UUID.class), Mockito.any(ContaDTO.class)))
                .thenReturn(contaDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/conta/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isOk(), true);
    }

    @Test
    public void testAtualizarContaComUsuarioNaoEncontradoOuTipoContaNaoEncontrado() throws Exception {
        GsonBuilder gsonBuilder = getGsonBuilder();

        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST_DIFERENTE);
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST_DIFERENTE);

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);
        String requestJson = getJson(gsonBuilder, contaDTO);

        Mockito.when(contaService.update(Mockito.any(UUID.class), Mockito.any(ContaDTO.class)))
                .thenThrow(DataIntegrityViolationException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/conta/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isBadRequest(), false);
    }

    @Test
    public void testAtualizarContaNaoEncontrada() throws Exception {
        GsonBuilder gsonBuilder = getGsonBuilder();

        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);
        String requestJson = getJson(gsonBuilder, contaDTO);

        Mockito.when(contaService.update(Mockito.any(UUID.class), Mockito.any(ContaDTO.class)))
                .thenThrow(NotFoundException.class);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.put("/api/conta/" + UUID_TEST)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result,status().isNotFound(), false);
    }

    @Test
    public void testDeletarPorContaComIdIgualUUIDTest() throws Exception {
        Mockito.doNothing().when(contaService).delete(Mockito.any(UUID.class));

        ResultActions result = mvc.perform(MockMvcRequestBuilders.delete("/api/conta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isOk(), false);
    }

    @Test
    public void testDeletarPorContaComContaNaoEncontrada() throws Exception {

        doThrow(NotFoundException.class).when(contaService).delete(Mockito.any(UUID.class));

        ResultActions result = mvc.perform(MockMvcRequestBuilders.delete("/api/conta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isNotFound(), false);
    }

    @Test
    public void testPesquisaTodosContas() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        Mockito.when(contaService.findAll())
                .thenReturn(Collections.singletonList(contaDTO));

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
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        ContaTipoContaDTO contaSomatorioDTO = getContaTipoContaDTO(tipoContaDTO, contaDTO);

        Mockito.when(contaService.findAllByTipoConta(UUID_TEST))
                .thenReturn(contaSomatorioDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/conta/tipoconta/" + UUID_TEST)
                        .contentType(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isOk(), contaDTO, contaSomatorioDTO);
    }

    @Test
    public void testPesquisaTodosContasPorTipoContaMesAno() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        ContaTipoContaDTO contaSomatorioDTO = getContaTipoContaDTO(tipoContaDTO, contaDTO);

        Mockito.when(contaService.findAllByTipoContaIdAndMesContaAndAnoConta(UUID_TEST, 11, 2021))
                .thenReturn(contaSomatorioDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/conta/tipoconta/" + UUID_TEST + "/11/2021")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        assertsRequest(result, status().isOk(), contaDTO, contaSomatorioDTO);
    }

    @Test
    public void testPesquisaTodosContasMesPorMesAno() throws Exception {
        UsuarioDTO usuarioDTO = getUsuarioDTO(UUID_TEST);
        TipoContaDTO tipoContaDTO = getTipoContaDTO();

        ContaDTO contaDTO = getContaDTO(usuarioDTO, tipoContaDTO);

        ContaTipoContaDTO contaSomatorioDTO = getContaTipoContaDTO(tipoContaDTO, contaDTO);

        TodasContaTipoContaDTO todasContaTipoContaDTO = new TodasContaTipoContaDTO();
        todasContaTipoContaDTO.setContas(Collections.singletonList(contaSomatorioDTO));
        todasContaTipoContaDTO.setSomatorio(contaSomatorioDTO.getSomatorio());

        Mockito.when(contaService.listarTodasContas(11, 2021))
                .thenReturn(todasContaTipoContaDTO);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/conta/todascontasmes/11/2021")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.contas", hasSize(1)))
                .andExpect(jsonPath("$.somatorio", is(contaDTO.getValor())))
                .andExpect(jsonPath("$.contas[0].somatorio", is(contaDTO.getValor())))
                .andExpect(jsonPath("$.contas[0].nomeTipoConta", is(contaSomatorioDTO.getNomeTipoConta())))
                .andExpect(jsonPath("$.contas[0].tipoContaId", is(contaSomatorioDTO.getTipoContaId().toString())))
                .andExpect(jsonPath("$.contas[0].contas[0].usuario.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.contas[0].contas[0].tipoConta.id", is(UUID_TEST.toString())))
                .andExpect(jsonPath("$.contas[0].contas[0].dataConta", is("2015-11-04 17:09:55")))
                .andExpect(jsonPath("$.contas[0].contas[0].mesConta", is(11)))
                .andExpect(jsonPath("$.contas[0].contas[0].anoConta", is(2021)))
                .andExpect(jsonPath("$.contas[0].contas[0].descricao", is("Descricao")))
                .andExpect(jsonPath("$.contas[0].contas[0].valor", is(100.0)))
                .andExpect(jsonPath("$.contas[0].contas[0].numeroParcela", is(1)))
                .andExpect(jsonPath("$.contas[0].contas[0].totalParcelas", is(1)))
                .andExpect(jsonPath("$.contas[0].contas[0].recorrente", is(false)));
   }

    private void assertsRequest(ResultActions result, ResultMatcher status, Boolean isContent) throws Exception {

        if (isContent) {
            result.andExpect(status)
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
        } else {
            result.andExpect(status);
        }
    }

    private void assertsRequest(ResultActions result, ResultMatcher status, ContaDTO contaDTO, ContaTipoContaDTO contaTipoContaDTO) throws Exception {
            result.andExpect(status)
                .andExpect(jsonPath("$.contas", hasSize(1)))
                .andExpect(jsonPath("$.somatorio", is(contaDTO.getValor())))
                .andExpect(jsonPath("$.nomeTipoConta", is(contaTipoContaDTO.getNomeTipoConta())))
                .andExpect(jsonPath("$.tipoContaId", is(contaTipoContaDTO.getTipoContaId().toString())))
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

    private TipoContaDTO getTipoContaDTO() {
        TipoContaDTO tipoContaDTO = new TipoContaDTO();
        tipoContaDTO.setId(UUID_TEST);
        tipoContaDTO.setDescricao("Descricao");
        return tipoContaDTO;
    }

    private ContaDTO getContaDTO(UsuarioDTO usuarioDTO, TipoContaDTO tipoContaDTO) {
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
        return contaDTO;
    }

    private ContaTipoContaDTO getContaTipoContaDTO(TipoContaDTO tipoContaDTO, ContaDTO contaDTO) {
        ContaTipoContaDTO contaSomatorioDTO = new ContaTipoContaDTO();
        contaSomatorioDTO.setContas(Collections.singletonList(contaDTO));
        contaSomatorioDTO.setSomatorio(contaDTO.getValor());
        contaSomatorioDTO.setTipoContaId(UUID_TEST);
        contaSomatorioDTO.setNomeTipoConta(tipoContaDTO.getDescricao());
        return contaSomatorioDTO;
    }

    private UsuarioDTO getUsuarioDTO(UUID uuid_test) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(uuid_test);
        return usuarioDTO;
    }

    private String getJson(GsonBuilder gsonBuilder, ContaDTO contaDTO) {
        Gson gson = gsonBuilder.create();
        return gson.toJson(contaDTO);
    }

    private GsonBuilder getGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());
        return gsonBuilder;
    }
}
