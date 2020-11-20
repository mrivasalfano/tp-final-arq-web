# TP Final de Arquitecturas WEB: Agenda de viajes 
## TUDAI - 2020

La solución empezó con la identifición de las entidades. El siguiente DER demuestra las tablas finales.

![DER](/tp-final-arq-web/images/DER.png)

A nivel de módulos se cuenta con 5 paquetes para el backend

![DER](/tp-final-arq-web/images/vista_modulos.png)

- Frontend: Interfaz WEB basada en HTML, CSS, Bootstrap y JavaScript. Permite logearse y realizar peticiones a la API.
- Controllers: Controladores de la API REST. Se encargan de recibir las peticiones de los clientes y procesarlas. Ejemplo: Cargar un viaje, listarlos, agregar planes, etc
- Repositories: Repositorios encargados de realizar consultas a la base de datos MySQL. Ejemplo: Listar planes por rango de fechas, obtener planes realizados, etc
- Entities: Entidades JPA que modelan las tablas.
- Config: Configuraciones de Swagger, JWT y filtro de rutas con autorización.
- Utils: Contiene POJO's para convertir fechas, almacenar reportes e inicializar datos de prueba.  
