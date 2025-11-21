# Appointment Service
[⬅ Volver al índice](./README.md)


Servicio encargado de la gestión de citas médicas. Maneja la creación, consulta y administración de las citas médicas de los pacientes.

### Endpoints

#### 1. Crear Cita Médica

**Método:** `POST`

**URL:** `/api/appointments`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Registra una nueva cita médica para un paciente en un horario específico.

**Request Body:**
```javascript
{
  scheduleId: number,   // ID del horario (requerido)
  employeeDni: string,  // DNI del empleado que crea la cita (requerido)
  patientId: number,    // ID del paciente (requerido)
  status: Status       // Estado: "PENDIENTE" | "CANCELADO" | "COMPLETADO" | "NOASISTIO" - opcional
}
```

**Response (201 Created):**
```javascript
{
  id: number,                    // ID de la cita
  schedule: {                    // Información del horario
    id: number,
    doctor: {
      id: number,
      dni: string,
      name: string,
      lastname: string,
      codigo: string,
      specialties: []
    },
    specialty: {
      id: number,
      name: string,
      description: string,
      cost: number,
      status: boolean            // opcional
    },
    office: {
      id: number,
      name: string,
      address: string,
      floor: number,
      officeNumber: string,
      status: boolean,           // opcional
      specialtyIds: number[]
    },
    date: string,                // Fecha (formato YYYY-MM-DD)
    startTime: string,           // Hora de inicio (formato HH:mm:ss)
    endTime: string,             // Hora de fin (formato HH:mm:ss)
    status: boolean              // opcional
  },
  employee: {                    // Empleado que registró la cita
    id: number,
    dni: string,
    name: string,
    lastname: string,
    role: string,
    isEnabled: boolean           // opcional
  },
  patient: {                     // Información del paciente
    id: number,
    dni: string,
    name: string,
    lastname: string,
    address: string,
    phone: string,
    birthdate: string,
    gender: string,
    status: boolean              // opcional
  },
  solicitationDateTime: string,  // Fecha y hora de solicitud (formato ISO 8601)
  finalCost: number,             // Costo final de la consulta
  status: string                 // Estado: "PENDIENTE" | "CANCELADO" | "COMPLETADO" | "NOASISTIO" - opcional
}
```

**Validaciones:**
- `scheduleId`: Campo obligatorio, el horario debe existir y estar disponible
- `employeeDni`: Campo obligatorio, el empleado debe existir
- `patientId`: Campo obligatorio, el paciente debe existir

#### 2. Obtener Todas las Citas

**Método:** `GET`

**URL:** `/api/appointments`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la lista completa de todas las citas médicas registradas.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

#### 3. Obtener Cita por ID

**Método:** `GET`

**URL:** `/api/appointments/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la información de una cita médica específica buscada por su ID.

**Request Body:** No requiere

**Response (200 OK):** Mismo formato que el endpoint de creación.

**Validaciones:**
- La cita con el ID especificado debe existir

#### 4. Obtener Citas por Horario

**Método:** `GET`

**URL:** `/api/appointments/schedule/{scheduleId}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna todas las citas médicas asociadas a un horario específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El horario con el ID especificado debe existir

#### 5. Obtener Citas por Empleado

**Método:** `GET`

**URL:** `/api/appointments/employee/{employeeDni}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna todas las citas médicas registradas por un empleado específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El empleado con el DNI especificado debe existir

#### 6. Obtener Citas por Consultorio

**Método:** `GET`

**URL:** `/api/appointments/office/{officeId}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna todas las citas médicas asignadas a un consultorio específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El consultorio con el ID especificado debe existir

#### 7. Obtener Citas por Rango de Fecha y Hora

**Método:** `GET`

**URL:** `/api/appointments/datetime-range/{start}/{end}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna las citas médicas que se encuentran dentro de un rango de fecha y hora específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El formato debe ser ISO 8601 (ej: 2025-11-16T08:00:00)
- La fecha/hora de inicio debe ser anterior a la fecha/hora de fin

#### 8. Cambiar Estado de Cita

**Método:** `PATCH`

**URL:** `/api/appointments/{id}/status?status={status}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Actualiza el estado de una cita médica.

**Request Body:** No requiere

**Query Parameters:**
- `status`: Estado de la cita (requerido) - Valores: `PENDIENTE`, `CANCELADO`, `COMPLETADO`, `NOASISTIO`

**Response (204 No Content):** Sin contenido

**Validaciones:**
- La cita con el ID especificado debe existir
- El estado debe ser uno de los valores permitidos

---
