# Create builder stage for build application.
FROM  maven:3.6.3-adoptopenjdk-14 as builder
WORKDIR /app
COPY . /app
# Build maven application
RUN mvn  -DskipTests=true clean package
RUN mv target/*.jar app.jar
# Reduce image size
FROM openjdk:17-buster
EXPOSE 8080
WORKDIR /app
COPY --from=builder /app/app.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
#docker build -t maxiplux/livemarket.io.eprocurment .
#docker tag    d49c6ba9aadc  maxiplux/livemarket.io.eprocurment:0.1.100
#docker tag  39d440f82330 maxiplux/livemarket.business.b2bcart:kuerbernetes
#docker push maxiplux/livemarket.business.b2bcart:kuerbernetes
#docker push maxiplux/livemarket.io.eprocurment:0.1.100
