package br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.controller;

import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.TipoContaService;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.dto.TipoContaDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tipoconta")
public class TipoContaController {

    @Autowired
    private TipoContaService tipoContaService;

    @GetMapping
    public List<TipoContaDTO> list() {
        return this.tipoContaService.findAll();
    }

    @GetMapping("/{id}")
    public TipoContaDTO findById(@PathVariable Long id) {
        TipoContaDTO tipoContaDTO;
        try {
            tipoContaDTO = this.tipoContaService.findById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getMessage()
            );
        }

        return tipoContaDTO;
    }

    @PostMapping
    public TipoContaDTO save(@RequestBody TipoContaDTO tipoConta) {
        return this.tipoContaService.save(tipoConta);
    }

    @PutMapping("/{id}")
    public TipoContaDTO update(@PathVariable Long id, @RequestBody TipoContaDTO tipoConta) {
        TipoContaDTO tipoContaDTO;
        try {
            tipoContaDTO = this.tipoContaService.update(id, tipoConta);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getMessage()
            );
        }
        return tipoContaDTO;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            this.tipoContaService.delete(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getMessage()
            );
        }
    }
}
