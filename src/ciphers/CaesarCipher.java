package ciphers;

public class CaesarCipher {
    /**
     *
     * @param plainText
     * @param key
     * @return Encryped String
     */
    public static String caesarCipherEncrypt(String plainText, int key) {

        key %= 26;
        StringBuilder cipher = new StringBuilder("");
        for (int i = 0; i < plainText.length(); i++) {
            int ch = plainText.charAt(i);
            cipher.append((char)( ch - 97 + key) % 26 + 97);
        }
        return cipher.toString();
    }

    /**
     *
     * @param cipherText
     * @param key
     * @return Decrypted String
     */
    public static String caesarCipherDecrypt(String cipherText, int key) {

        key %= 26;
        StringBuilder plain = new StringBuilder("");
        for (int i = 0; i < cipherText.length(); i++) {
            int ch = cipherText.charAt(i);
            plain.append((char)( ch - 97 - key + 26) % 26 + 97);
        }
        return plain.toString();
    }
}
