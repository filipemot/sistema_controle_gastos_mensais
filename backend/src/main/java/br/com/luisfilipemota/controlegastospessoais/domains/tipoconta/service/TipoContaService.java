package br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service;

import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.mapper.ITipoContaMapper;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.repository.ITipoContaRepository;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.dto.TipoContaDTO;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoContaService {

    private ITipoContaRepository tipoContaRepository;

    private ITipoContaMapper tipoContaMapper;

    public TipoContaService(ITipoContaMapper tipoContaMapper, ITipoContaRepository tipoContaRepository) {
        this.tipoContaMapper = tipoContaMapper;
        this.tipoContaRepository = tipoContaRepository;
    }

    public TipoContaDTO save(TipoContaDTO tipoContaDTO) {
        TipoConta tipoConta = tipoContaMapper.tipoContaDTOToTipoContaDto(tipoContaDTO);

        tipoConta = tipoContaRepository.save(tipoConta);

        return tipoContaMapper.tipoContaToTipoContaDto(tipoConta);
    }

    public TipoContaDTO update(Long id, TipoContaDTO tipoContaDTO) throws NotFoundException {

        Optional<TipoConta> optionalTipoConta = tipoContaRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Tipo de Conta não encontrado");
        }

        TipoConta tipoContaBd = optionalTipoConta.get();
        tipoContaBd.setDescricao(tipoContaDTO.getDescricao());
        TipoConta tipoContaUpdated = tipoContaRepository.save(tipoContaBd);
        tipoContaDTO = tipoContaMapper.tipoContaToTipoContaDto(tipoContaUpdated);

        return tipoContaDTO;
    }

    public List<TipoContaDTO> findAll() {
        List<TipoConta> listTipoConta = tipoContaRepository.findAll();
        List<TipoContaDTO> listTipoContaDto = new ArrayList<>();

        for (TipoConta tipoConta : listTipoConta) {
            listTipoContaDto.add(tipoContaMapper.tipoContaToTipoContaDto(tipoConta));
        }

        return listTipoContaDto;
    }

    public void delete(Long id) throws NotFoundException {
        Optional<TipoConta> optionalTipoConta = tipoContaRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Tipo de Conta não encontrado");
        }

        TipoConta tipoConta = optionalTipoConta.get();
        tipoContaRepository.delete(tipoConta);
    }

    public TipoContaDTO findById(Long id) throws NotFoundException {
        Optional<TipoConta> optionalTipoConta = tipoContaRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Tipo de Conta não encontrado");
        }

        TipoConta tipoConta = optionalTipoConta.get();

        return tipoContaMapper.tipoContaToTipoContaDto(tipoConta);
    }
}
