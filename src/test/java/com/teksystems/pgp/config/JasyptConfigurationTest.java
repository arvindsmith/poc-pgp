package com.teksystems.pgp.config;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JasyptConfigurationTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Autowired
    private StringEncryptor stringEncryptorBc;

    @Test
    public void encryptAndDecryptWithDefaultConfig(){
        //Given
        String privateData = "secret-data";

        //When
        String myEncryptedText = stringEncryptor.encrypt(privateData);
        assertNotSame(privateData, myEncryptedText);

        // then
        String plainText = stringEncryptor.decrypt(myEncryptedText);
        assertEquals(plainText, privateData);

    }

    @Test
    public void encryptAndDecryptUsingBouncyCastleProvider(){
        //Given
        String privateData = "secret-data";

        //When
        String myEncryptedText = stringEncryptorBc.encrypt(privateData);
        assertNotSame(privateData, myEncryptedText);

        // then
        String plainText = stringEncryptorBc.decrypt(myEncryptedText);
        assertEquals(plainText, privateData);

    }
}