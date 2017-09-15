package asymmetric;

import static utils.NumberTheoryUtil.*;

/**
 * Created by OMKAR JADHAV on 9/15/2017.
 * Copy code, make changes and have fun.
 */
public class RSA {

    /**
     * Encrypts given message using RSA and algorithm and returns the information in the form of list
     * @param msg Message to be encrypted
     * @return array of long integers "arr"
     *         arr[0], arr[1] -> n,e  respectively (part of public key)
     *         arr[2]         -> length of data(len)
     *         arr[3] to arr[3 + len] -> Encrypted data
     */
    public static long[] encrypt(String msg ) {
        long p = getRandomPrime();
        long q = getRandomPrime();
        long n = p * q;
        long phi = (p - 1) * (q - 1);
        long d = phi - 1;
        while ( true ) {
            if ( gcd( phi, d ) == 1 ) {
                break;
            }
        }

        //Can't use Fermat's Little theorem for finding inverse as "n" is not prime.
        long e = moduloInverseExtendedEuclidean( d, phi );
        long[] arr = new long[3 + msg.length()];
        arr[0] = n;
        arr[1] = d;
        arr[2] = e;
        for ( int i = 0 ; i < msg.length() ; i++ ) {
            long current = msg.charAt( i );
            arr[i + 3] = modPower( current, e, n );
        }
        return arr;
    }

    /**
     * Decrypts string already encrypted by RSA algorithm
     * @param arr Set of values of returned by encryption function
     * @return Returns decrypted string
     */
    public static String decrypt(long[] arr) {
        long n = arr[0];
        long d = arr[1];
        long e = arr[2];
        StringBuilder plain = new StringBuilder();
        for ( int j = 3; j < arr.length; ++j ) {
            long decrypted = modPower( arr[j], d, n );
            plain.append( (char) decrypted );
        }
        return plain.toString();
    }

    public static void main( String[] args ) {
        String msg = "ABCDEFGH";
        System.out.println("Plaintext : " + msg );
        String decrypted = decrypt( encrypt( msg ) );
        System.out.println("Decrypted :" + decrypted);
    }
}
