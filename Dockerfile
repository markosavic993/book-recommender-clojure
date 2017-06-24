FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/book-recommender.jar /book-recommender/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/book-recommender/app.jar"]
