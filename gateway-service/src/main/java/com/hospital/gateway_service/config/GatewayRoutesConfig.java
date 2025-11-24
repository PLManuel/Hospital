package com.hospital.gateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Configuration;
import com.hospital.gateway_service.enums.Role;
import com.hospital.gateway_service.filter.AuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class GatewayRoutesConfig {

  @Bean
  public RouteLocator customRoutes(RouteLocatorBuilder builder) {
    return builder.routes()

        // ---- ---- ---- ---- ---- Rutas Publicas ---- ---- ---- ---- ---- //
        .route("auth_login", r -> r
            .path("/api/auth/login")
            .filters(f -> f.stripPrefix(1))
            .uri("lb://auth-service"))
        // ---------------------------------------------------------------- //

        // ---- ---- ---- ---- ---- Rutas Privadas ---- ---- ---- ---- ---- //
        .route("auth_register", r -> r
            .path("/api/auth/register")
            .and().method(HttpMethod.POST)
            .filters(f -> f
                .stripPrefix(2)
                .rewritePath("/register", "/employees")
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://employee-service"))

        .route("get_all_users", r -> r
            .path("/api/users")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .rewritePath("/users", "/employees")
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://employee-service"))

        .route("get_user_by_dni", r -> r
            .path("/api/users/dni/{dni}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .rewritePath("/users", "/employees")
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://employee-service"))

        .route("get_user_by_id", r -> r
            .path("/api/users/id/{id}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .rewritePath("/users", "/employees")
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://employee-service"))

        .route("disable_user", r -> r
            .path("/api/users/disable/{dni}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .rewritePath("/users", "/auth")
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://auth-service"))

        .route("enable_user", r -> r
            .path("/api/users/enable/{dni}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .rewritePath("/users", "/auth")
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://auth-service"))

        // ---------------------------------------------------------------- //

        .route("create_specialty", r -> r
            .path("/api/specialties")
            .and().method(HttpMethod.POST)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://specialty-service"))

        .route("get_all_specialties", r -> r
            .path("/api/specialties")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://specialty-service"))

        .route("get_specialty_by_id", r -> r
            .path("/api/specialties/{id}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://specialty-service"))

        .route("disable_specialty", r -> r
            .path("/api/specialties/disable/{id}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://specialty-service"))

        .route("enable_specialty", r -> r
            .path("/api/specialties/enable/{id}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://specialty-service"))

        // ---------------------------------------------------------------- //

        .route("get_all_doctors", r -> r
            .path("/api/doctors")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://doctor-service"))

        .route("get_doctor_by_dni", r -> r
            .path("/api/doctors/dni/{dni}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://doctor-service"))

        .route("get_doctor_by_specialtyId", r -> r
            .path("/api/doctors/specialty/{specialtyId}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://doctor-service"))

        // ---------------------------------------------------------------- //

        .route("create_patient", r -> r
            .path("/api/patients")
            .and().method(HttpMethod.POST)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://patient-service"))

        .route("get_all_patients", r -> r
            .path("/api/patients")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://patient-service"))

        .route("get_patient_by_id", r -> r
            .path("/api/patients/id/{id}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://patient-service"))

        .route("get_patient_by_dni", r -> r
            .path("/api/patients/dni/{dni}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://patient-service"))

        .route("disable_patient", r -> r
            .path("/api/patients/disable/{id}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://patient-service"))

        .route("enable_patient", r -> r
            .path("/api/patients/enable/{id}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://patient-service"))

        // ---------------------------------------------------------------- //

        .route("create_office", r -> r
            .path("/api/offices")
            .and().method(HttpMethod.POST)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://office-service"))

        .route("get_all_offices", r -> r
            .path("/api/offices")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://office-service"))

        .route("get_office_by_id", r -> r
            .path("/api/offices/{id}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://office-service"))

        .route("disable_office", r -> r
            .path("/api/offices/disable/{id}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://office-service"))

        .route("enable_office", r -> r
            .path("/api/offices/enable/{id}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://office-service"))

        // ---------------------------------------------------------------- //

        .route("create_schedule", r -> r
            .path("/api/schedules")
            .and().method(HttpMethod.POST)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://schedule-service"))

        .route("get_all_schedules", r -> r
            .path("/api/schedules")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://schedule-service"))

        .route("update_schedule", r -> r
            .path("/api/schedules/{id}")
            .and().method(HttpMethod.PUT)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://schedule-service"))

        .route("get_schedule_by_id", r -> r
            .path("/api/schedules/{id}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://schedule-service"))

        .route("get_schedule_by_doctor_dni", r -> r
            .path("/api/schedules/doctor/{doctorDni}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://schedule-service"))

        .route("get_schedule_by_specialty_id", r -> r
            .path("/api/schedules/specialty/{specialtyId}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://schedule-service"))

        .route("get_schedule_by__doctor_dni_and_specialty_id", r -> r
            .path("/api/schedules/doctor/{doctorDni}/specialty/{specialtyId}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://schedule-service"))

        .route("get_schedule_by_office_id", r -> r
            .path("/api/schedules/office/{officeId}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://schedule-service"))

        .route("get_schedule_by_time_range", r -> r
            .path("/api/schedules/time-range/{startTime}/{endTime}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://schedule-service"))

        .route("disable_schedule", r -> r
            .path("/api/schedules/disable/{id}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://schedule-service"))

        .route("enable_schedule", r -> r
            .path("/api/schedules/enable/{id}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://schedule-service"))

        // ---------------------------------------------------------------- //

        .route("create_appointment", r -> r
            .path("/api/appointments")
            .and().method(HttpMethod.POST)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://appointment-service"))

        .route("get_all_appointments", r -> r
            .path("/api/appointments")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://appointment-service"))

        .route("get_appointment_by_id", r -> r
            .path("/api/appointments/{id}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://appointment-service"))

        .route("get_appointment_by_schedule_id", r -> r
            .path("/api/appointments/schedule/{scheduleId}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://appointment-service"))

        .route("get_appointments_by_employee_dni", r -> r
            .path("/api/appointments/employee/{employeeDni}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://appointment-service"))

        .route("get_appointments_by_office_id", r -> r
            .path("/api/appointments/office/{officeId}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://appointment-service"))

        .route("get_appointments_by_datetime_range", r -> r
            .path("/api/appointments/datetime-range/{start}/{end}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://appointment-service"))

        .route("change_status_appointment", r -> r
            .path("/api/appointments/{id}/status")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://appointment-service"))

        // ---------------------------------------------------------------- //

        .route("create_medical_history", r -> r
            .path("/api/medical-histories")
            .and().method(HttpMethod.POST)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://medical-history-service"))

        .route("get_all_medical_histories", r -> r
            .path("/api/medical-histories")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://medical-history-service"))

        .route("update_medical_history", r -> r
            .path("/api/medical-histories/{id}")
            .and().method(HttpMethod.PUT)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://medical-history-service"))

        .route("get_medical_history_by_id", r -> r
            .path("/api/medical-histories/{id}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://medical-history-service"))

        .route("get_medical_history_by_patient_id", r -> r
            .path("/api/medical-histories/patient/{patientId}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://medical-history-service"))

        .route("get_medical_histories_by_employee_dni", r -> r
            .path("/api/medical-histories/employee/{employeeDni}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://medical-history-service"))

        .route("get_medical_histories_by_created_at_range", r -> r
            .path("/api/medical-histories/created-at-range/{start}/{end}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://medical-history-service"))

        .route("get_medical_histories_by_updated_at_range", r -> r
            .path("/api/medical-histories/updated-at-range/{start}/{end}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://medical-history-service"))

        .route("disable_medical_history", r -> r
            .path("/api/medical-histories/disable/{id}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://medical-history-service"))

        .route("enable_medical_history", r -> r
            .path("/api/medical-histories/enable/{id}")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://medical-history-service"))

        // ---------------------------------------------------------------- //

        .route("create_receipt", r -> r
            .path("/api/receipts")
            .and().method(HttpMethod.POST)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://receipt-service"))

        .route("get_all_receipts", r -> r
            .path("/api/receipts")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://receipt-service"))

        // .route("update_receipt", r -> r
        //     .path("/api/receipts/{id}")
        //     .and().method(HttpMethod.PUT)
        //     .filters(f -> f
        //         .stripPrefix(1)
        //         .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
        //     .uri("lb://receipt-service"))

        .route("get_receipt_by_id", r -> r
            .path("/api/receipts/{id}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://receipt-service"))

        .route("get_receipt_by_appointment_id", r -> r
            .path("/api/receipts/appointment/{appointmentId}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://receipt-service"))

        .route("get_receipts_by_employee_id", r -> r
            .path("/api/receipts/employee/{employeeDni}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://receipt-service"))

        .route("get_receipts_by_created_at_range", r -> r
            .path("/api/receipts/created-at-range/{start}/{end}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://receipt-service"))

        .route("change_status_receipt", r -> r
            .path("/api/receipts/{id}/status")
            .and().method(HttpMethod.PATCH)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(Role.ADMIN.getRoleName())))
            .uri("lb://receipt-service"))


        // -------------------------------------------------------------------//
        .route("create_medical_attention", r -> r
            .path("/api/medical-attention")
            .and().method(HttpMethod.POST)
            .filters(f -> f
                .stripPrefix(1) // /api/medical-attention -> /medical-attention
                .filter(AuthorizationFilter.ofAny(
                    Role.ADMIN.getRoleName(),
                    Role.DOCTOR.getRoleName()
                )))
            .uri("lb://medical-attention-service"))

        .route("get_all_medical_attentions", r -> r
            .path("/api/medical-attention")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(
                    Role.ADMIN.getRoleName(),
                    Role.DOCTOR.getRoleName()
                )))
            .uri("lb://medical-attention-service"))

        .route("get_medical_attention_by_id", r -> r
            .path("/api/medical-attention/{id}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(
                    Role.ADMIN.getRoleName(),
                    Role.DOCTOR.getRoleName()
                )))
            .uri("lb://medical-attention-service"))

        .route("get_medical_attention_by_doctor", r -> r
            .path("/api/medical-attention/by-doctor/{doctorDni}")
            .and().method(HttpMethod.GET)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(
                    Role.ADMIN.getRoleName(),
                    Role.DOCTOR.getRoleName()
                )))
            .uri("lb://medical-attention-service"))

        .route("update_medical_attention", r -> r
            .path("/api/medical-attention/{id}")
            .and().method(HttpMethod.PUT)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(
                    Role.ADMIN.getRoleName(),
                    Role.DOCTOR.getRoleName()
                )))
            .uri("lb://medical-attention-service"))

        .route("delete_medical_attention", r -> r
            .path("/api/medical-attention/{id}")
            .and().method(HttpMethod.DELETE)
            .filters(f -> f
                .stripPrefix(1)
                .filter(AuthorizationFilter.ofAny(
                    Role.ADMIN.getRoleName(),
                    Role.DOCTOR.getRoleName()
                )))
            .uri("lb://medical-attention-service"))
        // ----------------------------------------------------//

        
        .build();
  }
}