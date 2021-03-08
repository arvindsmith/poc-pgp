package com.teksystems.pgp.service;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.examples.KeyBasedFileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Security;

@Service
public class EncryptionService {

    private static Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    public void encryptFile(final String originalFile, final String keyFile, final String encryptFile,final boolean armor,final boolean  withIntegrityCheck) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        logger.debug("encryptFile()");

        Security.addProvider(new BouncyCastleProvider());
        Method m = KeyBasedFileProcessor.class.getDeclaredMethod("encryptFile", String.class,String.class, String.class,Boolean.TYPE,Boolean.TYPE);
        m.setAccessible(true); //if security settings allow this
        m.invoke(null, encryptFile,originalFile,keyFile,armor,withIntegrityCheck);
    }

    public void decryptFile(final String encryptFile, final String decryptFile, final String keyFile,
                            final String passphrase) throws Exception {

        logger.debug("decryptFile()");
        Security.addProvider(new BouncyCastleProvider());
        Method m = KeyBasedFileProcessor.class.getDeclaredMethod("decryptFile", String.class,String.class, char[].class,String.class);
        m.setAccessible(true);
        m.invoke(null, encryptFile,keyFile,passphrase.toCharArray(),decryptFile);
    }
}
