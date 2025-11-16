# Hospital Management System - API Documentation

**Base URL:** `http://localhost:8080`

Todas las rutas de la API están bajo el prefijo `/api`.

---

## Autenticación con Bearer Token

Para todas las rutas protegidas del sistema, debes incluir el token JWT en el header `Authorization`:

```javascript
const token = localStorage.getItem('accessToken');

fetch('http://localhost:8080/api/auth/register', {
  method: 'POST',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    name: 'Juan',
    lastname: 'Pérez',
    dni: '87654321',
    role: 'DOCTOR',
    password: 'password123',
    specialtyIds: [1, 2]
  })
})
.then(response => response.json())
.then(data => console.log(data));
```

---


---
## 📚 Índice de microservicios

| Microservicio            | Descripción                                  | Documento |
|--------------------------|----------------------------------------------|-----------|
| Auth Service             | Login, tokens JWT y estado de usuarios       | [auth-service.md](./auth-service.md) |
| Employee Service         | Gestión de empleados                         | [employee-service.md](./employee-service.md) |
| Specialty Service        | Gestión de especialidades médicas            | [specialty-service.md](./specialty-service.md) |
| Doctor Service           | Gestión de doctores y sus especialidades     | [doctor-service.md](./doctor-service.md) |
| Patient Service          | Gestión de pacientes                         | [patient-service.md](./patient-service.md) |
| Office Service           | Gestión de consultorios                      | [office-service.md](./office-service.md) |
| Schedule Service         | Gestión de horarios médicos                  | [schedule-service.md](./schedule-service.md) |
| Appointment Service      | Gestión de citas médicas                     | [appointment-service.md](./appointment-service.md) |
| Medical History Service  | Gestión de historiales médicos               | [medical-history-service.md](./medical-history-service.md) |
| Receipt Service          | Gestión de comprobantes de pago              | [receipt-service.md](./receipt-service.md) |

> Para el detalle de cada microservicio, revisa su documento correspondiente.
