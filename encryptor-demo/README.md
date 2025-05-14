# Encryption Demo

A Java command-line tool that illustrates two classical cipher techniques—  
1. **Substitution**: generates a pseudorandom letter mapping from key’s hash,  
2. **Column Transposition**: shuffles fixed-length blocks into a new column order.

## Features
- **Encrypt** any text up to 64 characters at a time, padding with `O` as needed  
- **Decrypt** back to the original plaintext using the same key  
- Includes a basic test harness (`Tester.java`) demonstrating a full encrypt–decrypt 

## Quickstart
```bash
# compile & jar
javac -d out src/main/java/encryption/Encryption.java
jar cfe encryptor.jar encryption.Encryption -C out .

# run
java -jar encryptor.jar encrypt -i secret.txt -o secret.enc
java -jar encryptor.jar decrypt -i secret.enc -o plain.txt
