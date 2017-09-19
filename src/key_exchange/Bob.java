package key_exchange;

import utils.NumberTheoryUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static key_exchange.DiffeHellman.*;

/**
 * Created by OMKAR JADHAV on 9/19/2017.
 * Copy code, make changes and have fun.
 */

public class  Bob {
    Bob() throws IOException {
        long bobSecretNumber = NumberTheoryUtil.randomNumber( 1000, 100000 );
        //Get public key from Alice
        Socket socket = new Socket( "localhost", 9999 );
        DataInputStream dis = new DataInputStream( socket.getInputStream() );
        DataOutputStream dos = new DataOutputStream( socket.getOutputStream() );

        long P = dis.readLong();
        long e = dis.readLong();
        long A = dis.readLong();

        long bobSecretNum = NumberTheoryUtil.randomNumber( 100, 100000 );
        long B = getHalfKey( P, e, bobSecretNumber );

        dos.writeLong( B );
        dos.flush();


        long finalKey = findSharedKey( P, A, bobSecretNumber);
        System.out.println("Bob has key : " +  finalKey);
        System.out.flush();

        dis.close();
        dos.close();
        socket.close();

    }

    public static void main( String[] args ) throws IOException {
        new Bob();
    }
}
