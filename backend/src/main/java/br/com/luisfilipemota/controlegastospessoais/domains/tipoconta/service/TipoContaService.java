package br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service;

import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.mapper.ITipoContaMapper;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.repository.ITipoContaRepository;
import br.com.luisfilipemota.controlegastospessoais.domains.tipoconta.service.dto.TipoContaDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TipoContaService {

    private ITipoContaRepository tipoContaRepository;
    private ITipoContaMapper tipoContaMapper;

    public TipoContaService(ITipoContaRepository tipoContaRepository, ITipoContaMapper tipoContaMapper) {
        this.tipoContaRepository = tipoContaRepository;
        this.tipoContaMapper = tipoContaMapper;
    }

    public TipoContaDTO save(TipoContaDTO tipoContaDTO) {
        TipoConta tipoConta = tipoContaMapper.tipoContaDTOToTipoContaDto(tipoContaDTO);

        return tipoContaMapper.tipoContaToTipoContaDto(tipoContaRepository.save(tipoConta));
    }

    public TipoContaDTO update(Long id, TipoContaDTO tipoContaDTO) {

        TipoContaDTO tipoContaDtoBd = findById(id);
        tipoContaDtoBd.setDescricao(tipoContaDTO.getDescricao());

        TipoConta tipoConta = tipoContaMapper.tipoContaDTOToTipoContaDto(tipoContaDtoBd);

        return tipoContaMapper.tipoContaToTipoContaDto(tipoContaRepository.save(tipoConta));
    }

    public List<TipoContaDTO> findAll() {
        List<TipoConta> listTipoConta = tipoContaRepository.findAll();
        List<TipoContaDTO> listTipoContaDto = new ArrayList<>();

        for(TipoConta tipoConta : listTipoConta){
            listTipoContaDto.add(tipoContaMapper.tipoContaToTipoContaDto(tipoConta));
        }

        return listTipoContaDto;
    }

    public void delete(Long id) {
        TipoConta tipoConta = tipoContaRepository.findById(id).get();
        tipoContaRepository.delete(tipoConta);
    }

    public TipoContaDTO findById(Long id) {
        TipoConta tipoConta = tipoContaRepository.findById(id).get();

        return tipoContaMapper.tipoContaToTipoContaDto(tipoConta);
    }
}
