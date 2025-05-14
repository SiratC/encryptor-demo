# Encryptor Demo

A simple CLI tool demonstrating RSA/AES encryption in Java.

## Build

```bash
# With fat-jar
javac -d out src/main/java/**/*.java
jar cfe encryptor.jar encryption.Encryption -C out .
```

## Run

```bash
./run.sh encrypt -i secret.txt -o secret.bin
```

