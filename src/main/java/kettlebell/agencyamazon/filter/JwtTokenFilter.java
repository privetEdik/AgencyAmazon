package kettlebell.agencyamazon.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kettlebell.agencyamazon.service.user.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nullable HttpServletResponse response,
                                    @Nullable FilterChain filterChain)
            throws IOException, ServletException {

        final String authorization = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            Objects.requireNonNull(filterChain).doFilter(request, response);
            return;
        }

        final String jwtToken = authorization.substring(BEARER_PREFIX.length());

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            if (jwtService.validateToken(jwtToken)) {
                final String username = jwtService.extractUsername(jwtToken);
                final UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        Objects.requireNonNull(filterChain).doFilter(request, response);
    }
}
