# Receipt Service
[⬅ Volver al índice](./README.md)


Servicio encargado de la gestión de boletas y comprobantes de pago. Maneja la creación, consulta y administración de las boletas generadas por las citas médicas.

### Endpoints

#### 1. Crear Boleta

**Método:** `POST`

**URL:** `/api/receipts`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Registra una nueva boleta de pago para una cita médica. El monto total se calcula automáticamente desde la cita médica.

**Request Body:**
```javascript
{
  employeeDni: string,     // DNI del empleado que crea la boleta (requerido, 8-15 caracteres)
  appointmentId: number,   // ID de la cita médica (requerido)
  paymentMethod: string,   // Método de pago: "CONTADO" | "CREDITO" | "DEBITO" | "TRANSFERENCIA" (requerido)
  ruc: string,            // RUC para factura (opcional, máx 20 caracteres)
  companyName: string,    // Nombre de empresa para factura (opcional, máx 255 caracteres)
  status: string          // Estado: "PENDIENTE" | "PAGADO" | "ANULADO" | "FALLIDO" (opcional, por defecto PENDIENTE)
}
```

**Response (201 Created):**
```javascript
{
  id: number,                    // ID de la boleta
  employee: {                    // Empleado que creó la boleta
    id: number,
    dni: string,
    name: string,
    lastname: string,
    role: string,
    isEnabled: boolean           // opcional
  },
  medicalAppointment: {          // Cita médica asociada
    id: number,
    schedule: {
      id: number,
      doctor: {},
      specialty: {},
      office: {},
      date: string,
      startTime: string,
      endTime: string,
      status: boolean            // opcional
    },
    patient: {
      id: number,
      dni: string,
      name: string,
      lastname: string,
      address: string,
      phone: string,
      birthdate: string,
      gender: string,
      status: boolean            // opcional
    },
    solicitationDateTime: string,
    finalCost: number
  },
  paymentMethod: string,         // Método de pago
  totalAmount: number,           // Monto total (calculado automáticamente)
  ruc: string,                   // RUC de la empresa
  companyName: string,           // Nombre de la empresa
  createdAt: string,             // Fecha de creación (formato ISO 8601)
  status: string                 // Estado de la boleta - opcional
}
```

**Validaciones:**
- `employeeDni`: Campo obligatorio, debe tener entre 8 y 15 caracteres
- `appointmentId`: Campo obligatorio, la cita médica debe existir
- `paymentMethod`: Campo obligatorio, debe ser un método válido
- `ruc`: Máximo 20 caracteres
- `companyName`: Máximo 255 caracteres

#### 2. Obtener Todas las Boletas

**Método:** `GET`

**URL:** `/api/receipts`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la lista completa de todas las boletas registradas.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

#### 3. Obtener Boleta por ID

**Método:** `GET`

**URL:** `/api/receipts/{id}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna la información de una boleta específica buscada por su ID.

**Request Body:** No requiere

**Response (200 OK):** Mismo formato que el endpoint de creación.

**Validaciones:**
- La boleta con el ID especificado debe existir

#### 4. Obtener Boletas por Cita Médica

**Método:** `GET`

**URL:** `/api/receipts/appointment/{appointmentId}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna todas las boletas asociadas a una cita médica específica.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- La cita médica con el ID especificado debe existir

#### 5. Obtener Boletas por Empleado

**Método:** `GET`

**URL:** `/api/receipts/employee/{employeeDni}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna todas las boletas creadas por un empleado específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El empleado con el DNI especificado debe existir

#### 6. Obtener Boletas por Rango de Fecha de Creación

**Método:** `GET`

**URL:** `/api/receipts/created-at-range/{start}/{end}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Retorna las boletas creadas dentro de un rango de fechas específico.

**Request Body:** No requiere

**Response (200 OK):** Array con la misma estructura del objeto de respuesta del endpoint de creación.

**Validaciones:**
- El formato debe ser ISO 8601 (ej: 2025-11-16T08:00:00)
- La fecha/hora de inicio debe ser anterior a la fecha/hora de fin

#### 7. Cambiar Estado de Boleta

**Método:** `PATCH`

**URL:** `/api/receipts/{id}/status?status={status}`

**Protegida:** ✅ Sí (Requiere rol: ADMIN)

**Descripción:** Actualiza el estado de una boleta de pago.

**Request Body:** No requiere

**Query Parameters:**
- `status`: Estado de la boleta (requerido) - Valores: `PENDIENTE`, `PAGADO`, `ANULADO`, `FALLIDO`

**Response (204 No Content):** Sin contenido

**Validaciones:**
- La boleta con el ID especificado debe existir
- El estado debe ser uno de los valores permitidos

---