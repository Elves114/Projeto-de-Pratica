package WD.Works.config;

import WD.Works.repository.Auth.UsuarioRepository;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException exception)
            throws IOException, ServletException {

        // 🔒 SE ESTIVER BLOQUEADO → vai direto para página bloqueada
        if (exception instanceof LockedException) {
            response.sendRedirect("/bloqueado");
            return;
        }

        String email = request.getParameter("username");

        usuarioRepository.findByEmail(email).ifPresent(usuario -> {

            // 🚫 não mexer se já estiver bloqueado
            if (usuario.getBloqueadoAte() != null &&
                    usuario.getBloqueadoAte().isAfter(LocalDateTime.now())) {
                return;
            }

            Integer tentativas = usuario.getTentativasFalhadas();
            if (tentativas == null) tentativas = 0;

            tentativas++;

            usuario.setTentativasFalhadas(tentativas);

            System.out.println("Tentativa falhada: " + tentativas);

            if (tentativas >= 5) {
                usuario.setBloqueadoAte(
                        LocalDateTime.now().plusMinutes(15)
                );
                usuario.setTentativasFalhadas(0);

                System.out.println("UTILIZADOR BLOQUEADO");
            }

            usuarioRepository.save(usuario);
        });

        response.sendRedirect("/login?error");
    }

}