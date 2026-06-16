# AJPD Gestión Asociación - Backend

Este es el backend del sistema de gestión de la **Asociación Juvenil Proyecto Dubini (AJPD)**. Está desarrollado utilizando **Java 21** y **Spring Boot 3.4.1**, diseñado con un enfoque modular, eficiente y con soporte para compilación nativa (**GraalVM Native Image**).

---

## 🚀 Arquitectura y Tecnologías

El backend está estructurado con las siguientes tecnologías clave:
* **Framework principal:** Spring Boot 3.4.1 (Java 21).
* **Seguridad:** Spring Security con autenticación **JWT** para peticiones API y soporte para flujo de autenticación **OAuth2 (Google Login)**.
* **Acceso a datos:** Spring Data JDBC. Se seleccionó Spring Data JDBC debido a que Hibernate/JPA tradicional presenta severas incompatibilidades y problemas con la reflexión dinámica en tiempo de ejecución de **GraalVM Native Image**. Al evitar la magia reflexiva de JPA/Hibernate y optar por JDBC con mapeos simples y directos, se garantiza la total compatibilidad con la compilación nativa AOT y se reduce drásticamente el consumo de memoria.
* **Base de datos:** PostgreSQL.
* **Almacenamiento y Archivos:**
  * **Google Drive API v3:** Para almacenar documentos y fotos de perfil asociados a los miembros de la asociación de forma organizada en carpetas de Google Drive.
  * **Supabase Storage:** Para el almacenamiento de imágenes destinadas al sitio web público (Slider, Hero, Noticias).
* **Documentación:** Springdoc OpenAPI v2 (Swagger UI integrado) para la auto-generación de especificaciones REST en formato OpenAPI 3.
* **Compilación Nativa:** GraalVM Native Build Tools para generar ejecutables binarios nativos optimizados, eliminando la sobrecarga de la JVM y reduciendo los tiempos de arranque a milisegundos.

---

## 📁 Estructura del Proyecto

El código fuente sigue el patrón MVC / Capas estándar de Spring Boot:

* `org.dubini.gestion.config`: Configuraciones de la aplicación (Google Drive, JDBC, OpenAPI, Caché, CORS, etc.), incluyendo los **GraalVM Hints** (`AppHintsRegistrar`, `JwtHintsRegistrar`, `NativeHintsConfig`) necesarios para el funcionamiento de reflexión en la compilación nativa.
* `org.dubini.gestion.security`: Filtro JWT, proveedor de tokens, servicio de seguridad y gestores de éxito/error para Google OAuth2.
* `org.dubini.gestion.controller`: Controladores REST expuestos (`AuthController`, `MiembroController`, `CargoController`, `CentroController`, `UserPermissionController`, `NewsController`, etc.).
* `org.dubini.gestion.service`: Lógica de negocio de la aplicación (`MiembroService`, `GoogleDriveService`, `AuthService`, `UserPermissionService`, etc.).
* `org.dubini.gestion.repository`: Repositorios Spring Data JDBC e implementaciones personalizadas para consultas y búsquedas complejas.
* `org.dubini.gestion.model`: Entidades del modelo de datos de base de datos.
* `org.dubini.gestion.dto`: DTOs (Data Transfer Objects) para peticiones y respuestas estructuradas.
* `org.dubini.gestion.validation`: Validadores personalizados de negocio (como el validador de NIF/CIF).

---

## ⚙️ Configuración y Variables de Entorno

El backend utiliza un archivo `.env` en el directorio raíz para cargar variables de entorno locales en tiempo de desarrollo.

### Variables de Entorno Requeridas:

| Variable | Descripción | Valor por Defecto |
| :--- | :--- | :--- |
| `DB_URL` | URL de conexión JDBC a la base de datos PostgreSQL | *Requerido* |
| `DB_USERNAME` | Usuario de PostgreSQL | *Requerido* |
| `DB_PASSWORD` | Contraseña de PostgreSQL | *Requerido* |
| `JWT_SECRET` | Clave secreta para la firma y verificación de tokens JWT | *Requerido (Mínimo 256 bits)* |
| `GOOGLE_CLIENT_ID` | Client ID de Google Console para el login OAuth2 | `mock-client-id` |
| `GOOGLE_SECRET` | Client Secret de Google Console para OAuth2 | `mock-secret` |
| `FRONTEND_URL` | URL base del frontend para redirección tras OAuth2 | `http://localhost:4200` |
| `FRONTEND_API_URL` | URL base del frontend para CORS | `http://localhost:8081` |
| `SUPABASE_API_URL` | URL de la API de Supabase Storage | `https://mock.supabase.co` |
| `SUPABASE_NAME` | Nombre del Bucket de Supabase Storage | `mock-bucket` |
| `SUPABASE_KEY` | API Key / Service Role Key de Supabase | `mock-key` |
| `GOOGLE_DRIVE_PARENT_FOLDER_ID` | ID de la carpeta raíz de Google Drive para los documentos | `mock-parent-folder-id` |
| `GOOGLE_DRIVE_CREDENTIALS_JSON` | Contenido JSON de las credenciales de la Cuenta de Servicio de Google | *Requerido para Drive* |

---

## 🛠️ Ejecución y Construcción

### Prerrequisitos
* **JDK 21** instalado y configurado en la variable `JAVA_HOME`.
* **Maven 3.9+** instalado.
* Base de datos PostgreSQL activa.

### Ejecución en Modo Desarrollo
Para ejecutar la aplicación localmente con recarga automática y lectura de `.env`:
```bash
mvn spring-boot:run
```

### Ejecutar Pruebas Unitarias e Integración
```bash
mvn test
```

### Construcción del Jar Tradicional (JVM)
```bash
mvn clean package
```
Esto generará el archivo JAR ejecutable en `target/gestion-1.0.0-SNAPSHOT.jar`.

### Construcción Nativa con GraalVM (Native Image)
Para compilar la aplicación a un ejecutable nativo optimizado:
1. Asegúrate de tener GraalVM JDK 21 configurado y el comando `native-image` disponible.
2. Ejecuta el perfil nativo de Maven:
```bash
mvn clean package -Pnative
```
El archivo binario ejecutable e independiente se generará en `target/gestion`. Puedes ejecutarlo directamente con:
```bash
./target/gestion
```

---

## 📖 API Documentation (Swagger/OpenAPI)

Cuando la aplicación está en ejecución, se puede acceder a la documentación interactiva de la API a través de:

* **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
* **OpenAPI Spec (JSON):** `http://localhost:8080/v3/api-docs`

---

## 🗄️ Esquema de Base de Datos

Las tablas de base de datos se inicializan automáticamente si no existen a través del archivo [schema.sql](file:///home/franchu/Escritorio/AJPD-GestionMiembros/back/src/main/resources/schema.sql):

1. **`centros`**: Almacena los diferentes centros de la asociación.
2. **`cargos`**: Almacena los cargos y puestos de la organización.
3. **`miembros`**: Datos personales de los miembros (NIF/CIF, Teléfono, Correo, Estado de Alta/Baja, Pronombres, etc.).
4. **`historial_cargos`**: Historial cronológico de cargos ocupados por cada miembro (Timeline).
5. **`news`**: Publicaciones del sitio web (Título, Contenido en formato EditorJS, Fecha).
6. **`user_permissions`**: Permisos de gestión de usuarios basados en su correo de Google (`can_manage_permissions`, `can_manage_organization`, `can_manage_web`, `can_manage_finances`).
