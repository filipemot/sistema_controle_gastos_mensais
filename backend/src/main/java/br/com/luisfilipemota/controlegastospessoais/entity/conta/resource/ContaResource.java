package br.com.luisfilipemota.controlegastospessoais.entity.conta.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.ContaService;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaSomatorioDTO;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/conta")
public class ContaResource {

    private ContaService contaService;

    @GetMapping
    public List<ContaDTO> list() {
        return this.contaService.findAll();
    }

    @GetMapping("/tipoconta/{id}")
    public ContaSomatorioDTO listPorTipoConta(@PathVariable UUID id) {

        return this.contaService.findAllByTipoConta(id);
    }

    public ContaResource(ContaService contaService){
        this.contaService = contaService;
    }

    @GetMapping("/{id}")
    public ContaDTO findById(@PathVariable UUID id) {
        ContaDTO contaDTO;
        try {
            contaDTO = this.contaService.findById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }

        return contaDTO;
    }

    @PostMapping
    public ResponseEntity<ContaDTO> save(@RequestBody ContaDTO tipoConta) {

        ContaDTO contaDTO = this.contaService.save(tipoConta);

        return new ResponseEntity<>(contaDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ContaDTO update(@PathVariable UUID id, @RequestBody ContaDTO tipoConta) {
        ContaDTO contaDTO;
        try {
            contaDTO = this.contaService.update(id, tipoConta);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }
        return contaDTO;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        try {
            this.contaService.delete(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }
    }
}
