# Learning Platform REST API

Java 21, Spring Boot 4.1.1 (versión original), Springdoc 3.0.2 (compatibilidad con Boot 4.1.1 pendiente de verificar). Maven 3.9.x instalado localmente; el ZIP original no incluye .mvn/wrapper.

## Ejecutar

```bash
mvn clean package -DskipTests
mvn spring-boot:run -Dspring-boot.run.profiles=demo
```

El perfil demo usa H2 en memoria y pierde sus datos al detenerse. Sin perfil se conserva MySQL y application.properties original. No hay autenticación implementada.

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Pruebas desde Swagger

1. POST `/api/v1/student` con `{"firstName":"Ana","lastName":"Perez","email":"ana@example.com"}`. Guardar id.
2. POST `/api/v1/course` con `{"code":"CS101","title":"Java","description":"Introduccion","credits":4,"capacity":20}`. Guardar id.
3. POST `/api/v1/enrollment` con `{"studentId":1,"courseId":1}` (usar IDs reales). Guardar id.
4. POST `/api/v1/enrollment/{id}/confirm` sin cuerpo.
5. GET `/api/v1/enrollment/{id}` y verificar CONFIRMED.
6. Probar cancelación; para rechazo crear otra matrícula REQUESTED. Probar ID inexistente (404), datos inválidos (400) y duplicados o transiciones inválidas (409).

## Operaciones

| Método | Ruta (prefijo /api/v1) |
|---|---|
| POST | /student |
| GET | /student |
| GET | /student/{id} |
| PATCH | /student/{id}/active (body: {"active":false}) |
| POST | /course |
| GET | /course |
| GET | /course/{id} |
| PUT | /course/{id} (title, description, credits, capacity) |
| POST | /enrollment |
| GET | /enrollment/{id} |
| GET | /enrollment/by-student/{id} |
| GET | /enrollment/by-course/{id} |
| POST | /enrollment/{id}/confirm |
| POST | /enrollment/{id}/reject |
| POST | /enrollment/{id}/cancel |

Se exponen los casos de uso existentes en application, no los métodos internos del dominio sin caso de uso (tutoriales, prerrequisitos y modificación del progreso). No se agrega eliminación física. Los recursos REST son records separados de los agregados. Los errores se representan con ProblemDetail. No se ejecutó la compilación en el entorno de edición (Maven no instalado).
