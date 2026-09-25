# Variables y secretos de mcsv-auth

## Qué pasa con `.env`

`.env` es solo para desarrollo local y nunca debe subirse a GitHub ni copiarse
a la imagen. En Docker Compose, el archivo raíz `.env` sirve para sustituir
valores del YAML; para enviarlos al contenedor hay que declararlos en
`environment:` o `env_file:`. En una plataforma cloud se configuran en el
servicio y en su gestor de secretos.

Usa `.env.example` como contrato sin valores reales.

## Variables de ejecución

| Variable | Tipo | Ejemplo/uso |
|---|---|---|
| `HTTP_HOST` | Configuración | `0.0.0.0` |
| `HTTP_PORT` | Configuración | `9001` |
| `DB_USER` | Configuración | Usuario de PostgreSQL |
| `DB_URL` | Configuración | JDBC de PostgreSQL |
| `DB_PASSWORD` | Secreto | Contraseña de PostgreSQL |
| `JWT_ISSUER` | Configuración | Debe ser idéntico en todos los servicios |
| `JWT_PUBLIC_KEY_LOCATION` | Configuración | Ruta al secreto público montado |
| `JWT_PRIVATE_KEY_LOCATION` | Configuración sensible | Ruta al secreto privado montado |
| `HIBERNATE_LOG_SQL` | Configuración | `false` en producción |

`KAFKA_BOOTSTRAP`, `DEBUG_PORT` y las variables `*_API_URL` del `.env` recibido
no son consumidas por `mcsv-auth`; se eliminaron del ejemplo.

## Claves JWT

La clave privada recibida no está incluida en este paquete. Genera o importa el
par en el gestor de secretos de la plataforma y móntalo en ejecución:

```text
/run/secrets/jwt/private_key.pem   solo mcsv-auth
/run/secrets/jwt/public_key.pem    mcsv-auth, gateway y microservicios protegidos
```

Para crear un par nuevo en un directorio seguro:

```bash
openssl genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:2048 -out private_key.pem
openssl pkey -in private_key.pem -pubout -out public_key.pem
```

Si la clave privada que venía en el ZIP llegó a subirse alguna vez a un
repositorio, debes revocarla/rotarla; agregarla después a `.gitignore` no la
elimina del historial.
