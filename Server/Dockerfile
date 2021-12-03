FROM openjdk:16-jdk-oraclelinux8
MAINTAINER "Caleb Eardley, Thomas Neyman, Garret Stouffer"
WORKDIR ./
COPY ./build/distributions/server-1.0-SNAPSHOT.zip ./server.zip
RUN microdnf install zip unzip -y
RUN unzip server.zip
EXPOSE 7000
ENTRYPOINT ./server-1.0-SNAPSHOT/bin/server

