package br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.controller;

import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.TipoContaService;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.dto.TipoContaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tipoconta")
public class TipoContaController {

    private TipoContaService tipoContaService;

    @GetMapping
    public List<TipoContaDTO> list() {
        return this.tipoContaService.findAll();
    }

    public TipoContaController(TipoContaService tipoContaService){
        this.tipoContaService = tipoContaService;
    }

    @GetMapping("/{id}")
    public TipoContaDTO findById(@PathVariable Long id) {
        TipoContaDTO tipoContaDTO;
        try {
            tipoContaDTO = this.tipoContaService.findById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }

        return tipoContaDTO;
    }

    @PostMapping
    public ResponseEntity<TipoContaDTO> save(@RequestBody TipoContaDTO tipoConta) {

        TipoContaDTO tipoContaDTO = this.tipoContaService.save(tipoConta);

        return new ResponseEntity<>(tipoContaDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public TipoContaDTO update(@PathVariable Long id, @RequestBody TipoContaDTO tipoConta) {
        TipoContaDTO tipoContaDTO;
        try {
            tipoContaDTO = this.tipoContaService.update(id, tipoConta);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
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
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }
    }
}
