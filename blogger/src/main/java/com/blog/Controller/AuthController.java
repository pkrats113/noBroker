package com.blog.Controller;

import com.blog.Entity.Role;
import com.blog.Entity.User;
import com.blog.Payload.JWTAuthResponse;
import com.blog.Payload.SignInDto;
import com.blog.Payload.SignupDto;
import com.blog.Repository.RoleRepository;
import com.blog.Repository.UserRepository;
import com.blog.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @PostMapping("/signup")
    public ResponseEntity<SignupDto> registerUser(@RequestBody SignupDto signupDto){
        User user=new User();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setUsername(signupDto.getUsername());
        Role role = roleRepository.findByRoleName("Role_ADMIN");
        user.setRole(Collections.singleton(role));

        User savedUser = userRepository.save(user);
        SignupDto dto=new SignupDto();
        dto.setId(signupDto.getId());
        dto.setName(signupDto.getName());
        dto.setEmail(signupDto.getEmail());
        dto.setPassword(signupDto.getPassword());
        dto.setUsername(signupDto.getUsername());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody SignInDto signInDto){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(signInDto.getUsernameOrEmail(), signInDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtTokenProvider.generateToken(authenticate);

        return  ResponseEntity.ok(new JWTAuthResponse(token));

    }



}
