package br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.controller;

import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.TipoContaService;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.dto.TipoContaDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipoconta")
public class TipoContaController {

    private TipoContaService tipoContaService;
    public TipoContaController(TipoContaService tipoContaService) {

        this.tipoContaService = tipoContaService;
    }

    @GetMapping
    public List<TipoContaDTO> list() {
        return this.tipoContaService.findAll();
    }

    @PostMapping
    public TipoContaDTO save(@RequestBody TipoContaDTO tipoConta) {
        return this.tipoContaService.save(tipoConta);
    }

    @PutMapping("/{id}")
    public TipoContaDTO update(@PathVariable Long id, @RequestBody TipoContaDTO tipoConta) {
        return this.tipoContaService.update(id,tipoConta);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.tipoContaService.delete(id);
    }
}
