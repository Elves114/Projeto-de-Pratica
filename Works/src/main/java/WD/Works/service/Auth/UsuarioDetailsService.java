package WD.Works.service.Auth;

import WD.Works.model.Usuario;
import WD.Works.repository.Auth.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Utilizador não encontrado"));

        System.out.println("Utilizador encontrado: " + usuario.getEmail());
        System.out.println("Bloqueado até: " + usuario.getBloqueadoAte());

        //  BLOQUEIO
        if (usuario.getBloqueadoAte() != null &&
                usuario.getBloqueadoAte().isAfter(LocalDateTime.now())) {

            throw new LockedException("CONTA_BLOQUEADA");
        }

        //  LIMPAR BLOQUEIO EXPIRED
        if (usuario.getBloqueadoAte() != null &&
                usuario.getBloqueadoAte().isBefore(LocalDateTime.now())) {

            usuario.setBloqueadoAte(null);
            usuario.setTentativasFalhadas(0);
            repository.save(usuario);
        }

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(usuario.getRole().name())
                .build();
    }
}