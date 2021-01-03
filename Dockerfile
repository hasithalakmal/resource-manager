FROM ubuntu:18.04
MAINTAINER hasithag;

# Install prerequisites
RUN apt-get update && apt-get install --no-install-recommends -y \
software-properties-common curl apache2 supervisor jq && apt-get update

#Create folder to save files
RUN mkdir /var/www/html/smile24es/ -p
RUN mkdir /opt/smile24es/ -p
RUN mkdir /var/log/resource-app/ -p

# Install java11
RUN add-apt-repository ppa:openjdk-r/ppa && apt-get update && apt install --no-install-recommends -y openjdk-11-jdk
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64

COPY ./supervisord.conf /etc/supervisor/conf.d/supervisord.conf
COPY ./target/resource-manager-0.0.1-SNAPSHOT.jar /opt/smile24es/

EXPOSE 9090
EXPOSE 80

CMD ["/usr/bin/supervisord"]