# CI nativo de mcsv-auth

El workflow `.github/workflows/native-ci.yml` ejecuta pruebas JVM en cada push
y pull request. En la rama predeterminada, en ejecución manual y en tags `v*`
también genera un ejecutable nativo Linux AMD64. Los tags publican una imagen
en GHCR y adjuntan el binario a un GitHub Release.

## Dependencia privada de Maven

Este proyecto consume `com.think:x-artefacto-pagination` desde GitHub Packages.
La opción preferida es conceder al repositorio de `mcsv-auth` acceso de lectura
al paquete. Así el workflow utiliza `GITHUB_TOKEN` automáticamente.

Si el paquete no puede conceder ese acceso, crea dos repository secrets:

```text
MAVEN_PACKAGE_USERNAME=<usuario-de-github>
MAVEN_PACKAGE_TOKEN=<pat-classic-con-read:packages>
```

Estos secretos son únicamente para descargar la dependencia durante el build;
no son variables de ejecución de la aplicación.

## Compilación local

```bash
./mvnw clean test
./mvnw clean package -Dnative -DskipTests -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t mcsv-auth:native .
```

La compilación nativa no necesita las credenciales de PostgreSQL ni la clave
privada JWT. Esos valores se inyectan al ejecutar el contenedor.
