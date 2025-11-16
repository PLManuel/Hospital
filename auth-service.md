# Auth Service
[⬅ Volver al índice](./README.md)


Servicio encargado de la autenticación y autorización de usuarios en el sistema. Gestiona el login, generación de tokens JWT y control de acceso.

### Endpoints

#### 1. Login

**Método:** `POST`

**URL:** `/api/auth/login`

**Protegida:** ❌ No

**Descripción:** Permite a un usuario autenticarse en el sistema y obtener tokens de acceso (JWT).

**Request Body:**
```javascript
{
  dni: string,      // 8 dígitos numéricos (requerido)
  password: string  // Contraseña del usuario (requerido)
}
```

**Response (200 OK):**
```javascript
{
  accessToken: string,   // Token JWT para autenticación
  refreshToken: string,  // Token para renovar el accessToken
  employee: {
    id: number,          // ID del empleado
    name: string,        // Nombre del empleado
    lastname: string,    // Apellido del empleado
    dni: string,         // DNI del empleado (8 dígitos)
    role: string,        // Rol: "ADMIN" | "DOCTOR" | "ENFERMERA" | "RECEPCIONISTA" | "CAJERO"
    isEnabled: boolean   // Estado de la cuenta
  }
}
```

**Validaciones:**
- `dni`: Debe ser exactamente 8 dígitos numéricos
- `password`: Campo obligatorio, no puede estar vacío

#### 2. Deshabilitar Usuario

**Método:** `PATCH`

**URL:** `/api/users/disable/{dni}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Deshabilita la cuenta de un usuario. El usuario no podrá iniciar sesión hasta que sea habilitado nuevamente.

**Request Body:** No requiere

**Response (200 OK):** Sin contenido

**Validaciones:**
- El usuario con el DNI especificado debe existir

#### 3. Habilitar Usuario

**Método:** `PATCH`

**URL:** `/api/users/enable/{dni}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Habilita la cuenta de un usuario previamente deshabilitado.

**Request Body:** No requiere

**Response (200 OK):** Sin contenido

**Validaciones:**
- El usuario con el DNI especificado debe existir

---
