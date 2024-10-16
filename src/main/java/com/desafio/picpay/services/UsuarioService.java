package com.desafio.picpay.services;

import com.desafio.picpay.dtos.TipoUsuario;
import com.desafio.picpay.domain.usuario.Usuario;
import com.desafio.picpay.dtos.UsuarioDTO;
import com.desafio.picpay.exception.UsuarioExeption;
import com.desafio.picpay.repositories.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validarTransacao(Usuario remetente, BigDecimal valor) {
        if (remetente.getTipoUsuario() == TipoUsuario.LOJISTA) {
            throw new UsuarioExeption("Usuário do tipo Lojista não está autorizado a realizar transação");
        }

        if (remetente.getSaldo().compareTo(valor) < 0) {
            throw new UsuarioExeption("Saldo insuficiente");
        }
    }

    public Usuario encontrarUsuarioPorId(Long id) {
        return this.usuarioRepository.findUsuarioById(id).orElseThrow(() -> new UsuarioExeption("Usuário não encontrado"));
    }

    public void salvarUsuario(Usuario usuario) {
        this.usuarioRepository.save(usuario);
    }

    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario novoUsuario = new Usuario(usuarioDTO);
        this.salvarUsuario(novoUsuario);
        return novoUsuario;
    }

    public List<Usuario> listarUsuarios() {
        return this.usuarioRepository.findAll();
    }
}
