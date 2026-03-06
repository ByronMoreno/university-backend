Actúa como un arquitecto senior de software experto en Java, Spring Boot, Spring Security, JWT y arquitectura limpia.

Estoy construyendo un proyecto educativo para entender completamente cómo funciona la autenticación con JWT en Spring Boot.

IMPORTANTE:

El proyecto Spring Boot YA está creado.
No debes crear el proyecto desde cero.

Las características actuales del proyecto son:

Java: 21
Build Tool: Gradle
Base de datos: PostgreSQL

Credenciales de base de datos:

database: logindb
username: postgres
password: (vacío)

Tu tarea es ayudarme a construir la arquitectura y el código encima de este proyecto base.

El objetivo es que el código sea claro, pedagógico y fácil de entender para aprender cómo funciona la autenticación con JWT.

El proyecto debe usar:

Spring Boot
Spring Security
JWT
Lombok
Swagger / OpenAPI
JPA / Hibernate
PostgreSQL

========================
ARQUITECTURA
========================

El proyecto debe usar arquitectura por capas con los siguientes paquetes:

entity
dto
repository
service
serviceImpl
controller
facade
facadeImpl
security
config

Explica claramente la responsabilidad de cada capa.

========================
ENTIDAD PRINCIPAL
========================

Solo habrá una entidad de negocio principal:

University

Campos:

id
name
city
country

========================
INFRAESTRUCTURA DE SEGURIDAD
========================

Debemos implementar el sistema completo de autenticación con JWT usando las siguientes entidades:

User
Role
Permission

Relaciones:

User -> ManyToMany -> Role
Role -> ManyToMany -> Permission

Campos sugeridos:

User
- id
- username
- password
- enabled

Role
- id
- name

Permission
- id
- name

========================
ARQUITECTURA UNIVERSITY
========================

La entidad University debe seguir estrictamente este flujo:

Controller -> Facade -> Service -> Repository

Es decir:

UniversityController
UniversityFacade
UniversityFacadeImpl
UniversityService
UniversityServiceImpl
UniversityRepository

Explica el rol de cada capa.

========================
SEGURIDAD JWT
========================

Implementar los siguientes componentes:

JwtTokenProvider
JwtAuthenticationFilter
SecurityConfig
CustomUserDetailsService

Explicar claramente:

cómo se genera el token
cómo se valida el token
cómo se usa en cada request
cómo Spring Security intercepta las peticiones

========================
AUTENTICACIÓN
========================

Crear endpoints de autenticación:

POST /auth/register
POST /auth/login

Login debe devolver:

JWT Token

========================
ENDPOINTS UNIVERSITY
========================

CRUD completo:

GET /universities
GET /universities/{id}
POST /universities
PUT /universities/{id}
DELETE /universities/{id}

Solo usuarios autenticados pueden acceder.

========================
AUTORIZACIÓN
========================

Usar permisos.

Ejemplo de permisos:

UNIVERSITY_READ
UNIVERSITY_CREATE
UNIVERSITY_UPDATE
UNIVERSITY_DELETE

Roles de ejemplo:

ADMIN
USER

Asignar permisos a roles.

========================
SWAGGER
========================

Integrar Swagger OpenAPI.

Swagger debe permitir:

probar endpoints protegidos
enviar JWT Token desde Swagger

========================
BASE DE DATOS
========================

Usar PostgreSQL con estas credenciales:

database: logindb
username: postgres
password: vacío

Incluir configuración en application.yml.

Cargar datos iniciales:

1 usuario ADMIN
1 rol ADMIN
permisos básicos

========================
OBJETIVO EDUCATIVO
========================

Explicar claramente:

1. estructura del proyecto
2. cómo funciona JWT
3. cómo fluye una petición autenticada
4. cómo Spring Security valida el token
5. cómo funcionan Roles y Permissions

========================
FORMATO DE RESPUESTA
========================

Responde en este orden:

1. estructura de paquetes del proyecto
2. dependencias necesarias en Gradle
3. configuración PostgreSQL
4. entidades
5. DTOs
6. repositories
7. services
8. facades
9. controllers
10. configuración de seguridad
11. implementación JWT
12. configuración Swagger
13. flujo completo de autenticación
14. ejemplo de login y uso del token
15. buenas prácticas

