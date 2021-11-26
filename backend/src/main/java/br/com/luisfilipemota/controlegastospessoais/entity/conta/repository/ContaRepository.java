package br.com.luisfilipemota.controlegastospessoais.entity.conta.repository;

import br.com.luisfilipemota.controlegastospessoais.entity.conta.model.Conta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ContaRepository extends CrudRepository<Conta, UUID> {
    @Override
    List<Conta> findAll();

    List<Conta> findAllByTipoContaIdOrderByDataConta(UUID idTipoConta);

    List<Conta> findAllByTipoContaIdAndMesContaAndAnoContaOrderByDataConta(UUID idTipoConta, int mesConta, int anoConta );
}
