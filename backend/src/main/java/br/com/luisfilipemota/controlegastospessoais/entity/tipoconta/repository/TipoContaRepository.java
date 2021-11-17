package br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.repository;

import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.model.TipoConta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TipoContaRepository extends CrudRepository<TipoConta, UUID> {
    @Override
    List<TipoConta> findAll();
}
