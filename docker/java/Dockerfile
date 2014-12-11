# Pull base image.
FROM java:8

MAINTAINER zhenyuan.liu@duobei.com

COPY ../../build/libs/*.war /usr/src/app/rest.war
WORKDIR /usr/src/app

CMD ["java", "-jar", "/usr/src/app/rest.war"]

