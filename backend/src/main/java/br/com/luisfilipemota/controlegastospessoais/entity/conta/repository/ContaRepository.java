package br.com.luisfilipemota.controlegastospessoais.entity.conta.repository;

import br.com.luisfilipemota.controlegastospessoais.entity.conta.model.Conta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContaRepository extends CrudRepository<Conta, Long> {
    @Override
    List<Conta> findAll();
}
