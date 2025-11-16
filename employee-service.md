# Employee Service
[⬅ Volver al índice](./README.md)


Servicio encargado de la gestión de empleados del hospital. Maneja el registro, consulta y administración de usuarios del sistema.

### Endpoints

#### 1. Registrar Empleado

**Método:** `POST`

**URL:** `/api/auth/register`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Crea un nuevo empleado en el sistema. Si el rol es DOCTOR, también registra sus especialidades.

**Request Body:**
```javascript
{
  name: string,            // Nombre del empleado (requerido)
  lastname: string,        // Apellido del empleado (requerido)
  dni: string,            // DNI de 8 caracteres (requerido, único)
  role: string,           // "ADMIN" | "DOCTOR" | "ENFERMERA" | "RECEPCIONISTA" | "CAJERO" (requerido)
  password: string,       // Contraseña (requerido)
  specialtyIds: number[]  // Array de IDs de especialidades (opcional, requerido solo para DOCTOR)
}
```

**Response (201 Created):**
```javascript
{
  id: number,          // ID del empleado
  name: string,        // Nombre del empleado
  lastname: string,    // Apellido del empleado
  dni: string,         // DNI del empleado
  role: string,        // Rol asignado
  isEnabled: boolean   // Estado (siempre true al crear) - opcional
}
```

**Validaciones:**
- `name`: Campo obligatorio, no puede estar vacío
- `lastname`: Campo obligatorio, no puede estar vacío
- `dni`: Debe tener exactamente 8 caracteres y ser único
- `role`: Debe ser un rol válido
- `password`: Campo obligatorio
- `specialtyIds`: Requerido si el rol es DOCTOR

#### 2. Obtener Todos los Empleados

**Método:** `GET`

**URL:** `/api/users`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la lista completa de todos los empleados registrados en el sistema.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
[
  {
    id: number,          // ID del empleado
    name: string,        // Nombre del empleado
    lastname: string,    // Apellido del empleado
    dni: string,         // DNI del empleado
    role: string,        // Rol asignado
    isEnabled: boolean   // Estado de la cuenta - opcional
  }
]
```

#### 3. Obtener Empleado por DNI

**Método:** `GET`

**URL:** `/api/users/dni/{dni}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la información de un empleado específico buscado por su DNI.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
{
  id: number,          // ID del empleado
  name: string,        // Nombre del empleado
  lastname: string,    // Apellido del empleado
  dni: string,         // DNI del empleado
  role: string,        // Rol asignado
  isEnabled: boolean   // Estado de la cuenta - opcional
}
```

**Validaciones:**
- El empleado con el DNI especificado debe existir

#### 4. Obtener Empleado por ID

**Método:** `GET`

**URL:** `/api/users/id/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la información de un empleado específico buscado por su ID.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
{
  id: number,          // ID del empleado
  name: string,        // Nombre del empleado
  lastname: string,    // Apellido del empleado
  dni: string,         // DNI del empleado
  role: string,        // Rol asignado
  isEnabled: boolean   // Estado de la cuenta - opcional
}
```

**Validaciones:**
- El empleado con el ID especificado debe existir

---
