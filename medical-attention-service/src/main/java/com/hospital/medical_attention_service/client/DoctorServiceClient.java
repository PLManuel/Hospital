package com.hospital.medical_attention_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.medical_attention_service.dto.DoctorDTO;

@FeignClient(name = "doctor-service")
public interface DoctorServiceClient {
    @GetMapping("/doctors/dni/{dni}")
    DoctorDTO getDoctorByDni(@PathVariable String dni);
}
