package com.example.facebookbackendclone.controller;

import com.example.facebookbackendclone.model.*;
import com.example.facebookbackendclone.repository.RoleRepository;
import com.example.facebookbackendclone.request.*;
import com.example.facebookbackendclone.service.MyUserDetails;
import com.example.facebookbackendclone.service.UserService;
import com.example.facebookbackendclone.response.ResponseHandler;
//import com.example.facebookbackendclone.util.JwtUtil;
import com.example.facebookbackendclone.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/facebook")
public class UserController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtTokenUtil;



    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {
            if (userService.existsByEmail(user.getEmail())) {
                return ResponseHandler.getNewUserResponse(HttpStatus.MULTI_STATUS, null, "User with email already exists");
            }
            User newUser = userService.saveUser(user);

            return ResponseHandler.getNewUserResponse(HttpStatus.OK, String.valueOf(newUser.getId()), "User with name " + newUser.getFirstName() + " created successfully!");
        } catch (Exception e) {
            return ResponseHandler.getNewUserResponse(HttpStatus.MULTI_STATUS, null, e.getMessage());
        }

    }


    // This endpoint is used to make a particular user an admin so that he can get admin privileges
    @PostMapping("/user/admin")
    public ResponseEntity<Object> makeAdmin(@RequestBody UserEmail userEmail) {
        try {
            Role role = roleRepository.findByName(RoleType.ROLE_ADMIN).get();
            Set<Role> userRoles = new HashSet<>();

            User adminUser = userService.findByEmail(userEmail.getEmail());

            if (adminUser != null) {

                userRoles.add(role);

                if (adminUser.getRoles().iterator().hasNext()) {
                    userRoles.add(adminUser.getRoles().iterator().next());
                }
                adminUser.setRoles(userRoles);

                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(adminUser.getEmail(), adminUser.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = jwtTokenUtil.generateToken(authentication);

                return ResponseHandler.getAdminResponse(HttpStatus.OK, jwt, "User is now an admin");

            } else {
                return ResponseHandler.getAuthResponse(HttpStatus.NOT_FOUND, "User not found");
            }

        } catch (BadCredentialsException e) {
            return ResponseHandler.getAuthResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @PostMapping("/auth")
    public ResponseEntity<Object> loginUser(@RequestBody LoginUserRequest request) {
        try {
            Role role = roleRepository.findByName(RoleType.ROLE_AUTHENTICATED).get();
            Set<Role> userRoles = new HashSet<>();

            User user = userService.findByEmail(request.getEmail());

            if (user != null) {

                userRoles.add(role);

                if (user.getRoles().iterator().hasNext()) {
                    userRoles.add(user.getRoles().iterator().next());
                }
                user.setRoles(userRoles);

                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = jwtTokenUtil.generateToken(authentication);

                return ResponseHandler.getLoginResponse(HttpStatus.OK, jwt);

            } else {
                return ResponseHandler.getAuthResponse(HttpStatus.NOT_FOUND, "User not found");
            }

        } catch (BadCredentialsException e) {
            return ResponseHandler.getAuthResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('AUTHENTICATED')")
    @PostMapping("/user/logout")
    public ResponseEntity<Object> logoutUser(@RequestBody LogoutUserRequest logoutUserRequest) {
        try {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String email = userDetails.getUsername();

            User user = userService.findByEmail(logoutUserRequest.getEmail());

            if (email.equalsIgnoreCase(logoutUserRequest.getEmail())) {
                return ResponseHandler.getAuthResponse(HttpStatus.OK, "User with name " + user.getFirstName() + " logged out successfully");
            } else {
                return ResponseHandler.getAuthResponse(HttpStatus.BAD_REQUEST, "User with email " + logoutUserRequest.getEmail() + " not logged in");
            }
        } catch (Exception e) {
            return ResponseHandler.getAuthResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }

    @PreAuthorize("hasRole('AUTHENTICATED') or hasRole('ADMIN')")
    @DeleteMapping("/user")
    public ResponseEntity<Object> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) {
        try {
            User userToDelete = userService.findById(deleteUserRequest.getUserId());

            if (userToDelete != null) {
                userService.deleteById(userToDelete.getId());
                return ResponseHandler.getAuthResponse(HttpStatus.OK, "User with name " + userToDelete.getFirstName() + " deleted successfully");
            } else {
                return ResponseHandler.getAuthResponse(HttpStatus.NOT_FOUND, "User with email " + deleteUserRequest.getEmail() + " not found");
            }

        } catch (Exception e) {
            return ResponseHandler.getAuthResponse(HttpStatus.BAD_REQUEST, "Could not delete user because " + e.getMessage());
        }

    }


    @PreAuthorize("hasRole('AUTHENTICATED') or hasRole('ADMIN')")
    @PutMapping("/user")
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        try {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String email = userDetails.getUsername();

            User updatedUser = userService.updateUser(user, email);
            return ResponseHandler.getNewUserResponse(HttpStatus.OK, String.valueOf(updatedUser.getId()), "User with name " + updatedUser.getFirstName() + " updated successfully!");
        } catch (Exception e) {
            return ResponseHandler.getNewUserResponse(HttpStatus.UNAUTHORIZED, null, e.getMessage());
        }
    }


    @PreAuthorize("hasRole('AUTHENTICATED') or hasRole('ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<Object> getUserDetails(@RequestBody GetUserRequest request) {

        User user = userService.findById(request.getUserId());

        if (user != null ) {
            return ResponseHandler.getUserResponse(HttpStatus.OK, user);
        } else {
            return ResponseHandler.getAuthResponse(HttpStatus.BAD_REQUEST, "User not found");
        }

    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/users")
    public ResponseEntity<Object> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseHandler.getAllUsersResponse(HttpStatus.OK, users);
    }


}
