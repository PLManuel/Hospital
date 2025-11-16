# Doctor Service
[⬅ Volver al índice](./README.md)


Servicio encargado de la gestión de doctores del hospital. Maneja el registro y consulta de doctores con sus especialidades asignadas.

### Endpoints

#### 1. Obtener Todos los Doctores

**Método:** `GET`

**URL:** `/api/doctors`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la lista completa de todos los doctores registrados con sus especialidades.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
[
  {
    id: number,        // ID del doctor
    dni: string,       // DNI del doctor
    codigo: string,    // Código único del doctor
    name: string,      // Nombre del doctor
    lastname: string,  // Apellido del doctor
    specialties: [     // Lista de especialidades del doctor
      {
        id: number,          // ID de la especialidad
        name: string,        // Nombre de la especialidad
        description: string, // Descripción de la especialidad
        cost: number,        // Costo de la consulta
        status: boolean      // Estado de la especialidad - opcional
      }
    ]
  }
]
```

#### 2. Obtener Doctor por DNI

**Método:** `GET`

**URL:** `/api/doctors/dni/{dni}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la información de un doctor específico buscado por su DNI.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
{
  id: number,        // ID del doctor
  dni: string,       // DNI del doctor
  codigo: string,    // Código único del doctor
  name: string,      // Nombre del doctor
  lastname: string,  // Apellido del doctor
  specialties: [     // Lista de especialidades del doctor
    {
      id: number,          // ID de la especialidad
      name: string,        // Nombre de la especialidad
      description: string, // Descripción de la especialidad
      cost: number,        // Costo de la consulta
      status: boolean      // Estado de la especialidad - opcional
    }
  ]
}
```

**Validaciones:**
- El doctor con el DNI especificado debe existir

#### 3. Obtener Doctores por Especialidad

**Método:** `GET`

**URL:** `/api/doctors/specialty/{specialtyId}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la lista de doctores que tienen asignada una especialidad específica.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
[
  {
    id: number,        // ID del doctor
    dni: string,       // DNI del doctor
    codigo: string,    // Código único del doctor
    name: string,      // Nombre del doctor
    lastname: string,  // Apellido del doctor
    specialties: [     // Lista de especialidades del doctor
      {
        id: number,          // ID de la especialidad
        name: string,        // Nombre de la especialidad
        description: string, // Descripción de la especialidad
        cost: number,        // Costo de la consulta
        status: boolean      // Estado de la especialidad - opcional
      }
    ]
  }
]
```

**Validaciones:**
- La especialidad con el ID especificado debe existir

---
