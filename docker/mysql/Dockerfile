# Pull base image.
FROM mysql:latest

MAINTAINER zhenyuan.liu@duobei.com

ADD my.cnf /usr/local/mysql/

CMD ["mysqld", "--defaults-file=/usr/local/mysql/my.cnf", "--datadir=/var/lib/mysql", "--user=mysql"]