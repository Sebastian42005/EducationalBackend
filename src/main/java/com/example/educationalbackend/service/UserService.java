package com.example.educationalbackend.service;

import com.example.educationalbackend.dto.UserDto;
import com.example.educationalbackend.dto.mapper.UserMapper;
import com.example.educationalbackend.entity.StudentEntity;
import com.example.educationalbackend.entity.SubjectEntity;
import com.example.educationalbackend.entity.TeacherEntity;
import com.example.educationalbackend.entity.UserEntity;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.repository.StudentRepository;
import com.example.educationalbackend.repository.SubjectRepository;
import com.example.educationalbackend.repository.TeacherRepository;
import com.example.educationalbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final UserMapper userMapper = new UserMapper();
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public UserEntity getOwnUser(String email) throws EntityNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(EntityType.USER, email));
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserEntity getUser(int id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.USER, id));
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto updateUser(UserEntity user) {
        UserEntity dbUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(EntityType.USER, user.getId()));
        dbUser.setEmail(user.getEmail());
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.getTeacher().getSubjects().forEach(subject -> {
            if (user.getTeacher().getSubjects().stream().noneMatch(current -> current.getId() == subject.getId())) {
                subject.getTeachers().remove(dbUser.getTeacher());
            }
        });
        dbUser.getTeacher().setSubjects(getAllDbSubjects(user.getTeacher()));
        dbUser.getTeacher().getStudents().forEach(student -> {
            if (user.getTeacher().getStudents().stream().noneMatch(current -> current.getId() == student.getId())) {
                student.getTeachers().remove(dbUser.getTeacher());
            }
        });
        dbUser.getTeacher().setStudents(getAllDBStudents(user.getTeacher()));
        return userMapper.apply(dbUser);
    }

    private List<SubjectEntity> getAllDbSubjects(TeacherEntity teacher) {
        return teacher.getSubjects().stream().map(item -> {
            SubjectEntity sub = subjectRepository.findById(item.getId()).orElseThrow(() -> new EntityNotFoundException(EntityType.SUBJECT, item.getId()));
            if (sub.getTeachers().stream().noneMatch(current -> current.getId() == teacher.getId())) {
                sub.getTeachers().add(teacher);
            }
            return sub;
        }).toList();
    }

    private List<StudentEntity> getAllDBStudents(TeacherEntity teacher) {
        return teacher.getStudents().stream().map(item -> {
            StudentEntity student = studentRepository.findById(item.getId()).orElseThrow(() -> new EntityNotFoundException(EntityType.STUDENT, item.getId()));
            if (student.getTeachers().stream().noneMatch(current -> current.getId() == teacher.getId())) {
                student.getTeachers().add(teacher);
            }
            return student;
        }).toList();
    }

    public List<UserDto> getUsersWithWorkshopRequests() {
        return userRepository.getUsersWithWorkshopRequest();
    }

    @Transactional
    public UserDto setUserProfilePicture(MultipartFile file, int id) throws IOException {
        UserEntity user = getUser(id);
        user.setImage(file.getBytes());
        user.setContentType(file.getContentType());
        return userMapper.apply(user);
    }

    public ResponseEntity<byte[]> getProfilePicture(int id) {
        UserEntity user = getUser(id);
        if (user.getContentType() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(user.getContentType())).body(user.getImage());
    }
}
