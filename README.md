Test task for the RedShark team
============

## Implemented functionality:

* Authorization secured by JWT
* Create user with roles: "ROLE_ADMIN" or "ROLE_USER" (Run as "ROLE_ADMIN")
* Update user roles: Change a "ROLE_USER" as "ROLE_ADMIN" or "ROLE_ADMIN" as "ROLE_USER" (Run as "ROLE_ADMIN")
* Getting user list (Run as "ROLE_ADMIN")
* Create user devices, Update device name, Getting user devices
* Create, Update, Relocate, Search files on user device
* Create and Getting user tasks
* Swagger documentation : http://localhost:8080/swagger-ui.html
* Github Actions for build and publish docker container

Default username: root  
Default password: toor
 
## <a id="anchor"></a> Running the application locally by JVM

The application can be started locally using the following command:

~~~
$ ./gradlew clean build
$ java -jar build/libs/*.jar
~~~

## Running the application on Docker

The application can be started on Docker using the following command:

~~~
$ docker pull slaaavyn/redshark:latest
$ docker run \
    --name=<container_name> \
    --port=8080:8080 \\
~~~