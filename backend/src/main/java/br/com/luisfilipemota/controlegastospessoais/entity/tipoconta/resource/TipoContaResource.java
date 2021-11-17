package br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.TipoContaService;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto.TipoContaDTO;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tipoconta")
public class TipoContaResource {

    private TipoContaService tipoContaService;

    @GetMapping
    public List<TipoContaDTO> list() {
        return this.tipoContaService.findAll();
    }

    public TipoContaResource(TipoContaService tipoContaService){
        this.tipoContaService = tipoContaService;
    }

    @GetMapping("/{id}")
    public TipoContaDTO findById(@PathVariable UUID id) {
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
    public TipoContaDTO update(@PathVariable UUID id, @RequestBody TipoContaDTO tipoConta) {
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
    public void delete(@PathVariable UUID id) {
        try {
            this.tipoContaService.delete(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }
    }
}
