# **Proyecto Final Ingeniería Software III**

### 👥 Integrantes
*   **Kevin Dannie Guzmán Duran**
*   **Juan Diego Roa Porras**

### 📄 Recursos del Análisis

- 📘 [Notebook para el Análisis (Jupyter)](https://github.com/JuanRoa785/PF-Software-III/blob/main/Analisis_Proyecto_Final.ipynb)
- 📄 [Notebook exportado como PDF](https://github.com/JuanRoa785/PF-Software-III/blob/main/Analisis_Proyecto_Final.pdf)

---

# 📌 **Introducción**

Este proyecto se enfocó en el análisis empírico del comportamiento de una aplicación al ser desplegada en distintos entornos. Se utilizó como caso de estudio el proyecto final de la asignatura Entornos de Programación, evaluando su rendimiento tanto en Docker como en Kubernetes.

Las pruebas se llevaron a cabo en equipos con recursos limitados, proporcionados por la universidad, y también en un entorno con mayor capacidad de cómputo. Se variaron factores como el número de réplicas y la cantidad de nodos activos dentro del clúster de Kubernetes, con el fin de observar cómo estos elementos afectan el desempeño de la aplicación.

Aunque se trata de un análisis empírico, se procuró mantener condiciones consistentes en cada experimento para garantizar resultados comparables. Las métricas obtenidas sirvieron como base para las conclusiones que se presentan más adelante.

# 🎯 **Objetivos**

El principal objetivo de este proyecto es aplicar de manera práctica los conocimientos adquiridos a lo largo del curso, configurando y utilizando activamente el clúster de máquinas virtuales provisto al inicio del semestre. Se buscó desplegar aplicaciones de forma eficiente y sencilla utilizando tanto Docker como Kubernetes.

Adicionalmente, con el propósito de fortalecer la capacidad analítica y mejorar la toma de decisiones técnicas, se llevaron a cabo pruebas de carga bajo diferentes configuraciones de la aplicación. Estas pruebas permitieron comparar empíricamente las alternativas de despliegue disponibles, aprovechando los recursos limitados del entorno, y así fundamentar con datos sólidos cuál opción resulta más conveniente.

# 💻 **Not An Ebook**

<p align="justify">
<b>Not An Ebook</b> es la aplicación desarrollada como proyecto final para la asignatura de Entornos de Programación en la Universidad Industrial de Santander. Su propósito es establecer los fundamentos de un sistema de comercio electrónico enfocado en la venta de libros físicos. La plataforma permite a los usuarios registrarse, consultar información clave de los libros, como sinopsis, autor, género literario, número de páginas, entre otros, y simular una compra ingresando su dirección.

Asimismo, los administradores del emprendimiento pueden acceder a reportes de ventas utilizando filtros simples e intuitivos. Con el objetivo de escalar la aplicación, se contempla la implementación de funcionalidades avanzadas de gestión de inventario, permitiendo que el sistema evolucione hacia una solución de información en tiempo real que proporcione a los administradores datos relevantes sobre la rentabilidad del negocio.
</p>

## **Tecnologías Utilizadas**

*   **Backend:** Spring Boot
*   **Frontend:** Angular
*   **Database:** PostgreSQL
*   **Gestión de Imagenes:** Cloudinary

## **Software en Ejecución**

<p align="center">
  <img src="https://raw.githubusercontent.com/JuanRoa785/PF-Software-III/main/Assets/notanebook.gif" width="800"/>
</p>

# ⚙️ **Configuración de Despliegue**

La configuración del despliegue se basó en el uso de **variables de entorno**, lo que permitió adaptar la ejecución del software a diferentes entornos modificando únicamente sus valores. Gracias a este enfoque, la aplicación pudo ejecutarse y ser accesible tanto de forma local como en un cluster de máquinas virtuales sin la necesidad de realizar cambios en el código fuente.

## **Database (PostgreSQL)**
La configuración de la base de datos se mantuvo constante en todos los despliegues, utilizando las siguientes variables de entorno:

```dockerfile
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=adminPostgres
ENV POSTGRES_DB=not_an_ebook
```
Es importante destacar que el valor de `POSTGRES_DB` no debe ser modificado, ya que el backend tiene este nombre de base de datos codificado de forma fija. En caso de cambiarlo, será necesario ajustar el archivo `application.properties` del backend, reconstruir la imagen con `docker build` y, para mantener la coherencia en los despliegues, actualizar la imagen correspondiente en **Docker Hub**.

Aunque el servicio de PostgreSQL expone el puerto estándar **5432**, este fue mapeado al puerto **5435** en Docker y al **30543** en Kubernetes para evitar conflictos y facilitar el acceso según el entorno de ejecución.

## **Backend (Spring Boot)**

A continuación se presentan las variables de entorno definidas en el archivo `application.properties` del backend:

```java
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/not_an_ebook
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.jpa.show-sql=true
server.port = 8081

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

cloudinary.cloud-name=${CLOUD_NAME}
cloudinary.api-key=${CLOUD_API_KEY}
cloudinary.api-secret=${CLOUD_API_SECRET}
```

Estas variables permiten establecer la conexión con la base de datos PostgreSQL y habilitar el servicio de Cloudinary para la gestión de las portadas de los libros. Además, se configura el puerto del servidor y se definen límites para la carga de archivos, lo que facilita una configuración flexible y adaptable a diferentes entornos de despliegue.

## **Frontend (Angular -> Nginx)**

El despliegue de la aplicación frontend desarrollada en Angular presentó ciertos desafíos adicionales. Uno de los principales fue la necesidad de definir una única variable de entorno: la URL base para realizar peticiones al backend. Esta configuración se complica especialmente en entornos distribuidos como Kubernetes, donde no se puede predecir en qué nodo estarán desplegadas las réplicas del backend en un momento dado.

Para resolver esta problemática, se configuró y utilizó un ingress controller, el cual actúa como proxy inverso para enrutar las peticiones entrantes. La configuración específica se definió en el archivo ingress-config.yaml.

Además, para simplificar el proceso de creación de la imagen del frontend, se utilizó el comando:

```shell
npm run build --omit=dev
```

Este comando compila la aplicación en su versión de producción. Durante el proceso de construcción de la imagen Docker, el archivo `index.html` está diseñado para consumir un archivo `config.js`, el cual se genera dinámicamente mediante el script `docker-entrypoint.sh.` Este script sobrescribe `config.js` con los valores apropiados según la estrategia de despliegue. Su lógica principal es la siguiente:

```shell
if [ "$DEPLOY_TYPE" = "kubernetes" ]; then
  echo "window.API_URL = '${API_URL}';" > $CONFIG_PATH
else
  echo "window.API_URL = 'http://${BACKEND_HOST}:${BACKEND_PORT}';" > $CONFIG_PATH
fi
```
En esta lógica se observa que, para despliegues con Docker, es necesario especificar el host y el puerto del backend (por ejemplo: `10.6.101.100:8081`). En cambio, para Kubernetes, basta con usar el alias definido en el Ingress (en este caso: `backend.local`), ya que el enrutamiento es gestionado automáticamente dentro del clúster.

## **Docker Hub**
Una vez verificado que las imágenes funcionaban correctamente tanto en entornos Docker como en Kubernetes, se realizó el push final de estas al repositorio de Docker Hub, asegurando así su disponibilidad para los diferentes escenarios de despliegue.

<p align="center">
  <img src="https://raw.githubusercontent.com/JuanRoa785/PF-Software-III/main/Assets/dockerhub.png" width="700"/>
</p>

Cabe resaltar que, debido a la similitud estructural entre esta aplicación y la desplegada en el Reto 2, fue posible reutilizar gran parte de los `Dockerfile` y archivos `.yaml` utilizados anteriormente, lo cual facilitó y agilizó el proceso de configuración y despliegue.

---

# 🚀 **Despliegue de Not An Ebook**

```shell
#Clonar el repositorio
git clone https://github.com/JuanRoa785/PF-Software-III.git

#Ubicarse en el directorio del proyecto
cd PF-Software-III
```

## **Docker**
En caso de no ejecutar la aplicación de forma completamente local, es necesario actualizar en el archivo `docker-compose.yml` la variable de entorno `BACKEND_HOST`, asignándole la IP de la máquina virtual donde se desplegarán los contenedores.

Para levantar la aplicación, ejecuta los siguientes comandos:

```shell
docker compose up -d
docker ps # Verifica que los tres contenedores estén en ejecución
```

Una vez desplegado, los servicios estarán disponibles en las siguientes direcciones:

*   **Fronted:** `http://IP_MAQUINA:4200`
*   **Backend:** `http://IP_MAQUINA:8081`
*   **Database:** `IP_MAQUINA:30543`

Para detener la aplicación, simplemente ejecuta:
```shell
docker compose down
```

---

## **Kubernetes**
Para desplegar la aplicación en Kubernetes, primero asegúrate de estar ubicado en la carpeta `k8s/configs` y habilita el complemento `ingress` con los siguientes comandos:

```shell
microk8s kubectl enable ingress
cd k8s/configs
```
> 💡 Si tu clúster cuenta con varios nodos y se crea una réplica del controlador `ingress` por nodo, es recomendable editar su deployment para limitarlo a una sola réplica por clúster, evitando conflictos

Luego, aplica los `ConfigMaps` y `Secrets` necesarios para preparar tanto el despliegue como el Ingress:
```shell
microk8s kubectl apply -f .
```
### **Configuración de alias en la máquina local**
Para acceder a la aplicación o realizar pruebas desde la máquina local (por ejemplo, con JMeter o mediante navegador), debes crear un alias llamado `backend.local` apuntando a la IP del nodo donde se está ejecutando `ingress`. Para identificar la IP:
```shell
microk8s kubectl describe pods -n ingress
```

Busca una sección similar a esta en el resultado:
```shell
Node: roa-pc/192.168.1.12
```

Luego, edita el archivo de hosts:
```shell
sudo nano /etc/hosts
```

Y añade la siguiente línea:
```lua
192.168.1.12    backend.local
```
### **Despliegue de los servicios**
Finalmente, regresa a la carpeta principal (k8s/) y ejecuta los archivos de deployment y servicios:
```shell
cd ../
microk8s kubectl apply -f .
```

Una vez desplegados, los servicios estarán disponibles en los siguientes puertos:

*   **Frontend:** `http://IP_MAQUINA:30420`
*   **Backend:** `http://IP_MAQUINA:30081`
*   **Database:** `IP_MAQUINA:30543`

---

# 📊 **Metodología de Generación de Carga con JMeter**

Antes de abordar la prueba de estrés realizada con JMeter, es importante describir el estado inicial de la base de datos. Independientemente de la herramienta de despliegue utilizada, la aplicación se inicializa con los siguientes datos por defecto:

*   **75** Productos (Libros)
*   **100** Usuarios
*   **100** Direcciones
*   **1500** Ventas
*   **3700** Detalles de Ventas

<p align="center">
  <img src="https://raw.githubusercontent.com/JuanRoa785/PF-Software-III/main/Assets/database.gif" width="800"/>
</p>

---

El endpoint seleccionado para la prueba fue el del reporte individual, ya que es, sin duda, el que mayor carga genera tanto para el backend como para la base de datos. Este endpoint recibe los siguientes parámetros:

*  **cliente**: nombre del cliente, utilizado para filtrar las ventas en las que haya participado como comprador.
*  **fechaInferior**: límite inferior del rango de fechas para mostrar las ventas.
*  **fechaInferior**: límite superior del rango de fechas para mostrar las ventas.
*  **totalDesc**:  parámetro que permite ordenar las ventas según su total.

En la interfaz del frontend, este formulario se visualiza de la siguiente manera:
<p align="center">
  <img src="https://raw.githubusercontent.com/JuanRoa785/PF-Software-III/main/Assets/reporte.png" width="800"/>
</p>

Y su configuración equivalente en JMeter se muestra así:
<p align="center">
  <img src="https://raw.githubusercontent.com/JuanRoa785/PF-Software-III/main/Assets/jmeter.png" width="800"/>
</p>

Para estas pruebas, se dejó el parámetro cliente como cadena vacía, se fijó el **límite inferior** en el 1 de enero de 2020 y el **límite superior** en la fecha actual. De este modo, se garantiza que se consulten las **1500** ventas registradas, asegurando así que la carga generada por el endpoint sea significativa.

---

### Exportar el Notebook como PDF

- 📤 [Cómo exportar notebooks como PDF (YouTube)](https://youtu.be/-Ti9Mm21uVc)
