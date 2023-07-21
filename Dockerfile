#
# Docker: api-Hyperxpress 
# Autor: Athirson
#
FROM openjdk:8u171-slim
MAINTAINER Athirson <athirson.candido@bandtec.com.br>

#################################
# Configuração
#################################

WORKDIR /hyperxpress-project/
COPY target/*.jar /hyperxpress-project/app.jar
EXPOSE 80
EXPOSE 25
EXPOSE 587
ENTRYPOINT ["java","-jar","app.jar"]


