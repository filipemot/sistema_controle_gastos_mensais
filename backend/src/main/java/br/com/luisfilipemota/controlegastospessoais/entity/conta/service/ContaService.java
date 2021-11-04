package br.com.luisfilipemota.controlegastospessoais.entity.conta.service;

import br.com.luisfilipemota.controlegastospessoais.entity.conta.mapper.ContaMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.model.Conta;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.repository.ContaRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaDTO;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    private ContaRepository contaRepository;

    private ContaMapper contaMapper;

    public ContaService(ContaMapper contaMapper, ContaRepository contaRepository) {
        this.contaMapper = contaMapper;
        this.contaRepository = contaRepository;
    }

    public ContaDTO save(ContaDTO tipoContaDTO) {
        Conta conta = contaMapper.contaDTOToConta(tipoContaDTO);

        conta = contaRepository.save(conta);

        return contaMapper.contaToContaDto(conta);
    }

    public ContaDTO update(Long id, ContaDTO contaDTO) throws NotFoundException {

        Optional<Conta> optionalConta = contaRepository.findById(id);

        if (!optionalConta.isPresent()) {
            throw new NotFoundException("Conta não encontrado");
        }

        Conta contaBd = contaMapper.contaDTOToConta(contaDTO);
        contaBd.setId(optionalConta.get().getId());


        Conta contaUpdated = contaRepository.save(contaBd);
        contaDTO = contaMapper.contaToContaDto(contaUpdated);

        return contaDTO;
    }

    public List<ContaDTO> findAll() {
        List<Conta> listTipoConta = contaRepository.findAll();
        List<ContaDTO> listTipoContaDto = new ArrayList<>();

        for (Conta tipoConta : listTipoConta) {
            listTipoContaDto.add(contaMapper.contaToContaDto(tipoConta));
        }

        return listTipoContaDto;
    }

    public void delete(Long id) throws NotFoundException {
        Optional<Conta> optionalTipoConta = contaRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Conta não encontrado");
        }

        Conta tipoConta = optionalTipoConta.get();
        contaRepository.delete(tipoConta);
    }

    public ContaDTO findById(Long id) throws NotFoundException {
        Optional<Conta> optionalTipoConta = contaRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Conta não encontrado");
        }

        Conta tipoConta = optionalTipoConta.get();

        return contaMapper.contaToContaDto(tipoConta);
    }
}
