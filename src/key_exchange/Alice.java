package key_exchange;

import utils.NumberTheoryUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static key_exchange.DiffeHellman.*;

/**
 * Created by OMKAR JADHAV on 9/19/2017.
 * Copy code, make changes and have fun.
 */
public class Alice {
    Alice() throws IOException {
        long[] publicKey = generatePulicKey();
        long P = publicKey[0];
        long e = publicKey[1];
        long aliceSecretNum = NumberTheoryUtil.randomNumber( 1000, 100000 );
        long A = getHalfKey( P, e, aliceSecretNum );


        //Send public keys to Bob over network.
        ServerSocket server = new ServerSocket(9999);
        System.out.println("Alice listening" );
        Socket bob = server.accept();
        DataOutputStream out = new DataOutputStream( bob.getOutputStream() );
        DataInputStream dis = new DataInputStream( bob.getInputStream() );

        out.writeLong( P );
        out.writeLong( e );
        out.writeLong( A );
        out.flush();

        long B  = dis.readLong();

        long finalKey = findSharedKey( P, B, aliceSecretNum);

        System.out.println("Alice has key :" + finalKey);
        System.out.flush();

        dis.close();
        out.close();
        bob.close();
        server.close();

    }

    public static void main( String[] args ) throws IOException {
        new Alice();
    }
}

