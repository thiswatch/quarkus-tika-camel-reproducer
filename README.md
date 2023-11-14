# Reproducer

There seems to be an incompatibility between `camel-quarkus` and `quarkus-tika` extension.

# TL;DR

```shell
./gradlew quarkusDev

Hit 'r' and enter to run tests -> works

```

Go to `build.gradle.kts` and removing comments for camel (line 16) and tests will fail. You will also notice that 

```shell
./gradlew dependencyInsight --dependency io.quarkiverse.tika:quarkus-tika:2.0.3
```

shows that actually `2.0.2` is used. 
