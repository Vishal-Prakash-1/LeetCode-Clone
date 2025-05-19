FROM openjdk:8-jdk-slim
WORKDIR /app
CMD javac Main.java && timeout 2s java Main