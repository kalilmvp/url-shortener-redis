### URL "*shortener*"  using Spring Boot and REDIS

You need to have a local REDIS instance running. I suggest using the following
docker image

```
docker pull redis
``` 

And then run it 

```
docker run --name some-redis -d redis
```

The default port is 6379 and will already be exposed by default.

Then just run your Spring Boot application
```
./gradlew bootRun
```

Create the shortener by accessing
```url
http://localhost:8080/rest/url
```
POST method and pass a raw URL String.

Then to get back the url:
```url
http://localhost:8080/rest/url/<ID generated>
```