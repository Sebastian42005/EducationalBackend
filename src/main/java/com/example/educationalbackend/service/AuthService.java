package com.example.educationalbackend.service;

import com.example.educationalbackend.config.jwt.JwtTokenUtil;
import com.example.educationalbackend.config.jwt.JwtUserDetailsService;
import com.example.educationalbackend.config.ShaUtils;
import com.example.educationalbackend.dto.AuthenticationRequest;
import com.example.educationalbackend.dto.AuthenticationResponse;
import com.example.educationalbackend.entity.StudentEntity;
import com.example.educationalbackend.entity.TeacherEntity;
import com.example.educationalbackend.entity.UserEntity;
import com.example.educationalbackend.entity.enums.UserRole;
import com.example.educationalbackend.exception.exceptions.RoleNotExistsException;
import com.example.educationalbackend.exception.exceptions.WrongLoginCredentialsException;
import com.example.educationalbackend.repository.StudentRepository;
import com.example.educationalbackend.repository.TeacherRepository;
import com.example.educationalbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil tokenUtil;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws WrongLoginCredentialsException {
        final UserDetails userDetails = userDetailsService.verifyUser(authenticationRequest.email(), ShaUtils.decode(authenticationRequest.password()));
        final String token = tokenUtil.generateToken(userDetails);
        return new AuthenticationResponse(token, userDetails.getAuthorities().stream().findFirst().orElseThrow(() -> new RoleNotExistsException("")).getAuthority());
    }

    @Transactional
    public UserEntity register(UserEntity userEntity) {
        Optional<UserEntity> user = userRepository.findByEmail(userEntity.getEmail());
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        if (userEntity.getRole() == UserRole.TEACHER) {
            TeacherEntity teacherEntity = new TeacherEntity();
            userEntity.setTeacher(teacherRepository.save(teacherEntity));
        } else if (userEntity.getRole() == UserRole.STUDENT) {
            StudentEntity student = new StudentEntity();
            userEntity.setStudent(studentRepository.save(student));
        }
        userEntity.setPassword(ShaUtils.decode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return userEntity;
    }
}
