# Medical History Service
[⬅ Volver al índice](./README.md)


Servicio encargado de la gestión de historiales médicos de los pacientes. Maneja la creación, consulta y administración de los historiales médicos con sus atenciones médicas.

### Endpoints

#### 1. Crear Historial Médico

**Método:** `POST`

**URL:** `/api/medical-histories`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Registra un nuevo historial médico para un paciente.

**Request Body:**
```javascript
{
  patientId: number,    // ID del paciente (requerido)
  employeeDni: string,  // DNI del empleado que crea el historial (requerido)
  height: number,       // Altura del paciente en metros (opcional, mayor a 0)
  weight: number,       // Peso del paciente en kg (opcional, mayor a 0)
  status: boolean       // Estado del historial (opcional, por defecto true)
}
```

**Response (201 Created):**
```javascript
{
  id: number,                    // ID del historial médico
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
  employee: {                    // Empleado que creó el historial
    id: number,
    dni: string,
    name: string,
    lastname: string,
    role: string,
    isEnabled: boolean           // opcional
  },
  height: number,                // Altura del paciente
  weight: number,                // Peso del paciente
  createdAt: string,             // Fecha de creación (formato ISO 8601)
  updatedAt: string,             // Fecha de última actualización (formato ISO 8601)
  status: boolean,               // Estado del historial - opcional
  medicalAttentions: [           // Lista de atenciones médicas
    {
      id: number                 // ID de la atención médica
    }
  ]
}
```

**Validaciones:**
- `patientId`: Campo obligatorio, el paciente debe existir
- `employeeDni`: Campo obligatorio, el empleado debe existir
- `height`: Debe ser mayor a 0 si se proporciona
- `weight`: Debe ser mayor a 0 si se proporciona

#### 2. Obtener Todos los Historiales Médicos

**Método:** `GET`

**URL:** `/api/medical-histories`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la lista completa de todos los historiales médicos registrados.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

#### 3. Obtener Historial Médico por ID

**Método:** `GET`

**URL:** `/api/medical-histories/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la información de un historial médico específico buscado por su ID.

**Request Body:** No requiere

**Response (200 OK):** Mismo formato que el endpoint de creación.

**Validaciones:**
- El historial médico con el ID especificado debe existir

#### 4. Obtener Historial Médico por Paciente

**Método:** `GET`

**URL:** `/api/medical-histories/patient/{patientId}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna el historial médico de un paciente específico.

**Request Body:** No requiere

**Response (200 OK):** Mismo formato que el endpoint de creación.

**Validaciones:**
- El paciente con el ID especificado debe existir

#### 5. Obtener Historiales Médicos por Empleado

**Método:** `GET`

**URL:** `/api/medical-histories/employee/{employeeDni}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna todos los historiales médicos creados por un empleado específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El empleado con el DNI especificado debe existir

#### 6. Obtener Historiales por Rango de Fecha de Creación

**Método:** `GET`

**URL:** `/api/medical-histories/created-at-range/{start}/{end}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna los historiales médicos creados dentro de un rango de fechas específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El formato debe ser ISO 8601 (ej: 2025-11-16T08:00:00)
- La fecha/hora de inicio debe ser anterior a la fecha/hora de fin

#### 7. Obtener Historiales por Rango de Fecha de Actualización

**Método:** `GET`

**URL:** `/api/medical-histories/updated-at-range/{start}/{end}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna los historiales médicos actualizados dentro de un rango de fechas específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El formato debe ser ISO 8601 (ej: 2025-11-16T08:00:00)
- La fecha/hora de inicio debe ser anterior a la fecha/hora de fin

#### 8. Deshabilitar Historial Médico

**Método:** `PATCH`

**URL:** `/api/medical-histories/disable/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Deshabilita un historial médico.

**Request Body:** No requiere

**Response (204 No Content):** Sin contenido

**Validaciones:**
- El historial médico con el ID especificado debe existir

#### 9. Habilitar Historial Médico

**Método:** `PATCH`

**URL:** `/api/medical-histories/enable/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Habilita un historial médico previamente deshabilitado.

**Request Body:** No requiere

**Response (204 No Content):** Sin contenido

**Validaciones:**
- El historial médico con el ID especificado debe existir

---
