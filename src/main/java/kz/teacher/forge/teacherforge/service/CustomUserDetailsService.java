package kz.teacher.forge.teacherforge.service;


import kz.teacher.forge.teacherforge.models.CustomUserDetails;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetails loadUserByEmail(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userEmail));
        return new CustomUserDetails(user);
    }
}
