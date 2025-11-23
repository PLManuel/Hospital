package com.hospital.employee_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hospital.employee_service.client.AuthServiceClient;
import com.hospital.employee_service.dto.EmployeeRequestDTO;
import com.hospital.employee_service.exception.CustomException;
import com.hospital.employee_service.model.Role;
import com.hospital.employee_service.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataInitializer {

  @Bean
  public CommandLineRunner initializeAdmin(
      EmployeeService employeeService,
      AuthServiceClient authServiceClient,
      DiscoveryClient discoveryClient) {
    return args -> {
      try {
        waitForAuthServiceAndCreateAdmin(employeeService, authServiceClient, discoveryClient);
      } catch (Exception e) {
        log.error("❌ Error en la inicialización: {}", e.getMessage(), e);
      }
    };
  }

  private void waitForAuthServiceAndCreateAdmin(EmployeeService employeeService,
      AuthServiceClient authServiceClient,
      DiscoveryClient discoveryClient) throws InterruptedException {
    int maxRetries = 20;
    long waitTimeMs = 5000;

    for (int attempt = 0; attempt < maxRetries; attempt++) {
      if (isAuthServiceHealthy(discoveryClient, authServiceClient)) {
        log.info("✅ auth-service está disponible y saludable, creando el usuario ADMIN.");
        createAdmin(employeeService);
        return;
      }
      // log.warn("auth-service no está disponible, reintentando... (intento {}/{})",
      // attempt + 1, maxRetries);
      Thread.sleep(waitTimeMs);
    }
    log.error("❌ auth-service no se ha registrado o no está saludable después de {} intentos.", maxRetries);
  }

  private boolean isAuthServiceHealthy(DiscoveryClient discoveryClient, AuthServiceClient authServiceClient) {
    try {
      if (discoveryClient.getInstances("auth-service").isEmpty()) {
        log.warn("auth-service no está registrado en Eureka.");
        return false;
      }
      if (!"OK".equals(authServiceClient.healthCheck())) {
        log.warn("auth-service no pasó el Health Check.");
        return false;
      }
      log.info("✅ auth-service está disponible y saludable.");
      return true;
    } catch (Exception e) {
      log.error("❌ Error al verificar auth-service: {}", e.getMessage(), e);
      return false;
    }
  }

  private void createAdmin(EmployeeService employeeService) {
    EmployeeRequestDTO adminDto = new EmployeeRequestDTO();
    adminDto.setName("Admin");
    adminDto.setLastname("System");
    adminDto.setDni("12345678");
    adminDto.setRole(Role.ADMIN);
    adminDto.setPassword("admin123");
    try {
      employeeService.createEmployee(adminDto);
      log.info("✅ Usuario ADMIN creado exitosamente.");
    } catch (CustomException e) {
      if (e.getMessage().contains("ya existe")) {
        log.info("✅ Usuario ADMIN ya existe, omitiendo creación.");
      } else {
        log.error("❌ Error al crear el usuario ADMIN: {}", e.getMessage(), e);
      }
    } catch (Exception e) {
      log.error("❌ Error al crear el usuario ADMIN: {}", e.getMessage(), e);
    }
  }
}
