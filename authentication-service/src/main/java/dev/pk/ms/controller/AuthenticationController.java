package dev.pk.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pk.ms.jwt.JwtUtil;
import dev.pk.ms.model.AuthResponse;
import dev.pk.ms.model.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Replace with DB/LDAP logic in real-world scenario
    	System.out.println("_____________________________Login method");
        if ("john".equals(request.userName()) && "password123".equals(request.password())) {
        	System.out.println("_____________________________In if");
            List<String> roles = List.of("CUSTOMER"); // hardcoded for now
            String token = jwtUtil.generateToken(request.userName(), roles);
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
