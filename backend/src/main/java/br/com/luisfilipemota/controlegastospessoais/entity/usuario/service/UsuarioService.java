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

        Optional<Usuario> optionalTipoConta = usuarioRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Usuario não encontrado");
        }

        String senhaHash = getSenha(usuarioDTO.getSenhaUsuario());

        Usuario tipoContaBd = optionalTipoConta.get();
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
        Optional<Usuario> optionalTipoConta = usuarioRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Usuario não encontrado");
        }

        Usuario tipoConta = optionalTipoConta.get();
        usuarioRepository.delete(tipoConta);
    }

    public UsuarioDTO findById(UUID id) throws NotFoundException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (!optionalUsuario.isPresent()) {
            throw new NotFoundException("Usuario não encontrado");
        }

        Usuario tipoConta = optionalUsuario.get();

        return usuarioMapper.usuarioToUsuarioDto(tipoConta);
    }

    private String getSenha(String texto){

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
             return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
