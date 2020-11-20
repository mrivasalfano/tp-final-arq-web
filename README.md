# TP Final de Arquitecturas WEB: Agenda de viajes 
## TUDAI - 2020

La solución empezó con la identifición de las entidades. La siguiente imágen muestra las tablas finales.

#### Diagrama de entidades y relaciones
Identificamos un plan distinto a los "comunes" que sería el plan vuelo. Este tiene datos adicionales por lo tanto es una herencia de Plan.

![DER](/tp-final-arq-web/images/DER.png)

#### Vista de módulos
A nivel de módulos se cuenta con 5 paquetes para el backend

![Vista de módulos](/tp-final-arq-web/images/vista_modulos.png)

- Frontend: Interfaz WEB basada en HTML, CSS, Bootstrap y JavaScript. Permite logearse y realizar peticiones a la API.
- Controllers: Controladores de la API REST. Se encargan de recibir las peticiones de los clientes y procesarlas. Ejemplo: Cargar un viaje, listarlos, agregar planes, etc
- Repositories: Repositorios encargados de realizar consultas a la base de datos MySQL. Ejemplo: Listar planes por rango de fechas, obtener planes realizados, etc
- Entities: Entidades JPA que modelan las tablas.
- Config: Configuraciones de Swagger, JWT y filtro de rutas con autorización.
- Utils: Contiene POJO's para convertir fechas, almacenar reportes e inicializar datos de prueba.

#### Vista de C&C y despliegue
El sistema en funcionamienta corre de la siguiente manera

![Vista de C&C y despliegue](/tp-final-arq-web/images/vista_cc_despliegue.png)

1- Un cliente realiza una petición **REST** al servidor mediante un **ENDPOINT**  
2- El controlador correspondiente la recibe y pasa por un filtro JWT mediante un token.  
3- En caso de ser válido el token, el controlador usa uno o varios repositorios mediante métodos para acceder a la base de datos.  
4- Por último, termina de procesar y responde al cliente con un **estado** y el **JSON** correspondiente.

En cuanto al despliegue, todo el backend se encuentra en un mismo nodo, es decir, un monolito. Los clientes son independientes y cada uno está en su propio nodo. LA base de datos es MySQL y existe una sola instancia.

Por último se mostrará un diagrama que ejemplifica una alta de viaje mediante un archivo **.json** 
#### Diagrama de secuencia
![Vista de C&C y despliegue](/tp-final-arq-web/images/vista_cc_despliegue.png)
