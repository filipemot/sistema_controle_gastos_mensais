package br.com.luisfilipemota.controlegastospessoais.entity.usuario.repository;

import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface UsuarioRepository extends CrudRepository<Usuario, UUID> {
    @Override
    @NotNull
    List<Usuario> findAll();
}
