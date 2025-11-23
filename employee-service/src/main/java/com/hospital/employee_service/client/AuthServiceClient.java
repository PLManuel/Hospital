package com.hospital.employee_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.hospital.employee_service.dto.RegisterRequestDTO;
import com.hospital.employee_service.dto.EmployeeDTO;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

  @GetMapping("/auth/health")
  String healthCheck();

  @PostMapping("/auth/register")
  void registerUser(RegisterRequestDTO request);

  @GetMapping("/auth/user/{dni}")
  EmployeeDTO getUserByDni(@PathVariable("dni") String dni);

  @DeleteMapping("/auth/delete/{dni}")
  void deleteUser(@PathVariable("dni") String dni);
}
