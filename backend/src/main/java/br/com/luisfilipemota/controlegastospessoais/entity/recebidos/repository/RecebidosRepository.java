package br.com.luisfilipemota.controlegastospessoais.entity.recebidos.repository;

import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.model.Recebidos;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RecebidosRepository extends CrudRepository<Recebidos, UUID> {
    @Override
    List<Recebidos> findAll();
}
