import utils.Ciphers;

/**
 * Created by OMKAR JADHAV on 9/2/2017.
 * Copy code, make changes and have fun.
 */
public class Test {
    public static void main(String[] args) {

        String plainText = "abcdefgh";
        //String key = "Hello Google Hackerrank World";
        int[][] key = new int[][] {{3, 4}, {19, 11}};

        String encrypted = Ciphers.hillCipherEncrypt( plainText , key);
        String decrypted = Ciphers.hillCipherDecrypt(encrypted, key);
        System.out.println(plainText + " " + encrypted + " " + decrypted);

    }
}
