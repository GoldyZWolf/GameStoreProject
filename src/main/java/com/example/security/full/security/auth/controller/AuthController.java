package com.example.security.full.security.auth.controller;

import com.example.security.full.security.UserSecurity.dao.JpaUserDetailsService;
import com.example.security.full.security.auth.request.AuthenticationRequest;
import com.example.security.full.security.auth.service.AuthService;
import com.example.security.full.security.config.JwtAuthFilter;
import com.example.security.full.security.config.JwtUtils;
import com.example.security.full.security.users.Requests.UsersRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin(
    origins = {
        "http://localhost:3000", 
        }
    /*methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
}*/
)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JpaUserDetailsService jpaUserDetailsService;

    private final AuthService authService;

    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword(),
                            new ArrayList<>()));
            final UserDetails user = jpaUserDetailsService.loadUserByEmail(request.getEmail());
            if (user != null) {
                String jwt = jwtUtils.generateToken(user);
                Cookie cookie = new Cookie("jwt", jwt);
                cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
//                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/"); // Global
                response.addCookie(cookie);
                return ResponseEntity.ok(jwt);
            }
            return ResponseEntity.status(400).body("Error authenticating");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).body("" + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsersRequest user) throws Exception {
        try {
            //return ResponseEntity.ok(authService.AddUser(user).map(UserSecurity::new).orElseThrow(() -> new Exception("Unknown")));
            authService.AddUser(user);
            return ResponseEntity.status(200).body("User created");
        } catch(Exception e) {
            return ResponseEntity.status(204).body("Please check that email and username are unique in system!");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody HttpServletRequest request) throws Exception {
        JwtAuthFilter.removeSessionAndCookies(request);
        return ResponseEntity.status(200).body("Logged out successfully!");
    }

    @GetMapping("/home")
    public ResponseEntity<String> home() throws Exception {
        //JwtAuthFilter.removeSessionAndCookies(request);
        return ResponseEntity.status(200).body("Hello there");
    }

    @GetMapping("/register")
    public ResponseEntity<String> getRegisterExample() throws Exception {
        return ResponseEntity.status(200).body("This is an example of get method request to register API");
    }

}
