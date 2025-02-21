# Microblogging API

## Descripción
Microblogging API es una aplicación backend desarrollada con Spring Boot que permite a los usuarios registrarse, autenticarse y crear publicaciones con comentarios. Incluye autenticación, autorización con JWT y validaciones por roles.

## Tecnologías utilizadas
- **Spring Boot** (Backend principal)
- **Spring Security** (Autenticación y autorización con JWT)
- **JPA + Hibernate** (Persistencia de datos)
- **MySQL** (Base de datos relacional)
- **Dotenv** (Manejo de variables de entorno)

## Características
- Registro e inicio de sesión de usuarios con JWT.
- CRUD de publicaciones, usuarios y comentarios.
- Validaciones por roles (Usuario, Administrador).
- Manejo de excepciones personalizadas.

## Instalación y configuración
1. Clona el repositorio:
   ```bash
   git clone https://github.com/devEmersonc/Micro-blogging.git
   cd Micro-blogging
   ```
2. Crea un archivo `.env` en la raíz del proyecto con las siguientes variables de entorno:
   ```env
   DB_NAME=microblogging
   DB_USERNAME=root
   DB_PASSWORD=root
   JWT_SECRET_KEY=5a990397b91197c662cd8fd140c6864552730adf6
   ```
3. Modifica `MicrobloggingApplication.java` para cargar las variables de entorno:
   ```java
   public static void main(String[] args) {
       Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
       dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
       SpringApplication.run(MicrobloggingApplication.class, args);
   }
   ```
4. Crea la base de datos en MySQL:
   ```sql
   CREATE DATABASE microblogging;
   ```
5. Configura la base de datos en el archivo `application.properties`:
   ```properties
   spring.application.name=microblogging
   spring.datasource.url=jdbc:mysql://localhost:3306/${DB_NAME}
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.hibernate.ddl-auto=update
   ```
6. Ejecuta la aplicación con Maven:
   ```bash
   mvn spring-boot:run
   ```
## Seguridad y roles
- Los usuarios registrados pueden crear, editar y eliminar sus propias publicaciones.
- Los administradores pueden eliminar cualquier publicación o comentario.
- Se requiere un token JWT para acceder a los endpoints protegidos.

## Autor
Desarrollado por [Emerson](https://github.com/devEmersonc)
