package com.sinama.encryption.business;

import com.sinama.encryption.util.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Constrain {

    public String shaEncrypt(String text, String key) throws Exception {
        if(!text.isEmpty() && !key.isEmpty()){
            return SHA256.getSecurePasswordSHA(text, key);
        }else{
            throw new Exception();
        }
    }

    public String spnEncrypt(String text, String key) throws Exception {
        if(!text.isEmpty() && !key.isEmpty()){
            /*char[] key_char = key.toCharArray();
            return spn.encryptString(key_char, text);*/
            return SPN16.encryptWithSpn(text, key);
        }else{
            throw new Exception();
        }
    }

    public String spnDecrypt(String hash, String key) throws Exception {
        if(!hash.isEmpty() && !key.isEmpty()){
            //return spn.decryptString(key.toCharArray(), hash);
            return SPN16.decryptWithSpn(hash, key);
        }else{
            throw new Exception();
        }

    }

    public void spnFileEncrypt(String key, File inputFile) throws Exception {
        byte[] secretKey = key.getBytes(StandardCharsets.UTF_8);
        String fileName = inputFile.getName() + ".enc";
        FileSPN.encryptWitEcb(inputFile.getPath(), fileName, secretKey);
        Zipping.zip(fileName, fileName);
    }

    public void spnFileDecrypt(String key, File inputFile) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        byte[] secretKey = key.getBytes(StandardCharsets.UTF_8);
        String fileName = inputFile.getName().substring(0, inputFile.getName().length()-4);
        FileSPN.decryptWithEcb(inputFile.getPath(), fileName, secretKey);
    }
}
