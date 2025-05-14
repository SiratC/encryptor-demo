# Encryptor Demo

A simple CLI tool demonstrating RSA/AES encryption in Java.

## Build

```bash
# If using fat-jar
javac -d out src/main/java/**/*.java
jar cfe encryptor.jar com.yourname.Main -C out .

# Or, with Maven
mvn clean package
```

## Run

```bash
./run.sh encrypt -i secret.txt -o secret.bin
```

