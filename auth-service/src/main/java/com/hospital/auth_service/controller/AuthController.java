package com.hospital.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.hospital.auth_service.dto.AuthRequestDTO;
import com.hospital.auth_service.dto.AuthResponseDTO;
import com.hospital.auth_service.dto.EmployeeDTO;
import com.hospital.auth_service.model.Employee;
import com.hospital.auth_service.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @GetMapping("/health")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("OK");
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
    AuthResponseDTO response = authService.login(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(@Valid @RequestBody Employee request) {
    authService.registerUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/user/{dni}")
  public ResponseEntity<EmployeeDTO> getUserByDni(@PathVariable String dni) {
    EmployeeDTO employeeDTO = authService.getUserByDni(dni);
    return ResponseEntity.ok(employeeDTO);
  }

  @PatchMapping("/disable/{dni}")
  public ResponseEntity<Void> disableUser(@PathVariable String dni) {
    authService.disableUser(dni);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/enable/{dni}")
  public ResponseEntity<Void> enableUser(@PathVariable String dni) {
    authService.enableUser(dni);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/delete/{dni}")
  public ResponseEntity<Void> deleteUser(@PathVariable String dni) {
    authService.deleteUser(dni);
    return ResponseEntity.noContent().build();
  }
}
