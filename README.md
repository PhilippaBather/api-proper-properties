# properpropertiesapi

## Un API para una inmobiliaria ficticia

## Actividad de Aprendizaje para la asignatura Acceso a Datos para el FP Grado superior en Desarrollo de Aplicaciones Multiplataforma (DAM).


### Descripción:
#### - El API contiene endpoints para registrar (Post), obtener (Get) y actualizacar (Put, Delete) información sobre usuarios generales ('clients') y propietarios ('proprietors').
#### - También, hay endpoints para para registrar (Post), obtener (Get) y actualizacar (Put, Delete) información sobre inmuebles para alquiler y para vender, y sus direcciones.
#### - El API contiene endpoints tanto seguros (utilizando JWT) como no seguros: los endpoints seguros para registrar y 'login' a propietario, y para manejar los muebles (operaciones de Post, Put, Delete); los endpoints inseguros son de Get para obtener una lista de muebles para alquiler y vender para el usuario general (operaciones de Get).

### Ejecución: para usar con Maven, ejecutar mvn spring-boot:run en la línea de comando.

### ------------------------------------------------------------------------------------------------------

## An API for a fictitious estate agents

## Coursework for the module Access to Data for the FP Grado Superior in The Design of Multiplatform Applications (DAM).

### Description:
#### - The API contains endpoints to register (Post), obtain (Get), and update (Put, Delete) information about general users (clients) and property owners (proprietors).
#### - It also contains endpoints to register (Post), obtain (Get), and update (Put, Delete) information about rental properties and sale properties, and their addresses.
#### - The API contains both secured (using JWT) and unsecured endpoints: secured endpoints to register and login a proprietor, so that they can view and manage their properties (Post, Put, Delete operations); unsecured endpoints to obtain a list of properties for a general user to view (Get operations).

### To run: for use with Maven, execute mvn spring-boot:run in the command line.