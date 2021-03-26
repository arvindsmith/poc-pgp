package com.teksystems.pgp.service;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.examples.KeyBasedFileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Security;

@Service
public class EncryptionService {

    private static Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    public void encryptFile(final String originalFile, final String keyFile) throws Exception {

        logger.debug("encryptFile()");

        Security.addProvider(new BouncyCastleProvider());
        String [] arguments  = {"-e",originalFile,keyFile};
        KeyBasedFileProcessor.main(arguments);
    }

    public void decryptFile(final String encryptFile, final String decryptFile, final String keyFile,
                            final String passphrase) throws Exception {

        logger.debug("decryptFile()");
        Security.addProvider(new BouncyCastleProvider());
        String [] arguments  = {"-d",encryptFile,keyFile,passphrase};
        KeyBasedFileProcessor.main(arguments);
        Files.copy(Paths.get(new FileSystemResource("original.txt").getFile().getPath()), new FileOutputStream(decryptFile));
    }
}
