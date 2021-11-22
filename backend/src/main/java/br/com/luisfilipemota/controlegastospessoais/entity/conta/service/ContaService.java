package br.com.luisfilipemota.controlegastospessoais.entity.conta.service;

import br.com.luisfilipemota.controlegastospessoais.entity.conta.mapper.ContaMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.model.Conta;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.repository.ContaRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaTipoContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.repository.TipoContaRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.TipoContaService;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto.TipoContaDTO;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContaService {

    private final ContaRepository contaRepository;

    private final ContaMapper contaMapper;

    private final TipoContaRepository tipoContaRepository;

    public ContaService(ContaMapper contaMapper, ContaRepository contaRepository, TipoContaRepository tipoContaRepository) {
        this.contaMapper = contaMapper;
        this.contaRepository = contaRepository;
        this.tipoContaRepository = tipoContaRepository;
    }

    public ContaDTO save(ContaDTO tipoContaDTO) {
        Conta conta = contaMapper.contaDTOToConta(tipoContaDTO);

        conta = contaRepository.save(conta);

        return contaMapper.contaToContaDto(conta);
    }

    public ContaDTO update(UUID id, ContaDTO contaDTO) throws NotFoundException {

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

    public void delete(UUID id) throws NotFoundException {
        Optional<Conta> optionalTipoConta = contaRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Conta não encontrado");
        }

        Conta tipoConta = optionalTipoConta.get();
        contaRepository.delete(tipoConta);
    }

    public ContaDTO findById(UUID id) throws NotFoundException {
        Optional<Conta> optionalTipoConta = contaRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Conta não encontrado");
        }

        Conta tipoConta = optionalTipoConta.get();

        return contaMapper.contaToContaDto(tipoConta);
    }

    public ContaTipoContaDTO findAllByTipoContaIdAndMesContaAndAnoConta(UUID id, int mes, int ano) {
        List<Conta> listContaPorTipoConta = contaRepository.findAllByTipoContaIdAndMesContaAndAnoConta(id, mes, ano);
        ContaTipoContaDTO contaSomatorioDTO = new ContaTipoContaDTO();

        getContasPorTipoConta(id, listContaPorTipoConta, contaSomatorioDTO);

        return contaSomatorioDTO;
    }

    public ContaTipoContaDTO findAllByTipoConta(UUID id) {
        List<Conta> listContaPorTipoConta = contaRepository.findAllByTipoContaId(id);
        ContaTipoContaDTO contaSomatorioDTO = new ContaTipoContaDTO();

        getContasPorTipoConta(id, listContaPorTipoConta, contaSomatorioDTO);


        return contaSomatorioDTO;
    }

    private void getContasPorTipoConta(UUID id, List<Conta> listContaPorTipoConta, ContaTipoContaDTO contaSomatorioDTO) {
        Optional<TipoConta> tipoConta = tipoContaRepository.findById(id);

        if(tipoConta.isPresent()) {
            List<ContaDTO> listTipoContaDto = new ArrayList<>();

            Double somatorio = 0.0;

            for (Conta conta : listContaPorTipoConta) {
                somatorio += conta.getValor();
                listTipoContaDto.add(contaMapper.contaToContaDto(conta));
            }
            contaSomatorioDTO.setContas(listTipoContaDto);
            contaSomatorioDTO.setSomatorio(somatorio);
            contaSomatorioDTO.setTipoContaId(tipoConta.get().getId());
            contaSomatorioDTO.setNomeTipoConta(tipoConta.get().getDescricao());
        }
    }
}
