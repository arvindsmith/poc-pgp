package com.teksystems.pgp.service;

import com.teksystems.pgp.utils.PGPUtils;
import org.bouncycastle.openpgp.PGPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchProviderException;

@Service
public class EncryptionService {

    private static Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    public void encryptFile(final String originalFile, final FileInputStream keyFile, final FileOutputStream encryptFile) throws NoSuchProviderException, IOException, PGPException {

        logger.debug("encryptFile()");

        PGPUtils.encryptFile(encryptFile, originalFile, PGPUtils.readPublicKey(keyFile));

        keyFile.close();
        encryptFile.close();

    }

    public void decryptFile(final FileInputStream encryptFile, final FileOutputStream decryptFile, final FileInputStream keyFile,
                            final String passphrase) throws NoSuchProviderException, IOException, PGPException {

        logger.debug("decryptFile()");

        PGPUtils.decryptFile(encryptFile, decryptFile, keyFile, passphrase.toCharArray());

        encryptFile.close();
        decryptFile.close();
        keyFile.close();

    }
}
