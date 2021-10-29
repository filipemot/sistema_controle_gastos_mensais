package br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.repository;

import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.model.TipoConta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITipoContaRepository extends CrudRepository<TipoConta, Long> {
    @Override
    List<TipoConta> findAll();
}
