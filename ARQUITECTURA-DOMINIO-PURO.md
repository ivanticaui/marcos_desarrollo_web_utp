# Learning Platform — DDD, CQRS y eventos de dominio

## Cambios realizados

- `shared/domain/model/aggregates/AbstractDomainAggregateRoot`: reemplaza la herencia de Spring Data por una clase Java pura que registra y drena eventos pendientes.
- `shared/domain/model/events/DomainEvent`: interfaz marcadora de eventos del dominio.
- Los cuatro `record` de `learning/domain/events` implementan `DomainEvent`.
- `shared/application/events/DomainEventPublisher`: puerto tipado con `DomainEvent`.
- `shared/infraestructure/events/SpringDomainEventPublisher`: adaptador que sigue usando `ApplicationEventPublisher` de Spring.
- Los tres `CommandService` publican eventos tipados después de guardar los agregados.
- Pruebas de dominio añadidas en `DomainEventsTest`.

## Flujo

1. Un comando invoca un caso de uso en Application.
2. El agregado cambia de estado y registra un `DomainEvent` sin depender de Spring.
3. El repositorio persiste el agregado.
4. Application recupera los eventos pendientes con `pullDomainEvents()` y los entrega al puerto `DomainEventPublisher`.
5. Infrastructure publica en Spring; los listeners `AFTER_COMMIT` reaccionan tras confirmar la transacción.

## Consideraciones

- Los eventos de creación se registran antes de obtener el ID de la base de datos. Application reconstruye esos eventos con el ID persistido antes de publicarlos.
- `pullDomainEvents()` vacía la lista. Se llama tras persistir correctamente, no durante la rehidratación desde JPA.
- Los eventos de Spring son **en memoria** y no garantizan entrega frente a caída del proceso. Para entrega fiable se necesitaría Transactional Outbox.
- El control de capacidad del curso no incorpora bloqueo concurrente adicional.
- Se compiló con `javac` todo el código de Domain y Shared Domain. No se pudo ejecutar Maven porque el proyecto no incluye `.mvn/wrapper/maven-wrapper.properties` y el entorno no tiene Maven instalado.
