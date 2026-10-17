package daviddev.todosimple_api.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import daviddev.todosimple_api.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAutheticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTUtil jwtUtil;

    public JWTAutheticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        // Envia o AuthenticationManager para a classe pai configurar o contexto corretamente
        super(authenticationManager);
        setFilterProcessesUrl("/login");
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // 1. Valida se o método é POST
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            throw new org.springframework.security.authentication.AuthenticationServiceException("Método HTTP não suportado para login. Use POST.");
        }

        try {
            // 2. Valida se o body está vazio (Content-Length igual a 0)
            if (request.getContentLength() == 0) {
                throw new BadCredentialsException("O corpo da requisição de login está vazio.");
            }

            // 3. Lê o JSON de forma segura mapeando para a classe User
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            if (user == null || user.getUsername() == null || user.getPassword() == null) {
                throw new BadCredentialsException("Usuário ou senha não informados no JSON.");
            }

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>());

            // 4. Executa a autenticação usando o gerenciador herdado da classe pai
            return getAuthenticationManager().authenticate(authenticationToken);

        } catch (IOException e) {
            throw new org.springframework.security.authentication.AuthenticationServiceException(
                    "Erro ao processar o JSON de login: " + e.getMessage(), e
            );
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authentication) {

        UserSpringSecurity userSpringSecurity =
                (UserSpringSecurity) authentication.getPrincipal();

        String username = userSpringSecurity.getUsername();

        String token = this.jwtUtil.generateToken(username);

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
    }
}