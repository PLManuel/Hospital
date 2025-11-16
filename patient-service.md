# Patient Service
[⬅ Volver al índice](./README.md)


Servicio encargado de la gestión de pacientes del hospital. Maneja el registro, consulta y administración de la información de los pacientes.

### Endpoints

#### 1. Crear Paciente

**Método:** `POST`

**URL:** `/api/patients`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Registra un nuevo paciente en el sistema.

**Request Body:**
```javascript
{
  dni: string,        // DNI del paciente (requerido, 8 caracteres, único)
  name: string,       // Nombre del paciente (requerido, máx 255 caracteres)
  lastname: string,   // Apellido del paciente (requerido, máx 255 caracteres)
  address: string,    // Dirección del paciente (opcional, máx 500 caracteres)
  phone: string,      // Teléfono del paciente (opcional, máx 20 caracteres)
  birthdate: string,  // Fecha de nacimiento (requerido, formato YYYY-MM-DD, no puede ser futura)
  gender: string,     // Género: "MASCULINO" | "FEMENINO" (requerido)
  status: boolean     // Estado del paciente (opcional, por defecto true)
}
```

**Response (201 Created):**
```javascript
{
  id: number,         // ID del paciente
  dni: string,        // DNI del paciente
  name: string,       // Nombre del paciente
  lastname: string,   // Apellido del paciente
  address: string,    // Dirección del paciente
  phone: string,      // Teléfono del paciente
  birthdate: string,  // Fecha de nacimiento (formato YYYY-MM-DD)
  gender: string,     // Género del paciente
  status: boolean     // Estado del paciente - opcional
}
```

**Validaciones:**
- `dni`: Campo obligatorio, debe tener exactamente 8 caracteres y ser único
- `name`: Campo obligatorio, máximo 255 caracteres
- `lastname`: Campo obligatorio, máximo 255 caracteres
- `address`: Máximo 500 caracteres
- `phone`: Máximo 20 caracteres
- `birthdate`: Campo obligatorio, no puede ser una fecha futura
- `gender`: Campo obligatorio, debe ser MASCULINO o FEMENINO

#### 2. Obtener Todos los Pacientes

**Método:** `GET`

**URL:** `/api/patients`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la lista completa de todos los pacientes registrados.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
[
  {
    id: number,         // ID del paciente
    dni: string,        // DNI del paciente
    name: string,       // Nombre del paciente
    lastname: string,   // Apellido del paciente
    address: string,    // Dirección del paciente
    phone: string,      // Teléfono del paciente
    birthdate: string,  // Fecha de nacimiento (formato YYYY-MM-DD)
    gender: string,     // Género del paciente
    status: boolean     // Estado del paciente - opcional
  }
]
```

#### 3. Obtener Paciente por ID

**Método:** `GET`

**URL:** `/api/patients/id/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la información de un paciente específico buscado por su ID.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
{
  id: number,         // ID del paciente
  dni: string,        // DNI del paciente
  name: string,       // Nombre del paciente
  lastname: string,   // Apellido del paciente
  address: string,    // Dirección del paciente
  phone: string,      // Teléfono del paciente
  birthdate: string,  // Fecha de nacimiento (formato YYYY-MM-DD)
  gender: string,     // Género del paciente
  status: boolean     // Estado del paciente - opcional
}
```

**Validaciones:**
- El paciente con el ID especificado debe existir

#### 4. Obtener Paciente por DNI

**Método:** `GET`

**URL:** `/api/patients/dni/{dni}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la información de un paciente específico buscado por su DNI.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
{
  id: number,         // ID del paciente
  dni: string,        // DNI del paciente
  name: string,       // Nombre del paciente
  lastname: string,   // Apellido del paciente
  address: string,    // Dirección del paciente
  phone: string,      // Teléfono del paciente
  birthdate: string,  // Fecha de nacimiento (formato YYYY-MM-DD)
  gender: string,     // Género del paciente
  status: boolean     // Estado del paciente - opcional
}
```

**Validaciones:**
- El paciente con el DNI especificado debe existir

#### 5. Deshabilitar Paciente

**Método:** `PATCH`

**URL:** `/api/patients/disable/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Deshabilita un paciente. No podrá agendar nuevas citas médicas.

**Request Body:** No requiere

**Response (204 No Content):** Sin contenido

**Validaciones:**
- El paciente con el ID especificado debe existir

#### 6. Habilitar Paciente

**Método:** `PATCH`

**URL:** `/api/patients/enable/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Habilita un paciente previamente deshabilitado.

**Request Body:** No requiere

**Response (204 No Content):** Sin contenido

**Validaciones:**
- El paciente con el ID especificado debe existir

---
