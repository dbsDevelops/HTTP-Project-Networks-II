FROM openjdk:11-jdk

RUN apt-get update && \
    apt-get install -y gradle git && \
    rm -rf /var/lib/apt/lists/*

RUN git clone "https://github.com/dbsDevelops/HTTP-Project-Networks-II.git"

WORKDIR /HTTP-Project-Networks-II

RUN chmod +x gradlew && \
    ./gradlew clean build && \
    ./gradlew javadoc

EXPOSE 80 443

CMD ["java", "-jar", "/HTTP-Project-Networks-II/app/build/libs/ServerDocker.jar"]