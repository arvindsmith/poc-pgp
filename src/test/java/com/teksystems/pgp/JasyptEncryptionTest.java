package com.teksystems.pgp;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class JasyptEncryptionTest {

    @Test
    public void encryptAndDecryptUsingSamePasswords() {
        // given
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        BasicTextEncryptor textDecryptor = new BasicTextEncryptor();
        String privateData = "secret-data";
        textEncryptor.setPasswordCharArray("some-random-data".toCharArray());
        textDecryptor.setPasswordCharArray("some-random-data".toCharArray());

        // when
        String myEncryptedText = textEncryptor.encrypt(privateData);
        assertNotSame(privateData, myEncryptedText); // myEncryptedText can be save in db

        // then
        String plainText = textDecryptor.decrypt(myEncryptedText);
        assertEquals(plainText, privateData);
    }

    @Test
    public void encryptAndDecryptUsingDifferentPasswordsThrowException() {
        // given
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        BasicTextEncryptor textDecryptor = new BasicTextEncryptor();
        String privateData = "secret-data";
        textEncryptor.setPasswordCharArray("some-random-data".toCharArray());
        textDecryptor.setPasswordCharArray("wrong password".toCharArray());

        // when
        String myEncryptedText = textEncryptor.encrypt(privateData);
        assertNotSame(privateData, myEncryptedText); // myEncryptedText can be save in db

        // then
        assertThatThrownBy(() -> {
            textDecryptor.decrypt(myEncryptedText);
        }).isInstanceOf(EncryptionOperationNotPossibleException.class);

    }

    @Test
    public void checkPasswordOfEncryptorIsSame() {
        String password = "secret-pass";
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        String encryptedPassword = passwordEncryptor.encryptPassword(password);

        // when
        boolean result = passwordEncryptor.checkPassword("secret-pass", encryptedPassword);

        // then
        assertTrue(result);
    }

    @Test
    public void checkPasswordOfEncryptorIsDifferent() {
        String password = "secret-pass";
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(password);

        // when
        boolean result = passwordEncryptor.checkPassword("secret-pass-not-same", encryptedPassword);

        // then
        assertFalse(result);
    }

    @Test
    public void encryptAndDecryptUsingStandardPBEStringEncryptor(){
        //Given
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        String privateData = "secret-data";
        encryptor.setPassword("some-random-passwprd");
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        //when
        String encryptedText = encryptor.encrypt(privateData);
        //then
        assertNotSame(privateData, encryptedText);

        //when
        String plainText = encryptor.decrypt(encryptedText);
        //then
        assertEquals(plainText, privateData);

    }

}
