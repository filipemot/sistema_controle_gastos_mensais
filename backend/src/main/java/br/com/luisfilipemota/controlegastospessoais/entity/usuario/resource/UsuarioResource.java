package br.com.luisfilipemota.controlegastospessoais.entity.usuario.resource;

import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.UsuarioService;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioResource {

    @Autowired
    private final UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioDTO> list() {
        return this.usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public UsuarioDTO findById(@PathVariable UUID id) {
        UsuarioDTO usuarioDTO;
        try {
            usuarioDTO = this.usuarioService.findById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }

        return usuarioDTO;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> save(@RequestBody @Valid UsuarioDTO usuarioDTO) {

        UsuarioDTO tipoContaDTO = this.usuarioService.save(usuarioDTO);

        return new ResponseEntity<>(tipoContaDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public UsuarioDTO update(@PathVariable UUID id, @RequestBody @Valid UsuarioDTO usuarioDTO) {
        UsuarioDTO tipoContaDTO;
        try {
            tipoContaDTO = this.usuarioService.update(id, usuarioDTO);
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
            this.usuarioService.delete(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }
    }
}
