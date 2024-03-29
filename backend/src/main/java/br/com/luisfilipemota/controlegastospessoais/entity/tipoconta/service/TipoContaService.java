package br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service;

import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.mapper.TipoContaMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.repository.TipoContaRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto.TipoContaDTO;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TipoContaService {

    private final TipoContaRepository tipoContaRepository;

    private final TipoContaMapper tipoContaMapper;

    public TipoContaService(TipoContaMapper tipoContaMapper, TipoContaRepository tipoContaRepository) {
        this.tipoContaMapper = tipoContaMapper;
        this.tipoContaRepository = tipoContaRepository;
    }

    public TipoContaDTO save(TipoContaDTO tipoContaDTO) {
        TipoConta tipoConta = tipoContaMapper.tipoContaDTOToTipoConta(tipoContaDTO);

        tipoConta = tipoContaRepository.save(tipoConta);

        return tipoContaMapper.tipoContaToTipoContaDto(tipoConta);
    }

    public TipoContaDTO update(UUID id, TipoContaDTO tipoContaDTO) throws NotFoundException {

        TipoConta tipoContaBd = getTipoConta(id);
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

    public void delete(UUID id) throws NotFoundException {
        TipoConta tipoConta = getTipoConta(id);
        tipoContaRepository.delete(tipoConta);
    }

    public TipoContaDTO findById(UUID id) throws NotFoundException {
        TipoConta tipoConta = getTipoConta(id);

        return tipoContaMapper.tipoContaToTipoContaDto(tipoConta);
    }


    private TipoConta getTipoConta(UUID id) throws NotFoundException {
        Optional<TipoConta> optionalTipoConta = tipoContaRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Tipo de Conta não encontrado");
        }

        return optionalTipoConta.get();
    }

}
