package com.ltp.backend.service;

import java.lang.StackWalker.Option;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ltp.backend.entity.ConfirmationToken;
import com.ltp.backend.entity.Role;
import com.ltp.backend.entity.User;
import com.ltp.backend.entity.UserRegistrationInformation;
import com.ltp.backend.repository.UserRepository;
import com.ltp.backend.exception.UserExistsException;
import com.ltp.backend.exception.UserNotFoundWithEmailException;
import com.ltp.backend.exception.UserNotFoundWithIdException;
import com.ltp.backend.message.UsernameResponse;

@Service
public class UserServiceIml implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public String signUpUser(UserRegistrationInformation userRegistrationInformation) {
        // TODO Auto-generated method stub
        if (userRepository.existsUserByEmailAndChecks(userRegistrationInformation.getEmail(), true)) {
            throw new UserExistsException(
                    "This user with this email exists already and this email is also already activated!");
        }

        User user = new User(userRegistrationInformation.getUsername(), userRegistrationInformation.getEmail(),
                userRegistrationInformation.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User createdUser = userRepository.save(user);

        // id: 1L means role with admin (only admin role in this project)
        Role role = roleService.getRoleById(1L);

        createdUser.addRolesToUser(role);

        User createdUserWithRole = userRepository.save(createdUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confimationToken = new ConfirmationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), createdUserWithRole);

        confirmationTokenService.saveConfirmationToken(confimationToken);
        return token;

    }

    @Override
    public User getUserWithId(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundWithIdException("This user id " + id + " does not exist!");
        }
    }

    @Override
    public User getUserWithEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundWithEmailException("The user with this email " + email + " does not exist!");
        }
    }

    @Override
    public User getLoggedInUserWithEmail(String email) {
        // TODO Auto-generated method stub
        Optional<User> user = userRepository.findByEmailAndChecks(email, true);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundWithEmailException(
                    "This email " + email + " does not exist or this email is not yet validated!");
        }
    }

    @Override
    public User updateUsername(UsernameResponse usernameResponse, Long id) {
        User user = getUserWithId(id);

        user.setUsername(usernameResponse.getUsername());

        return userRepository.save(user);
    }

}
