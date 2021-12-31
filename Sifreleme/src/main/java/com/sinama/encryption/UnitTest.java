package com.sinama.encryption;

import com.sinama.encryption.util.FileSPN;
import com.sinama.encryption.util.SHA256;
import com.sinama.encryption.util.SPN16;
import com.sinama.encryption.util.Zipping;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.sinama.encryption.util.FileSPN.encryptWitEcb;
import static org.junit.Assert.assertEquals;

public class UnitTest {
    @Test
    public void testSPNEncryption() {
        String definedPlainText = "utkubernamert";
        String encryptedText = SPN16.encryptWithSpn(definedPlainText, "tfencoder");
        String expected = "00010011000100100000110100010011000001000000001100010100000010000000011100001011000000110001010000010010";
        assertEquals(encryptedText, expected);
    }

    @Test
    public void testSHA256Encryption() {
        String definedPlainText = "utkubernamert";
        String encryptedText = SHA256.getSecurePasswordSHA(definedPlainText, "tfencoder");
        String expected = "bfbe4698a64373b103dcc6e51e00f2fc409132a640b855f3be0c88280022f4da";
        assertEquals(encryptedText, expected);
    }

    @Test
    public void testSPNDecryption() {
        String definedCipherText = "00010011000100100000110100010011000001000000001100010100000010000000011100001011000000110001010000010010";
        String plainText = SPN16.decryptWithSpn(definedCipherText, "tfencoder");
        String expected = "utkubernamert";
        assertEquals(plainText, expected);
    }

    @Test
    public void testFileEncryption() throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        byte[] key = "bernabernabernabernabernabernabe".getBytes();
        boolean fileEncryptStatus = encryptWitEcb("C://Users//ASUS//Desktop//tenor.gif", "tenor.gif.enc", key);
        assertEquals(fileEncryptStatus, false);
    }
    @Test
    public void testFileCompress() throws IOException {
        boolean zipStatus = Zipping.zip("C://Users//ASUS//Desktop//tenor.gif","tenor.gif.zip");
        assertEquals(zipStatus, false);
    }
}
