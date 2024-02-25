package kz.teacher.forge.teacherforge.controller.admin;

import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.UserDto;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin/schools/{schoolId}/users")
public class UserAdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PutMapping("/{userId}")
    public User edit(@PathVariable("userId") UUID userId,
                     @RequestBody UserDto userdto){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ApiException(ApiError.RESOURCE_NOT_FOUND , "not found user"));
        Optional.ofNullable(userdto.getUserName()).ifPresent(userName -> user.setUserName(userName));
        Optional.ofNullable(userdto.getLastName()).ifPresent(userLastname -> user.setLastName(userLastname));
        Optional.ofNullable(userdto.getMiddleName()).ifPresent(userMiddleName -> user.setMiddleName(userMiddleName));
        Optional.ofNullable(userdto.getPassword()).ifPresent(userPassword -> user.setPassword(passwordEncoder.encode(userPassword)));
        Optional.ofNullable(userdto.getPosition()).ifPresent(position -> user.setPosition(position));
        Optional.ofNullable(userdto.getNumber()).ifPresent(number-> user.setPhoneNumber(number));
        Optional.ofNullable(userdto.getCategory()).ifPresent(category -> user.setCategory(category));
        return userRepository.save(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable("userId") UUID userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<User> getUserByFullName(@RequestParam("name") String name ,
                                        @PathVariable("schoolId") UUID schoolId,
                                        @RequestParam("role") String role) {
        return userRepository.findByName(name , role.toUpperCase() , schoolId);
    }
}
