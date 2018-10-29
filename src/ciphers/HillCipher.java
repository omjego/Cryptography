package ciphers;

import utils.LinearAlgebraUtil;
import utils.NumberTheoryUtil;

public class HillCipher {

    /**
     * Removing extra spaces.
     * @param msg
     * @return
     */
    private static String removeSpaces(String msg){
        return msg.replace(" ","");
    }

    /**
     * Encrypting Hill Cipher.
     * @param plain
     * @param key
     * @return
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
