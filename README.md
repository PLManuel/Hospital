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

| Microservicio             | Descripción                              | Documento |
|---------------------------|------------------------------------------|-----------|
| Auth Service              | Login, tokens JWT y estado de usuarios   | [auth-service.md](./auth-service/README.md) |
| Employee Service          | Gestión de empleados                     | [employee-service.md](./employee-service/README.md) |
| Specialty Service         | Gestión de especialidades médicas        | [specialty-service.md](./specialty-service/README.md) |
| Doctor Service            | Gestión de doctores y sus especialidades | [doctor-service.md](./doctor-service/README.md) |
| Patient Service           | Gestión de pacientes                     | [patient-service.md](./patient-service/README.md) |
| Office Service            | Gestión de consultorios                  | [office-service.md](./office-service/README.md) |
| Schedule Service          | Gestión de horarios médicos              | [schedule-service.md](./schedule-service/README.md) |
| Appointment Service       | Gestión de citas médicas                 | [appointment-service.md](./appointment-service/README.md) |
| Medical History Service   | Gestión de historiales médicos           | [medical-history-service.md](./medical-history-service/README.md) |
| Receipt Service           | Gestión de comprobantes de pago          | [receipt-service.md](./receipt-service/README.md) |
| Medical Attention Service | Gestión de atenciones medicas            | [medical-attention-service.md](./medical-attention-service/README.md) |

> Para el detalle de cada microservicio, revisa su documento correspondiente.
