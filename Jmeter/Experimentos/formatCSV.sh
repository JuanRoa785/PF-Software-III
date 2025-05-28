#!/bin/bash

# Archivo de salida
output="resultados.csv"

# Cabecera del CSV
echo "Label,# Samples,Average,Min,Max,Std. Dev.,Error %,Throughput,Received KB/sec,Sent KB/sec,Avg. Bytes" > "$output"

# Ordenar archivos como exp1.csv, exp2.csv, ..., exp64.csv en orden numérico
for file in $(ls exp*.csv | sort -V); do
    # Evitar agregar el archivo de resultados si ya existiera con nombre similar
    if [[ "$file" == "$output" ]]; then
        continue
    fi

    # Extraer la segunda línea (datos) y añadirla al archivo de salida
    sed -n '2p' "$file" >> "$output"
done

echo "Resumen generado en $output"

