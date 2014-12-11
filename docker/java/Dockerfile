# Pull base image.
FROM java:8

MAINTAINER zhenyuan.liu@duobei.com

COPY ./*.war /usr/src/app/rest.war
WORKDIR /usr/src/app

CMD ["java", "-jar", "-Dspring.profiles.active=dev", "/usr/src/app/rest.war"]

