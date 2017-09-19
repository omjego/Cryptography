package key_exchange;

import utils.NumberTheoryUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by OMKAR JADHAV on 9/19/2017.
 *
 */


/**
 *  Find about this algorithm on
 *  https://security.stackexchange.com/questions/45963/diffie-hellman-key-exchange-in-plain-english
 */
public class DiffeHellman {

    /**
     *
     * Generates pair of (P,e) where P is prime number and e is any random number required for Diffie Hellman
     * @return
     */
    public static long[] generatePulicKey() {
        return new long[]{ NumberTheoryUtil.getRandomPrime(), NumberTheoryUtil.randomNumber( 1000, 100000) };
    }

    /**
     * Finding common key. G^(a*b) mod P
     * @param P  Prime number( Part of public data)
     * @param halfPrivateKey G^b got from peer
     * @param mySecretNumber Number a, secret number generated in first step of algorithm
     * @return Returns G^(a*b) % P, which will be used to encrypt data afterwords.
     */

    public static long findSharedKey(long P, long halfPrivateKey, long mySecretNumber) {
        return NumberTheoryUtil.modPower( halfPrivateKey, mySecretNumber, P);
    }

    public static long getHalfKey(long P, long e, long mySecretNumber) {
        long result = NumberTheoryUtil.modPower( e, mySecretNumber, P);
        return result;
    }


    public static void main( String[] args ) throws IOException {
    }
}
