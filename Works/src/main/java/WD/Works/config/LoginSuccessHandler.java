package WD.Works.config;

import WD.Works.repository.Auth.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException {

        String email = authentication.getName();

        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            usuario.setTentativasFalhadas(0);
            usuario.setBloqueadoAte(null);
            usuarioRepository.save(usuario);
        });

        response.sendRedirect("/produtos");
    }
}