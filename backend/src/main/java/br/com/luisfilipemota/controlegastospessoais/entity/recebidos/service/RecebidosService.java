package br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service;

import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.mapper.RecebidosMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.model.Recebidos;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.repository.RecebidosRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service.dto.RecebidosDTO;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecebidosService {

    private RecebidosRepository recebidosRepository;

    private RecebidosMapper recebidosMapper;

    public RecebidosService(RecebidosMapper contaMapper, RecebidosRepository contaRepository) {
        this.recebidosMapper = contaMapper;
        this.recebidosRepository = contaRepository;
    }

    public RecebidosDTO save(RecebidosDTO tipoContaDTO) {
        Recebidos conta = recebidosMapper.recebidosDTOToRecebidos(tipoContaDTO);

        conta = recebidosRepository.save(conta);

        return recebidosMapper.recebidosToRecebidosDto(conta);
    }

    public RecebidosDTO update(UUID id, RecebidosDTO contaDTO) throws NotFoundException {

        Optional<Recebidos> optionalConta = recebidosRepository.findById(id);

        if (!optionalConta.isPresent()) {
            throw new NotFoundException("Recebidos não encontrado");
        }

        Recebidos contaBd = recebidosMapper.recebidosDTOToRecebidos(contaDTO);
        contaBd.setId(optionalConta.get().getId());


        Recebidos contaUpdated = recebidosRepository.save(contaBd);
        contaDTO = recebidosMapper.recebidosToRecebidosDto(contaUpdated);

        return contaDTO;
    }

    public List<RecebidosDTO> findAll() {
        List<Recebidos> listTipoConta = recebidosRepository.findAll();
        List<RecebidosDTO> listTipoContaDto = new ArrayList<>();

        for (Recebidos tipoConta : listTipoConta) {
            listTipoContaDto.add(recebidosMapper.recebidosToRecebidosDto(tipoConta));
        }

        return listTipoContaDto;
    }

    public void delete(UUID id) throws NotFoundException {
        Optional<Recebidos> optionalTipoConta = recebidosRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Recebidos não encontrado");
        }

        Recebidos tipoConta = optionalTipoConta.get();
        recebidosRepository.delete(tipoConta);
    }

    public RecebidosDTO findById(UUID id) throws NotFoundException {
        Optional<Recebidos> optionalTipoConta = recebidosRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Recebidos não encontrado");
        }

        Recebidos tipoConta = optionalTipoConta.get();

        return recebidosMapper.recebidosToRecebidosDto(tipoConta);
    }
}
