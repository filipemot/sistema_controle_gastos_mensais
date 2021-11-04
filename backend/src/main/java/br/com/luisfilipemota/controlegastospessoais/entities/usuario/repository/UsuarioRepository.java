package br.com.luisfilipemota.controlegastospessoais.entities.usuario.repository;

import br.com.luisfilipemota.controlegastospessoais.entities.usuario.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    @Override
    List<Usuario> findAll();
}
