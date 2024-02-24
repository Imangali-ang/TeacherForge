package kz.teacher.forge.teacherforge.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.teacher.forge.teacherforge.models.CustomUserDetails;
import kz.teacher.forge.teacherforge.models.token.Token;
import kz.teacher.forge.teacherforge.repository.TokenRepository;
import kz.teacher.forge.teacherforge.service.CustomUserDetailsService;
import kz.teacher.forge.teacherforge.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader==null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request , response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtUtils.getUsernameFromToken(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails userDetails = this.userDetailsService.loadUserByEmail(userEmail);
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            if(jwtUtils.validateToken(jwt) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                //TODO: impl exception
            }
        }
        filterChain.doFilter(request, response);
    }
}
