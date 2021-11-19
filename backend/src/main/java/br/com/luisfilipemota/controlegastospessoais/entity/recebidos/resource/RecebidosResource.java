package br.com.luisfilipemota.controlegastospessoais.entity.recebidos.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service.RecebidosService;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service.dto.RecebidosDTO;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recebidos")
public class RecebidosResource {

    private RecebidosService recebidosService;

    @GetMapping
    public List<RecebidosDTO> list() {
        return this.recebidosService.findAll();
    }

    public RecebidosResource(RecebidosService contaService){
        this.recebidosService = contaService;
    }

    @GetMapping("/{id}")
    public RecebidosDTO findById(@PathVariable UUID id) {
        RecebidosDTO recebidosDTO;
        try {
            recebidosDTO = this.recebidosService.findById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }

        return recebidosDTO;
    }

    @PostMapping
    public ResponseEntity<RecebidosDTO> save(@RequestBody RecebidosDTO tipoConta) {

        RecebidosDTO recebidosDTO = this.recebidosService.save(tipoConta);

        return new ResponseEntity<>(recebidosDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public RecebidosDTO update(@PathVariable UUID id, @RequestBody RecebidosDTO tipoConta) {
        RecebidosDTO recebidosDTO;
        try {
            recebidosDTO = this.recebidosService.update(id, tipoConta);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }
        return recebidosDTO;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        try {
            this.recebidosService.delete(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }
    }
}
