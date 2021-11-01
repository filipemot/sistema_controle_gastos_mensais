package br.com.luisfilipemota.controlegastospessoais.domains.usuario.service;

import br.com.luisfilipemota.controlegastospessoais.domains.usuario.mapper.IUsuarioMapper;
import br.com.luisfilipemota.controlegastospessoais.domains.usuario.model.Usuario;
import br.com.luisfilipemota.controlegastospessoais.domains.usuario.repository.IUsuarioRepository;
import br.com.luisfilipemota.controlegastospessoais.domains.usuario.service.dto.UsuarioDTO;
import com.google.common.hash.Hashing;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private IUsuarioRepository usuarioRepository;

    private IUsuarioMapper usuarioMapper;

    public UsuarioService(IUsuarioMapper usuarioMapper, IUsuarioRepository usuarioRepository) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        String senhaHash = Hashing.sha256()
                .hashString(usuarioDTO.getSenhaUsuario(), StandardCharsets.UTF_8)
                .toString();

        Usuario usuario = usuarioMapper.usuarioDtoToUsuario(usuarioDTO);

        usuario.setSenha(senhaHash);

        usuario = usuarioRepository.save(usuario);

        return usuarioMapper.usuarioToUsuarioDto(usuario);
    }

    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) throws NotFoundException {

        Optional<Usuario> optionalTipoConta = usuarioRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Usuario não encontrado");
        }
        String senhaHash = Hashing.sha256()
                .hashString(usuarioDTO.getSenhaUsuario(), StandardCharsets.UTF_8)
                .toString();

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

    public void delete(Long id) throws NotFoundException {
        Optional<Usuario> optionalTipoConta = usuarioRepository.findById(id);

        if (!optionalTipoConta.isPresent()) {
            throw new NotFoundException("Usuario não encontrado");
        }

        Usuario tipoConta = optionalTipoConta.get();
        usuarioRepository.delete(tipoConta);
    }

    public UsuarioDTO findById(Long id) throws NotFoundException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (!optionalUsuario.isPresent()) {
            throw new NotFoundException("Usuario não encontrado");
        }

        Usuario tipoConta = optionalUsuario.get();

        return usuarioMapper.usuarioToUsuarioDto(tipoConta);
    }
}
