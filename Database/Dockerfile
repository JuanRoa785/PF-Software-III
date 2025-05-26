FROM postgres:17

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=adminPostgres
ENV POSTGRES_DB=not_an_ebook

COPY poblarDB.sql /docker-entrypoint-initdb.d/

EXPOSE 5432