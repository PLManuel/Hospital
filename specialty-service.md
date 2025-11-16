# Specialty Service
[⬅ Volver al índice](./README.md)


Servicio encargado de la gestión de especialidades médicas. Maneja la creación, consulta y administración de las especialidades disponibles en el hospital.

### Endpoints

#### 1. Crear Especialidad

**Método:** `POST`

**URL:** `/api/specialties`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Registra una nueva especialidad médica en el sistema.

**Request Body:**
```javascript
{
  name: string,        // Nombre de la especialidad (requerido, 3-100 caracteres)
  description: string, // Descripción de la especialidad (opcional, máx 255 caracteres)
  cost: number,        // Costo de la consulta (requerido, mayor a 0)
  status: boolean      // Estado de la especialidad (opcional, por defecto true)
}
```

**Response (201 Created):**
```javascript
{
  id: number,          // ID de la especialidad
  name: string,        // Nombre de la especialidad
  description: string, // Descripción de la especialidad
  cost: number,        // Costo de la consulta
  status: boolean      // Estado de la especialidad - opcional
}
```

**Validaciones:**
- `name`: Campo obligatorio, entre 3 y 100 caracteres
- `description`: Máximo 255 caracteres
- `cost`: Campo obligatorio, debe ser mayor a 0

#### 2. Obtener Todas las Especialidades

**Método:** `GET`

**URL:** `/api/specialties`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la lista completa de todas las especialidades médicas registradas.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
[
  {
    id: number,          // ID de la especialidad
    name: string,        // Nombre de la especialidad
    description: string, // Descripción de la especialidad
    cost: number,        // Costo de la consulta
    status: boolean      // Estado de la especialidad - opcional
  }
]
```

#### 3. Obtener Especialidad por ID

**Método:** `GET`

**URL:** `/api/specialties/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la información de una especialidad específica buscada por su ID.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
{
  id: number,          // ID de la especialidad
  name: string,        // Nombre de la especialidad
  description: string, // Descripción de la especialidad
  cost: number,        // Costo de la consulta
  status: boolean      // Estado de la especialidad - opcional
}
```

**Validaciones:**
- La especialidad con el ID especificado debe existir

#### 4. Deshabilitar Especialidad

**Método:** `PATCH`

**URL:** `/api/specialties/disable/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Deshabilita una especialidad. No podrá ser asignada a nuevos doctores o horarios.

**Request Body:** No requiere

**Response (204 No Content):** Sin contenido

**Validaciones:**
- La especialidad con el ID especificado debe existir

#### 5. Habilitar Especialidad

**Método:** `PATCH`

**URL:** `/api/specialties/enable/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Habilita una especialidad previamente deshabilitada.

**Request Body:** No requiere

**Response (204 No Content):** Sin contenido

**Validaciones:**
- La especialidad con el ID especificado debe existir

---
