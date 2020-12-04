# Exchanges
Demo for Exchange System

**Endpoint 1: POST /exchange** 

* Registra una postura en la base de datos

**Endpoint 2: GET /exchange/realtime**

* Se puede obtener en tiempo real las posturas que se van ingresando

**Endpoint 3: GET /exchange/{profile:[LOW, MEDIUM, HIGH]}?date={date}**

* Se obtiene las posturas basados en el perfil y la fecha

1. LOW:  obtener la postura más alta de la fecha
2. MEDIUM: obtener el promedio de posturas de la fecha
3. HIGH: obtener la postura más baja de la fecha

**Endpoint 4: GET /cache/exchange/{profile:[LOW, MEDIUM, HIGH]}?date={date}**

* Se utilizo redis como cache para almacenar la consulta
* Para usarlo en la aplicacion se debe previamente tener instalado docker
* Luego ejecutar "docker compose up redis"

**Contrato swagger O openapi**

* localhost:8082/v2/api-docs

**Test**

* No se llego a realizar


