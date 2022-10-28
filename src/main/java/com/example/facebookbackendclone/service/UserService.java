package com.example.facebookbackendclone.service;

import com.example.facebookbackendclone.model.Role;
import com.example.facebookbackendclone.model.RoleType;
import com.example.facebookbackendclone.model.User;
import com.example.facebookbackendclone.repository.RoleRepository;
import com.example.facebookbackendclone.repository.UserRepository;
import com.example.facebookbackendclone.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtTokenUtil;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) throws Exception {
        verifyUserDetails(user);
//        user.setPassword(getEncodedPassword(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user, String oldEmail) throws Exception {
        verifyUserDetails(user);
//        return userRepository.update(user.getFirstName(), user.getLastName(), user.getEmail(), user.getDob(), user.getCountry(), user.getGender(), user.getPassword());

        User oldUser = userRepository.findByEmail(oldEmail);

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String dob = user.getDob();
        String country = user.getCountry();
        String gender = user.getGender();
        String password = user.getPassword();

        oldUser.setFirstName(firstName);
        oldUser.setLastName(lastName);
        oldUser.setEmail(email);
        oldUser.setDob(dob);
        oldUser.setCountry(country);
        oldUser.setGender(gender);
        oldUser.setPassword(password);

        return userRepository.save(oldUser);

    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public Boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    private void verifyUserDetails(User user) throws Exception {
        if (user.getFirstName().isBlank()) {
            throw new Exception("Cannot create user with empty first name");
        }

        if (user.getLastName().isBlank()) {
            throw new Exception("Cannot create user with empty last name");
        }

        if (user.getEmail().isBlank()) {
            throw new Exception("Cannot create user with empty email address");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("User with email already exists");
        }

        if (user.getDob().isBlank() || !validateDateOfBirth(user.getDob())) {
            throw new Exception("Invalid Date of Birth. Enter a date in the form of dd/mm/yyyy");
        } else {
            if (user.getDob().contains("/")) {
                String dob = user.getDob().strip();
//                System.out.println("Date of Birth -> " + dob);
                int beginIndex = dob.lastIndexOf("/");
//                System.out.println("Last index of / -> " + beginIndex);
                String yearString = dob.substring(beginIndex+1);
//                System.out.println("Date of Birth -> " + yearString);
                int year = Integer.parseInt(yearString);
                if (year < 1950 || year > 2021) {
                    throw new Exception("Date of Birth cannot be less than 1950 or greater than 2022");
                }
            } else {
                throw new Exception("Invalid Date of Birth, please use '/' as separators");
            }
        }

        if (user.getCountry().isBlank()) {
            throw new Exception("Cannot create user without a country");
        }

        if (user.getGender().isBlank()) {
            throw new Exception("Cannot create user without a gender");
        } else if (!user.getGender().equalsIgnoreCase("M") && !user.getGender().equalsIgnoreCase("F")) {
            throw new Exception("Invalid gender. Gender can only be M or F");
        }

        if (user.getPassword().isBlank() || user.getPassword().length() < 8) {
            throw new Exception("Password should have a minimum of 8 characters");

        } else if (!validatePassword(user.getPassword())) {
            throw new Exception("Password must contain at least a number, one uppercase letter, one lowercase letter and a special character");
        }

    }

    private boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-+_!@#$%^&*., ?]).+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean validateDateOfBirth(String dob) {
        String regex = "[^a-zA-Z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dob);
        return matcher.matches();
    }


    public void initRoles() {
        Role nonAuthenticated = new Role();
        nonAuthenticated.setName(RoleType.ROLE_NON_AUTHENTICATED);
        roleRepository.save(nonAuthenticated);

        Role authenticated = new Role();
        nonAuthenticated.setName(RoleType.ROLE_AUTHENTICATED);
        roleRepository.save(authenticated);

        Role admin = new Role();
        nonAuthenticated.setName(RoleType.ROLE_ADMIN);
        roleRepository.save(admin);
    }
}
