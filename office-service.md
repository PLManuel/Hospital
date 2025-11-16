# Office Service
[⬅ Volver al índice](./README.md)


Servicio encargado de la gestión de consultorios del hospital. Maneja el registro, consulta y administración de los consultorios médicos con sus especialidades asignadas.

### Endpoints

#### 1. Crear Consultorio

**Método:** `POST`

**URL:** `/api/offices`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Registra un nuevo consultorio en el sistema con sus especialidades asignadas.

**Request Body:**
```javascript
{
  name: string,         // Nombre del consultorio (requerido)
  address: string,      // Ubicación del consultorio (requerido)
  floor: number,        // Piso donde se encuentra (opcional)
  officeNumber: string, // Número del consultorio (opcional)
  status: boolean,      // Estado del consultorio (opcional, por defecto true)
  specialtyIds: number[] // IDs de especialidades asignadas (requerido, mínimo 1)
}
```

**Response (201 Created):**
```javascript
{
  id: number,           // ID del consultorio
  name: string,         // Nombre del consultorio
  address: string,      // Ubicación del consultorio
  floor: number,        // Piso donde se encuentra
  officeNumber: string, // Número del consultorio
  status: boolean,      // Estado del consultorio - opcional
  specialtyIds: number[] // IDs de especialidades asignadas
}
```

**Validaciones:**
- `name`: Campo obligatorio
- `address`: Campo obligatorio
- `specialtyIds`: Campo obligatorio, debe contener al menos una especialidad

#### 2. Obtener Todos los Consultorios

**Método:** `GET`

**URL:** `/api/offices`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la lista completa de todos los consultorios registrados.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
[
  {
    id: number,           // ID del consultorio
    name: string,         // Nombre del consultorio
    address: string,      // Ubicación del consultorio
    floor: number,        // Piso donde se encuentra
    officeNumber: string, // Número del consultorio
    status: boolean,      // Estado del consultorio - opcional
    specialtyIds: number[] // IDs de especialidades asignadas
  }
]
```

#### 3. Obtener Consultorio por ID

**Método:** `GET`

**URL:** `/api/offices/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la información de un consultorio específico buscado por su ID.

**Request Body:** No requiere

**Response (200 OK):**
```javascript
{
  id: number,           // ID del consultorio
  name: string,         // Nombre del consultorio
  address: string,      // Ubicación del consultorio
  floor: number,        // Piso donde se encuentra
  officeNumber: string, // Número del consultorio
  status: boolean,      // Estado del consultorio - opcional
  specialtyIds: number[] // IDs de especialidades asignadas
}
```

**Validaciones:**
- El consultorio con el ID especificado debe existir

#### 4. Deshabilitar Consultorio

**Método:** `PATCH`

**URL:** `/api/offices/disable/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Deshabilita un consultorio. No podrá ser usado para nuevos horarios o citas.

**Request Body:** No requiere

**Response (204 No Content):** Sin contenido

**Validaciones:**
- El consultorio con el ID especificado debe existir

#### 5. Habilitar Consultorio

**Método:** `PATCH`

**URL:** `/api/offices/enable/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Habilita un consultorio previamente deshabilitado.

**Request Body:** No requiere

**Response (204 No Content):** Sin contenido

**Validaciones:**
- El consultorio con el ID especificado debe existir

---
