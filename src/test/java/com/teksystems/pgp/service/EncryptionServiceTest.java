package com.teksystems.pgp.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class EncryptionServiceTest {

    private static Logger logger = LoggerFactory.getLogger(EncryptionServiceTest.class);

    @Autowired
    EncryptionService encryptionService;

    @Value("${files.original}")
    private String originalFilePath;

    @Value("${pgp.publicKeyFile}")
    private String publicKeyFilePath;

    @Value("${files.encrypt}")
    private String encryptFilePath;

    @Value("${pgp.secretKeyFile}")
    private String secretKeyFilePath;

    @Value("${pgp.passphrase}")
    private String passphrase;

    @Value("${files.decrypt}")
    private String decryptFilePath;

    @Test
    @Order(1)
    void encryptFile() throws Exception {

        logger.info("encryptTest()");

        encryptionService.encryptFile(originalFilePath,publicKeyFilePath);

        assertEquals((new File(encryptFilePath)).exists(), true);

        List<String> encryptFileContent = Files.readAllLines(Paths.get(encryptFilePath),
                StandardCharsets.ISO_8859_1);

        assertFalse(encryptFileContent.containsAll(
                Files.lines(Paths.get(originalFilePath)).collect(Collectors.toList())));
    }

    @Test
    @Order(2)
    void decryptFile() throws Exception {

        logger.info("decryptTest()");

        encryptionService.decryptFile(encryptFilePath,
                decryptFilePath,
                secretKeyFilePath, passphrase);

        assertEquals((new File(decryptFilePath)).exists(), true);

        List<String> fileContent = Files.readAllLines(Paths.get(decryptFilePath));

        assertTrue(fileContent.containsAll(Files.readAllLines(Paths.get(originalFilePath))));

    }
}