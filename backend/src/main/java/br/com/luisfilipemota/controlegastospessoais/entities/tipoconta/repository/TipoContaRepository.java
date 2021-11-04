package br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.repository;

import br.com.luisfilipemota.controlegastospessoais.entities.tipoconta.model.TipoConta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TipoContaRepository extends CrudRepository<TipoConta, Long> {
    @Override
    List<TipoConta> findAll();
}