El código debe estar completo, limpio y listo para ejecutarse dentro del proyecto existente.

He completado la implementación de la arquitectura solicitada. He
  organizado el proyecto siguiendo los principios de Clean Architecture
  y una estructura por capas clara para facilitar su comprensión
  pedagógica.

  A continuación, presento la documentación del proyecto y la guía de
  uso.

  1. Estructura del Proyecto
  El código se ha organizado de la siguiente manera:


   * entity: Modelos JPA que representan las tablas de la base de datos
     (University, User, Role, Permission).
   * dto: Objetos para transferir datos entre el cliente y el servidor
     (Requests y Responses).
   * repository: Interfaces que extienden JpaRepository para la
     persistencia.
   * service / serviceImpl: Capa de lógica de negocio pura.
   * facade / facadeImpl: Capa que orquesta las llamadas a servicios y
     realiza el mapeo Entidad-DTO.
   * controller: Endpoints REST que exponen las funcionalidades al
     exterior.
   * security: Componentes core para la gestión de JWT y configuración
     de Spring Security.
   * config: Configuraciones generales (Swagger y carga de datos
     iniciales).

  ---


  2. ¿Cómo funciona JWT en este proyecto?
  El flujo de JSON Web Token (JWT) se divide en tres partes:


   1. Generación: Cuando el usuario hace un POST /auth/login con
      credenciales válidas, AuthService usa JwtTokenProvider para crear
      un token firmado con una clave secreta. Este token contiene el
      subject (username) y la fecha de expiración.
   2. Transmisión: El cliente recibe el token y debe incluirlo en la
      cabecera de cada petición HTTP subsiguiente: Authorization:
      Bearer <token>.
   3. Validación: JwtAuthenticationFilter intercepta cada petición,
      extrae el token, valida su firma y expiración, y carga el usuario
      en el contexto de seguridad de Spring si es válido.

  ---


  3. Flujo de una Petición Autenticada
  Cuando intentas acceder a GET /universities:


   1. Interceptación: JwtAuthenticationFilter detecta el token.
   2. Autenticación: JwtTokenProvider valida el token y extrae el
      nombre de usuario.
   3. Autorización: CustomUserDetailsService carga al usuario y sus
      permisos (como UNIVERSITY_READ).
   4. Control de Acceso: El controlador verifica mediante
      @PreAuthorize("hasAuthority('UNIVERSITY_READ')") si el usuario
      tiene el permiso necesario.
   5. Ejecución: Si todo es correcto, la petición fluye por: Controller
      -> Facade -> Service -> Repository.

  ---

  4. Ejemplo de Uso

  A. Inicio de Sesión (Login)
  Usa las credenciales de administrador creadas automáticamente por
  DataInitializer:


   * Endpoint: POST http://localhost:8080/auth/login
   * Body:
   1     {
   2       "username": "admin",
   3       "password": "admin123"
   4     }
   * Respuesta: Recibirás un JSON con el campo token.


  B. Uso del Token en Swagger
   1. Accede a: http://localhost:8080/swagger-ui.html
   2. Haz clic en el botón Authorize.
   3. Pega el token recibido (sin el prefijo "Bearer", solo el código).
   4. ¡Ya puedes probar los endpoints protegidos de /universities!

  ---


  5. Buenas Prácticas Aplicadas
   * Contraseñas Seguras: Uso de BCryptPasswordEncoder.
   * Stateless: La aplicación no guarda sesiones en memoria, todo se
     basa en el token JWT.
   * Principio de Responsabilidad Única: Separación estricta entre
     servicios (lógica) y facades (mapeo/orquestación).
   * Seguridad Basada en Permisos: Se comprueban permisos específicos
     en lugar de solo roles, lo que permite un control más granular.
   * Arquitectura Limpia: El controlador nunca habla directamente con
     el repositorio.


  El proyecto está configurado para ejecutarse con Java 21 y
  PostgreSQL. Asegúrate de tener creada la base de datos logindb antes
  de iniciar la aplicación.
