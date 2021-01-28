FROM mcr.microsoft.com/dotnet/sdk:3.1-alpine
RUN apk add --update python3
RUN apk add --update npm
RUN npm i --global mocha
RUN apk add --update openjdk8

ENV JAVA_HOME="/usr/lib/jvm/java-1.8-openjdk"
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN mkdir /tempdirs
RUN dotnet new xunit -o /tempdirs/testproject
RUN rm /tempdirs/testproject/UnitTest1.cs
VOLUME /tmp
ADD junitlib/* /tmp/junitlib/
ADD target/gatex-lang.jar gatex-lang.jar
ENTRYPOINT ["java","-jar","gatex-lang.jar"]