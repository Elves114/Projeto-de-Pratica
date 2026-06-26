package WD.Works.service.Auth;

import WD.Works.model.Auth.PasswordResetToken;
import WD.Works.model.Auth.Role;
import WD.Works.model.Usuario;
import WD.Works.repository.Auth.PasswordResetTokenRepository;
import WD.Works.repository.Auth.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void resetarSenha(String token, String novaSenha) {

        PasswordResetToken resetToken =
                tokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Token inválido"));

        if (resetToken.getExpiracao().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        Usuario usuario = resetToken.getUsuario();

        usuario.setSenha(
                passwordEncoder.encode(novaSenha)
        );

        usuarioRepository.save(usuario);

        tokenRepository.delete(resetToken);
    }

    public void cadastrar(Usuario usuario) {

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );
        usuario.setRole(Role.ROLE_USER);
        usuarioRepository.save(usuario);
    }
    public void alterarSenha(
            String email,
            String senhaAtual,
            String novaSenha,
            String confirmarSenha) {

        Usuario usuario =
                usuarioRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("Utilizador não encontrado"));

        if (!passwordEncoder.matches(
                senhaAtual,
                usuario.getSenha())) {

            throw new RuntimeException(
                    "Senha atual incorreta");
        }

        if (!novaSenha.equals(confirmarSenha)) {

            throw new RuntimeException(
                    "As senhas não coincidem");
        }

        usuario.setSenha(
                passwordEncoder.encode(novaSenha)
        );

        usuarioRepository.save(usuario);
    }
}