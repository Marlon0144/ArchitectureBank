# Reporte de Auditoría: Proyecto Simulador Bancario (ArchitectureBank)

Como Arquitecto de Software y Analista DevOps, he evaluado el estado actual del repositorio de cara a la implementación de un pipeline de CI/CD (GitHub Actions, SonarCloud, Docker). 

A continuación, presento los hallazgos técnicos estructurados:

## 1. Arquitectura del Repositorio
Se trata de un formato **Monorepo**. El código del Frontend y del Backend residen en el mismo repositorio. Si bien están alojados juntos, no están acoplados a nivel de sistema de construcción (el frontend no es servido por la carpeta `src/main/resources/static` del backend, sino que se ubica en su propia carpeta raíz `/frontend`).

## 2. Stack Tecnológico Backend
- **Lenguaje:** Java 17
- **Framework:** Spring Boot (v4.0.3), incluyendo módulos para Data JPA, WebMVC y Validation.
- **Base de Datos Configurada:** MySQL (`mysql-connector-j`).
- **Herramienta de Construcción:** Maven (uso de `pom.xml` y wrappers de mvnw).
- **Puerto de Exposición (Default):** El archivo `application.properties` no especifica la directiva `server.port`, por lo cual por defecto el servidor embebido (generalmente Tomcat en Spring Boot Web) de backend correrá en el **puerto 8080**.

## 3. Stack Tecnológico Frontend
- **Framework / Librerías:** Vanilla JS, CSS y HTML (sin uso de React, Angular o Vue).
- **Gestor de Paquetes:** Ninguno (no existe `package.json` ni archivos como `npm`, `yarn` o `pnpm`).
- **Puerto de Desarrollo:** Al no estar gestionado por Node.js / Vite / Webpack, no tiene un puerto predeterminado intrínseco. Típicamente los desarrolladores deberán usar un Live Server genérico (ej. puerto 5500) o un servidor HTTP estático simple para probarlo.

## 4. Estado de Pruebas Automatizadas
- **Backend:** Se cuenta con una capa de pruebas generada, alojada en `/src/test/java/...`. Se identifican dependencias de prueba configuradas (`spring-boot-starter-webmvc-test`, `spring-boot-starter-data-jpa-test`) apoyadas sobre el ecosistema standard de Spring Boot el cual utiliza **JUnit 5 / Mockito**. Al momento se observa la presencia de un solo test básico (`ArchitectureBankApplicationTests.java`), por lo cual carece de cobertura de unidades/negocio real aún.
- **Frontend:** **Inexistente**. No se detectan pruebas unitarias (ej. Jest, Vitest), ni de e2e (Cypress, Playwright), lo cual deberá ser considerado o suplido al implementar el pipeline en GitHub Actions.

## 5. Estructura de Carpetas 
A continuación se resume la taxonomía principal actual:

```text
/ (Raíz del Repositorio)
├── frontend/                          # Capa Frontend estática (Vanilla)
│   ├── css/                           # Archivos de estilo (.css)
│   ├── js/                            # Lógica del cliente y consumo de APIs (.js)
│   └── index.html                     # Punto de entrada Web
├── src/                               # Código fuente del Backend (Java)
│   ├── main/
│   │   ├── java/.../architectureBank/
│   │   │   ├── controller/            # Controladores REST API
│   │   │   ├── DTO/                   # Objetos de Transferencia de Datos
│   │   │   ├── entity/                # Entidades de BD (Modelo JPA)
│   │   │   ├── mapper/                # Mappers de entidad a DTO y viceversa
│   │   │   ├── repository/            # Acceso a base de datos (Spring Data JPA)
│   │   │   ├── service/               # Lógica de negocio
│   │   │   └── ArchitectureBankApplication.java
│   │   └── resources/
│   │       └── application.properties # Archivo principal de configuración Backend
│   └── test/                          # Directorio de pruebas Backend (JUnit)
├── pom.xml                            # Archivo descriptor y configurador de Maven
└── README.md                          # Archivo base de documentación
```

---
**Recomendaciones Inmediatas para CI/CD:**
1. Crear un `Dockerfile` multietapa en el root (o dos separados, uno para el back y un servidor `nginx` para el front).
2. Es obligatorio crear tests significativos en el backend para que el paso del Workflow y la integración de SonarCloud valgan la pena en el despliegue funcional.
3. Se recomienda considerar en el futuro `package.json` en el Frontend utilizando Vite o Webpack para poder implementar linters (ESLint), minificación y pruebas automatizadas mediante `Jest`.