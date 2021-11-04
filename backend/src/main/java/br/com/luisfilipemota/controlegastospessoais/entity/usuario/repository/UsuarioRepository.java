package br.com.luisfilipemota.controlegastospessoais.entity.usuario.repository;

import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    @Override
    List<Usuario> findAll();
}
