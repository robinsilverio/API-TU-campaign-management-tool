package com.example.tu_campaign_management_tool_api.controllers;

import com.example.tu_campaign_management_tool_api.payload.request.LoginRequest;
import com.example.tu_campaign_management_tool_api.payload.responses.ErrorResponse;
import com.example.tu_campaign_management_tool_api.payload.responses.JwtResponse;
import com.example.tu_campaign_management_tool_api.payload.responses.RolesResponse;
import com.example.tu_campaign_management_tool_api.security.jwt.JwtUtils;
import com.example.tu_campaign_management_tool_api.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @GetMapping("/validateJwt")
    public ResponseEntity<?> validateJwtToken(@RequestParam String paramToken) {
        if (jwtUtils.validateJwtToken(paramToken)) {
            return ResponseEntity.ok(new RolesResponse(jwtUtils.getRolesFromJwtToken(paramToken)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid Token"));
        }
    }

}
