#--- BUILDING ---#

#Utilizamos la imagen de maven para crear el artefacto desplegable (jar) del proyecto
FROM maven:3.9.6-eclipse-temurin-17 AS maven

#Copiamos el codigo fuente dentro de la imagen
COPY ./src /usr/local/app/src

#Copiamos el pom dentro de la imagen y en la raiz del directorio de la app
COPY ./pom.xml /usr/local/app

#Definimos el directorio de la aplicaciøn
WORKDIR /usr/local/app

#Ejecutamos los comandos clean y package propios de maven para generar el jar,
#saltamos los test para que no de error
RUN mvn clean package -DskipTests

#--- DESPLIEGUE ---#

#Partimos ahora de una imagen de java
FROM eclipse-temurin:17

#Se definen las variables de entorno
ENV DB_HOST=localhost
ENV DB_PORT=5432
ENV DB_USER=postgres
ENV DB_PASS=adminPostgres
ENV CLOUD_NAME=dqqz93inr
ENV CLOUD_API_KEY=651365279725395
ENV CLOUD_API_SECRET=VZUT8hrqCiZcLQ3NUyo2Mg8vcqc

# Copia el jar ejecutable de la imagen auxiliar alias mavn
COPY  --from=maven /usr/local/app/target/Biblioteca-0.0.1-SNAPSHOT.jar /usr/share/biblioteca.jar

# Lanza el ejecutable usando java
CMD ["java", "-jar", "/usr/share/biblioteca.jar"]

# Expone el puerto 8081
EXPOSE 8081