package kz.teacher.forge.teacherforge.controller;

import kz.teacher.forge.teacherforge.models.CustomUserDetails;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;
    public Optional<User> getCurrentUser(){
        UUID userId;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            userId = userDetails.getId();
            System.out.println("User ID: " + userId);
        } else {
            System.out.println("User ID cannot be found, principal is not an instance of UserDetails");
            throw new ApiException(ApiError.RESOURCE_NOT_FOUND , "not found user from context");
        }
        return userRepository.findById(userId);
    }
}
