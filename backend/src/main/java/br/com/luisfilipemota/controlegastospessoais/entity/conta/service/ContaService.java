package br.com.luisfilipemota.controlegastospessoais.entity.conta.service;

import br.com.luisfilipemota.controlegastospessoais.entity.conta.mapper.ContaMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.model.Conta;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.repository.ContaRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.ContaTipoContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto.TodasContaTipoContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.repository.TipoContaRepository;
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

        Conta contaBd = contaMapper.contaDTOToConta(contaDTO);

        contaBd.setId(getConta(id).getId());

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

        Conta tipoConta = getConta(id);
        contaRepository.delete(tipoConta);
    }

    public ContaDTO findById(UUID id) throws NotFoundException {
        Conta tipoConta = getConta(id);

        return contaMapper.contaToContaDto(tipoConta);
    }

    public ContaTipoContaDTO findAllByTipoContaIdAndMesContaAndAnoConta(UUID id, int mes, int ano) {
        List<Conta> listContaPorTipoConta = contaRepository.findAllByTipoContaIdAndMesContaAndAnoContaOrderByDataConta(id, mes, ano);
        ContaTipoContaDTO contaSomatorioDTO = new ContaTipoContaDTO();

        getContasPorTipoConta(id, listContaPorTipoConta, contaSomatorioDTO);

        return contaSomatorioDTO;
    }

    public ContaTipoContaDTO findAllByTipoConta(UUID id) {
        List<Conta> listContaPorTipoConta = contaRepository.findAllByTipoContaIdOrderByDataConta(id);
        ContaTipoContaDTO contaSomatorioDTO = new ContaTipoContaDTO();

        getContasPorTipoConta(id, listContaPorTipoConta, contaSomatorioDTO);

        return contaSomatorioDTO;
    }

    public TodasContaTipoContaDTO listarTodasContas(int mes, int ano){

        List<TipoConta> listaTipoConta = tipoContaRepository.findAll();
        List<ContaTipoContaDTO> listaContaTipoContaDTO = new ArrayList<>();

        TodasContaTipoContaDTO todasContaTipoContaDTO = getTodasContaTipoContaDTO(mes, ano, listaTipoConta, listaContaTipoContaDTO);

        return todasContaTipoContaDTO;
    }

    private TodasContaTipoContaDTO getTodasContaTipoContaDTO(int mes, int ano, List<TipoConta> listaTipoConta, List<ContaTipoContaDTO> listaContaTipoContaDTO) {
        Double somatorio = 0.0;

        for(TipoConta tipoConta : listaTipoConta){
            ContaTipoContaDTO contaTipoContaDTO = findAllByTipoContaIdAndMesContaAndAnoConta(tipoConta.getId(), mes, ano);
            listaContaTipoContaDTO.add(contaTipoContaDTO);
            somatorio += contaTipoContaDTO.getSomatorio();
        }

        TodasContaTipoContaDTO todasContaTipoContaDTO = new TodasContaTipoContaDTO();
        todasContaTipoContaDTO.setContas(listaContaTipoContaDTO);
        todasContaTipoContaDTO.setSomatorio(somatorio);
        return todasContaTipoContaDTO;
    }

    private Conta getConta(UUID id) throws NotFoundException {
        Optional<Conta> optionalTipoConta = contaRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Conta não encontrado");
        }
        return optionalTipoConta.get();
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
