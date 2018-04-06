/*
 * Project - RSA
 * Created By Patel Jay.
 * On 19 3 2018.
 */

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA {

    private static final int BIT = 50;
    private static final BigInteger ONE = BigInteger.valueOf(1);

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String input;
        System.out.println("Enter the plain text:");
        input = in.nextLine();

        BigInteger p, q, N, phiN, e, d;
        Random r = new Random();

        p = BigInteger.probablePrime(BIT, r);
        q = BigInteger.probablePrime(BIT, r);

        N = p.multiply(q);
        e = BigInteger.probablePrime(BIT / 2, r);

        phiN = p.subtract(ONE).multiply(q.subtract(ONE));

        while (phiN.gcd(e).compareTo(ONE) > 0) {
            e.add(ONE);
        }

        d = e.modInverse(phiN);

        byte txt[] = input.getBytes();
        BigInteger ct = encDnc(txt, e, N);
        BigInteger pt = encDnc(ct.toByteArray(), d, N);

       	System.out.println("CT: " + ct);
       	System.out.println("PT: " + pt);
        System.out.println("CT: " + new String(ct.toByteArray()));
        System.out.println("PT: " + new String(pt.toByteArray()));
    }

    private static BigInteger encDnc(byte[] message, BigInteger ed, BigInteger N) {
        return new BigInteger(message).modPow(ed, N);
    }
}