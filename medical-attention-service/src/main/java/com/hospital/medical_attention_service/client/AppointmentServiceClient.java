package com.hospital.medical_attention_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.medical_attention_service.dto.AppointmentDTO;

@FeignClient(name= "appointment-service")
public interface AppointmentServiceClient {
    @GetMapping("/appointment/{id}")
    AppointmentDTO getAppointmentById(@PathVariable Long id);
}
