#!/bin/sh

# Ruta donde estará el config.js en el contenedor
CONFIG_PATH=/usr/share/nginx/html/config.js

# Determina la URL de la API según el tipo de despliegue
if [ "$DEPLOY_TYPE" = "kubernetes" ]; then
  echo "window.API_URL = '${API_URL}';" > $CONFIG_PATH
else 
  echo "window.API_URL = 'http://${BACKEND_HOST}:${BACKEND_PORT}';" > $CONFIG_PATH
fi

# Ejecuta el comando original (nginx)
exec "$@"