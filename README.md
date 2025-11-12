# Documentación de la prueba Técnica
En este archivo estan los pasos necesarios para poder levantar el API y poder hacer consultas.

## Paso 1
Clonar el repositorio, una vez clonado el repositorio abre una terminal en esa carpeta.

## Paso 2
Se debe de tener instalada la herrmaienta Docker.
Ejeuta el sigueinte comando para levantar el API:
      docker-compose up --build

## Paso 3
Esperamos unos minutos para que se levante el contendor, este contenedor ya tiene la base de datos, y por defecto se crea un usuario, solo es cuestión de ejecutar el comando.

El API estará lista una vez que en la terminal muestre el mensaje "Usuario administrador ya existe"

## Paso 4
Abre el navegador de tu prefrencia e ingresa a la siguiente url:
http://localhost:3005/swagger-ui/index.html#/

Veras la interfaz de swagger con los endpoints del API.

## Paso 5 
Ve al endpoint /api/profile/login   e ingresa las siguientes claves

usuario: admin

contraseña: admin123

Y como respuesta obtendras los tokens para acceder a los demas endpoints, en Swagger hasta arriba de lado izquierdo esta una opción que se llama "Authorize", da clic en esa opción y solo pega el accesToken y ya podras hacer las peticiones sin problema.

## Paso 6
Para parar el api, ve a tu terminal donde se esten los logs y presiona ctrl+C y en seguida el contendor se detiene.

