package br.com.luisfilipemota.controlegastospessoais.entity.usuario.service;

import br.com.luisfilipemota.controlegastospessoais.entity.usuario.mapper.UsuarioMapper;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.repository.UsuarioRepository;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static br.com.luisfilipemota.controlegastospessoais.util.seguranca.Sha256.getSenha;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioMapper usuarioMapper, UsuarioRepository usuarioRepository) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        String senhaHash = getSenha(usuarioDTO.getSenhaUsuario());

        Usuario usuario = usuarioMapper.usuarioDtoToUsuario(usuarioDTO);

        usuario.setSenha(senhaHash);

        usuario = usuarioRepository.save(usuario);

        return usuarioMapper.usuarioToUsuarioDto(usuario);
    }

    public UsuarioDTO update(UUID id, UsuarioDTO usuarioDTO) throws NotFoundException {
        String senhaHash = getSenha(usuarioDTO.getSenhaUsuario());

        Usuario tipoContaBd = getUsuario(id);
        tipoContaBd.setNome(usuarioDTO.getNome());
        tipoContaBd.setEmail(usuarioDTO.getEmail());
        tipoContaBd.setSenha(senhaHash);

        Usuario tipoContaUpdated = usuarioRepository.save(tipoContaBd);
        usuarioDTO = usuarioMapper.usuarioToUsuarioDto(tipoContaUpdated);

        return usuarioDTO;
    }

    public List<UsuarioDTO> findAll() {
        List<Usuario> listTipoConta = usuarioRepository.findAll();
        List<UsuarioDTO> listTipoContaDto = new ArrayList<>();

        for (Usuario tipoConta : listTipoConta) {
            listTipoContaDto.add(usuarioMapper.usuarioToUsuarioDto(tipoConta));
        }

        return listTipoContaDto;
    }

    public void delete(UUID id) throws NotFoundException {
        Usuario tipoConta = getUsuario(id);
        usuarioRepository.delete(tipoConta);
    }

    public UsuarioDTO findById(UUID id) throws NotFoundException {
        Usuario tipoConta = getUsuario(id);

        return usuarioMapper.usuarioToUsuarioDto(tipoConta);
    }

    private Usuario getUsuario(UUID id) throws NotFoundException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (!optionalUsuario.isPresent()) {
            throw new NotFoundException("Usuario não encontrado");
        }

        return optionalUsuario.get();
    }
}
