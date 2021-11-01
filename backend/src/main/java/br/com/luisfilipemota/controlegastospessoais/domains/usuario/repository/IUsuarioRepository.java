package br.com.luisfilipemota.controlegastospessoais.domains.usuario.repository;

import br.com.luisfilipemota.controlegastospessoais.domains.usuario.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {
    @Override
    List<Usuario> findAll();
}
