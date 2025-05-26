# Etapa 1: Build de Angular
FROM node:20 AS build

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --omit=dev

# Etapa 2: NGINX + configuración
FROM nginx:alpine

# Opcional: instalar herramientas
RUN apk update && apk add bash nano

# Copiar Angular compilado
COPY --from=build /app/dist/not-an-ebook/browser /usr/share/nginx/html/

# Copiar archivo nginx.conf
COPY nginx.conf /etc/nginx/nginx.conf

# Copiar el entrypoint que genera config.js
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh

#Se definen las variables de entorno
ENV DEPLOY_TYPE=docker
ENV BACKEND_HOST=localhost
ENV BACKEND_PORT=8081

# Establecer entrypoint
ENTRYPOINT ["/docker-entrypoint.sh"]

# Exponer puerto
EXPOSE 80

# Iniciar NGINX
CMD ["nginx", "-g", "daemon off;"]