# Schedule Service
[⬅ Volver al índice](./README.md)


Servicio encargado de la gestión de horarios médicos. Maneja la creación, consulta y administración de los horarios de disponibilidad de los doctores.

### Endpoints

#### 1. Crear Horario

**Método:** `POST`

**URL:** `/api/schedules`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Registra un nuevo horario de disponibilidad para un doctor en una especialidad y consultorio específico.

**Request Body:**
```javascript
{
  doctorDni: string,    // DNI del doctor (requerido)
  employeeDni: string,  // DNI del empleado que crea el horario (requerido)
  specialtyId: number,  // ID de la especialidad (requerido)
  officeId: number,     // ID del consultorio (requerido)
  date: string,         // Fecha del horario (requerido, formato YYYY-MM-DD)
  startTime: string,    // Hora de inicio (requerido, formato HH:mm:ss)
  endTime: string,      // Hora de fin (requerido, formato HH:mm:ss)
  status: boolean       // Estado del horario (opcional, por defecto true)
}
```

**Response (201 Created):**
```javascript
{
  id: number,      // ID del horario
  doctor: {
    id: number,
    dni: string,
    name: string,
    lastname: string,
    codigo: string,
    specialties: [
      {
        id: number,
        name: string,
        description: string,
        cost: number,
        status: boolean    // opcional
      }
    ]
  },
  employee: {
    id: number,
    dni: string,
    name: string,
    lastname: string,
    role: string,
    isEnabled: boolean   // opcional
  },
  specialty: {
    id: number,
    name: string,
    description: string,
    cost: number,
    status: boolean      // opcional
  },
  office: {
    id: number,
    name: string,
    address: string,
    floor: number,
    officeNumber: string,
    status: boolean,     // opcional
    specialtyIds: number[]
  },
  date: string,          // Fecha del horario (formato YYYY-MM-DD)
  startTime: string,     // Hora de inicio (formato HH:mm:ss)
  endTime: string,       // Hora de fin (formato HH:mm:ss)
  status: boolean        // Estado del horario - opcional
}
```

**Validaciones:**
- Todos los campos marcados como requeridos son obligatorios
- El doctor debe existir y tener la especialidad asignada
- El consultorio debe existir y soportar la especialidad
- La fecha no puede ser pasada
- La hora de fin debe ser posterior a la hora de inicio

#### 2. Obtener Todos los Horarios

**Método:** `GET`

**URL:** `/api/schedules`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la lista completa de todos los horarios registrados.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

#### 3. Obtener Horario por ID

**Método:** `GET`

**URL:** `/api/schedules/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la información de un horario específico buscado por su ID.

**Request Body:** No requiere

**Response (200 OK):** Mismo formato que el endpoint de creación.

**Validaciones:**
- El horario con el ID especificado debe existir

#### 4. Obtener Horarios por Doctor

**Método:** `GET`

**URL:** `/api/schedules/doctor/{doctorDni}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna todos los horarios de un doctor específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El doctor con el DNI especificado debe existir

#### 5. Obtener Horarios por Especialidad

**Método:** `GET`

**URL:** `/api/schedules/specialty/{specialtyId}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna todos los horarios de una especialidad específica.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- La especialidad con el ID especificado debe existir

#### 6. Obtener Horarios por Doctor y Especialidad

**Método:** `GET`

**URL:** `/api/schedules/doctor/{doctorDni}/specialty/{specialtyId}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna los horarios de un doctor filtrados por una especialidad específica.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El doctor y la especialidad deben existir

#### 7. Obtener Horarios por Consultorio

**Método:** `GET`

**URL:** `/api/schedules/office/{officeId}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna todos los horarios asignados a un consultorio específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El consultorio con el ID especificado debe existir

#### 8. Obtener Horarios por Rango de Horas

**Método:** `GET`

**URL:** `/api/schedules/time-range/{startTime}/{endTime}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna los horarios que se encuentran dentro de un rango de horas específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El formato de las horas debe ser HH:mm:ss
- La hora de inicio debe ser anterior a la hora de fin

#### 9. Deshabilitar Horario

**Método:** `PATCH`

**URL:** `/api/schedules/disable/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Deshabilita un horario. No podrá ser usado para nuevas citas.

**Request Body:** No requiere

**Response (204 No Content):** Sin contenido

**Validaciones:**
- El horario con el ID especificado debe existir

#### 10. Habilitar Horario

**Método:** `PATCH`

**URL:** `/api/schedules/enable/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Habilita un horario previamente deshabilitado.

**Request Body:** No requiere

**Response (204 No Content):** Sin contenido

**Validaciones:**
- El horario con el ID especificado debe existir

---
