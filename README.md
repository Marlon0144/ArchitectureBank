# 🏦 Architecture Bank - Simulador Bancario

[![Build Status](https://github.com/Marlon0144/ArchitectureBank/actions/workflows/build.yml/badge.svg)](https://github.com/Marlon0144/ArchitectureBank/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Marlon0144_ArchitectureBank&metric=alert_status)](https://sonarcloud.io/dashboard?id=Marlon0144_ArchitectureBank)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Marlon0144_ArchitectureBank&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Marlon0144_ArchitectureBank)

Proyecto desarrollado para la asignatura de **Arquitectura de Software** en la **Universidad de Antioquia (UdeA)**. Esta aplicación demuestra una arquitectura full-stack moderna, implementando un pipeline completo de Integración y Despliegue Continuo (CI/CD).

## 🚀 Despliegue en Vivo
La aplicación se encuentra desplegada y operativa en la nube de **Render**:
🔗 **[https://architecturebankdeploy.onrender.com/](https://architecturebankdeploy.onrender.com/)**

---

## 🛠️ Stack Tecnológico

* **Backend:** Java 17 con Spring Boot 4.0.3.
* **Persistencia:** JPA / Hibernate con base de datos **PostgreSQL** (Render).
* **Frontend:** Vanilla JavaScript, HTML5 y CSS3 (Servido de forma monolítica por Spring Boot).
* **Mapeo de Objetos:** MapStruct para la conversión eficiente entre Entidades y DTOs.
* **Pruebas:** JUnit 5 y base de datos **H2** en memoria para entornos de test.
* **Contenedorización:** Docker.

---

## ⚙️ Arquitectura y DevOps (CI/CD)

El proyecto implementa un flujo automatizado mediante **GitHub Actions**, garantizando la calidad del código en cada cambio:

1.  **Unit Tests:** Ejecución de pruebas unitarias automáticas con H2.
2.  **Análisis de Calidad:** Escaneo de código mediante **SonarCloud** para detectar vulnerabilidades y deuda técnica.
3.  **Build Artifact:** Generación del archivo `.jar` ejecutable mediante Maven.
4.  **Dockerización:** Creación de una imagen de contenedor basada en `eclipse-temurin:17-jre-alpine` y publicación en **Docker Hub**.
5.  **Continuous Deployment:** Render detecta la nueva imagen en Docker Hub y actualiza el servicio automáticamente, inyectando las credenciales de PostgreSQL mediante variables de entorno.

---

## 📦 Ejecución con Docker

Si deseas ejecutar la imagen oficial del proyecto localmente:

```bash
docker pull marlon0144/architecturebank:latest

docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://<HOST_DB>:<PORT>/<DB_NAME> \
  -e SPRING_DATASOURCE_USERNAME=<TU_USUARIO> \
  -e SPRING_DATASOURCE_PASSWORD=<TU_PASSWORD> \
  -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
  marlon0144/architecturebank:latest