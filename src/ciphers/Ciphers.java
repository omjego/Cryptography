package ciphers;

import utils.LinearAlgebraUtil;
import utils.NumberTheoryUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by OMKAR JADHAV on 9/2/2017.
 * Copy code, make changes and have fun.
 */

public class Ciphers {

    private static String removeSpaces(String str) {
        return str.replace(" ", "");
    }

    public static String caesarCipherEncrypt(String plainText, int key) {
        
        key %= 26;
        StringBuilder cipher = new StringBuilder("");
        for (int i = 0; i < plainText.length(); i++) {
            int ch = plainText.charAt(i);
            cipher.append((char)( ch - 97 + key) % 26 + 97);
        }
        return cipher.toString();
    }

    public static String caesarCipherDecrypt(String cipherText, int key) {

        key %= 26;
        StringBuilder plain = new StringBuilder("");
        for (int i = 0; i < cipherText.length(); i++) {
            int ch = cipherText.charAt(i);
            plain.append((char)( ch - 97 - key + 26) % 26 + 97);
        }
        return plain.toString();
    }

    private static String convertToRequiredForm(String msg ) {

        //Convert string to appropriate form
        msg = Ciphers.removeSpaces(msg);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < msg.length(); i += 1) {
            char ch = msg.charAt(i);
            if ( i + 1 < msg.length() ) {
                char nextChar = msg.charAt( i + 1 );
                if (ch == nextChar ) {
                    result.append(ch);
                    result.append("x");
                } else {
                    result.append(ch);
                    result.append(nextChar);
                    i += 1;
                }
            } else {
                result.append(ch);
                result.append('x');
            }
        }
        return result.toString();
    }

    /**
     * Creates play fair matrix using provided key
     * @param key
     * @return Returns a 5x5 2D matrix of char.
     */
    private static char[][] getPlayFairMatrix(String key ) {

        char[][] matrix = new char[5][5];
        boolean[] exists = new boolean[26];
        ArrayList<Integer> chars = new ArrayList<>();
        for (int i = 0; i < key.length(); i++) {
            int ch = key.charAt(i) - 97;
            if ( ! exists[ch] ) {
                exists[ch] = true;
                chars.add(ch);
            }
        }
        Arrays.fill(exists, false);

        int x = 0, y = 0;
        for (Integer aChar : chars) {
            int ch = aChar;
            exists[ch] = true;
            // If I or J already checked out and now we're trying J or I respec.
            if (ch == 'i' - 97 && exists['j' - 97]) {
                continue;
            }
            if (ch == 'j' - 97 && exists['i' - 97]) {
                continue;
            }
            matrix[x][y] = (char) (ch + 97);
            y = (y + 1) % 5;
            if (y == 0) {
                x++;
            }
        }

        for (int i = 0; i < 26 && x < 5; i++) {
            if( !exists[i] ) {
                if (i == 'i' - 97 && exists['j' - 97] ) {
                    continue;
                }
                if (i == 'j' - 97 && exists['i' - 97]) {
                    continue;
                }
                exists[i] = true;
                matrix[x][y] = (char)(i + 97);
                y = (y + 1) % 5;
                if (y == 0) {
                    x++;
                }
                if (x == 5) {
                    break;
                }
            }
        }
        return matrix;
    }

    /**
     * Method actually used to encrypt or decrypt the given text with given play fair matrix
     * @param msg Text to be used
     * @param matrix Play fair matrix
     * @param encrypt Flag which denotes either to do encryption(+1) or decryption(-1)
     * @return Returns required text
     */
    @SuppressWarnings("ConstantConditions")
    private static String encodeDecodeUsingMatrix(String msg, char[][] matrix, int encrypt ) {

        //Encode string
        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < msg.length(); i += 2) {
            int x1, x2, y1, y2;
            x1 = y1 = x2 = y2 = -1;
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if (matrix[j][k] == msg.charAt(i)) {
                        x1 = j;
                        y1 = k;
                    }
                    if (matrix[j][k] == msg.charAt(i + 1)) {
                        x2 = j;
                        y2 = k;
                    }
                }
            }
            assert x1 >= 0 && y1 >= 0 && x2 >= 0 && y2 >= 0 : "Invalid matrix";

            //System.out.println(msg.charAt(i) + " " + msg.charAt( i + 1 ));
            if (x1 == x2) {
                cipher.append(matrix[x1][(y1 + encrypt + 5) % 5]);
                cipher.append(matrix[x2][(y2 + encrypt + 5) % 5]);
            } else if (y1 == y2) {
                cipher.append(matrix[(x1 + encrypt + 5) % 5][y1]);
                cipher.append(matrix[(x2 + encrypt + 5) % 5][y2]);
            } else {
                cipher.append(matrix[x1][y2]);
                cipher.append(matrix[x2][y1]);
            }
        }
        return cipher.toString();
    }

    /**
     * Removes spaces, converts string into appropriate form for processing and encrypts plaintext
     * @param msg Text to encrypted
     * @param key Key used for encryption (should be made up of a-z)
     * @param encrypt Flag representing either to msg is to be encrypted or decrypted
     * @return Returns encrypted (or decrypted )text
     */
    private static String playFairHelper(String msg, String key, int encrypt) {

        key = Ciphers.removeSpaces(key);
        msg = Ciphers.convertToRequiredForm( msg );
        char[][] matrix = Ciphers.getPlayFairMatrix(key);

        System.out.println("Play fair matrix : ");
        for(int i = 0 ; i < 5; ++i ) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " " );
            }
            System.out.println("");
        }
        return  encodeDecodeUsingMatrix(msg, matrix, encrypt);
    }

    /**
     * Decrypt given string with given key using Play Fair cipher.
     * @param msg Cipher text to be decrypted
     * @param key Key used
     * @return Returns decrypted message
     */
    public static String playFairDecrypt(String msg, String key ) {
        return playFairHelper(msg.toLowerCase(), key.toLowerCase(), -1);
    }

    /**
     * Encrypt given string with given key using Play Fair cipher.
     * @param msg Plaintext to be encrypted
     * @param key Key used
     * @return Returns encrypted message
     */
    public static String playFairEncryption(String msg, String key) {

        return playFairHelper(msg.toLowerCase(), key.toLowerCase(), +1  );
    }

    /**
     *  Encrypt given data using Hill cipher
     * @param plain  Plaintext
     * @param key  An NxN 2D array used as a key
     * @return Encrypted text
     */
    public static String hillCipherEncrypt(String plain, int[][] key ) {
        plain = removeSpaces( plain.toLowerCase() );
        int keySize = key.length;
        while ( plain.length() % keySize != 0 ) plain += 'x';
        int len = plain.length();
        String cipherText = "";
        for ( int i = 0 ; i < len; i += keySize ) {
            int[] vector = new int[ keySize ];
            for ( int j = 0 ; j < keySize ; j++ ) {
                vector[j] = plain.charAt( i + j ) - 'a';
            }

            // Key * vector
            for ( int j = 0 ; j < keySize; j++ ) {
                int val = 0;
                for ( int k = 0 ; k <  keySize; k++ ) {
                    val += key[ j ][ k ] * vector[ k ];
                }
                val %= 26;
                cipherText += (char)('a' + val);
            }
        }

        return cipherText;
    }

    /**
     * Decrypt Hill cipher encrypted text using Key
     * @param cipher
     * @param key
     * @return
     */
    public static String hillCipherDecrypt(String cipher, int[][] key) {
        int keySize = key.length;
        int[][] inverse = LinearAlgebraUtil.findCofactorMatrix( key );
        inverse = LinearAlgebraUtil.transpose( inverse );
        int determinant = LinearAlgebraUtil.determinant( key, keySize );
        int inv = (int) NumberTheoryUtil.moduloInverseExtendedEuclidean( determinant, 26 );
        for ( int i = 0 ; i < keySize ; i++ ) {
            for ( int j = 0 ; j < keySize ; j++ ) {
                while ( inverse[i][j] < 0 ) inverse[i][j] += 26;
                inverse[ i ][ j ] = inverse[ i ][ j ] %  26;
                inverse[ i ][ j ] *= inv;
                inverse[ i ][ j ] %= 26;
            }
        }
        int len = cipher.length();
        String plainText = "";
        for ( int i = 0 ; i < len; i += keySize ) {
            int[] vector = new int[ keySize ];
            for ( int j = 0 ; j < keySize ; j++ ) {
                vector[j] = cipher.charAt( i + j ) - 'a';
            }

            // Key * vector
            for ( int j = 0 ; j < keySize; j++ ) {
                int val = 0;
                for ( int k = 0 ; k <  keySize; k++ ) {
                    val += inverse[ j ][ k ] * vector[ k ];
                }
                val %= 26;
                plainText += (char)('a' + val);
            }
        }
        return plainText;
    }

}
