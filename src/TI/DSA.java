package TI;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class DSA {

    private static final Scanner keyboard = new Scanner(System.in);
    private final Random random = new Random();
    private final BigInteger oneNegative = new BigInteger("-1");
    private final BigInteger one = new BigInteger("1");
    private final BigInteger zero = new BigInteger("0");
    private final int N = 8;
    private final int L = 128;
    public  BigInteger publicKey;
    public  BigInteger g, p, q;

    public DSA() {
        q = generateQ(N);
        p = generateP(L,q);
        g = generateG(p,q);
    }

    private BigInteger generateQ(int length) {
        return  BigInteger.probablePrime(length,random);
    }

    private BigInteger generateP(int length, BigInteger q) {
        BigInteger _p;
        do {
            _p = BigInteger.probablePrime(length, random);
        } while (!_p.subtract(one).mod(q).equals(zero));
        return _p;
    }

    private BigInteger generateG(BigInteger p, BigInteger q) {
        BigInteger _g,h = new BigInteger("2");
        do {
            _g = h.modPow(p.subtract(one).divide(q),p);
            if(!_g.equals(one)){
                break;
            } else {
                h = h.add(one);
            }
        } while(true);
        return _g;
    }

    public BigInteger generatePrivateKey(BigInteger q) {
        BigInteger _privateKey;
        int length = q.bitLength();
        do {
            _privateKey = new BigInteger(length,random);
        } while (_privateKey.compareTo(q) != -1);
        return _privateKey;
    }

    public void generatePublicKey(BigInteger g, BigInteger p, BigInteger privateKey) {
        publicKey =  g.modPow(privateKey,p);
    }

    public Pair sign(BigInteger message, BigInteger privateKey) {
        BigInteger k;
        do {
            k = new BigInteger(N,random);
        } while (k.compareTo(q) != -1);
        BigInteger r = (g.modPow(k,p)).mod(q);
        BigInteger s = k.modPow(oneNegative,q).multiply(hash(message).add(privateKey.multiply(r))).mod(q);
        return new Pair(r,s);
    }
    
    public boolean verify(Pair signature, BigInteger message) {
        BigInteger w = signature.getSecond().modPow(new BigInteger("-1"),q);
        BigInteger u1 = hash(message).multiply(w).mod(q);
        BigInteger u2 = signature.getFirst().multiply(w).mod(q);
        BigInteger v = g.pow(u1.intValue()).multiply(publicKey.pow(u2.intValue())).mod(p).mod(q);
        System.out.println("v = " + v);
        return v.equals(signature.getFirst());
    }

    private BigInteger hash(BigInteger message) {
        return message.mod(new BigInteger("256")); // 8 bit
    }

    public static void main(String[] args) {
        DSA dsa = new DSA();
        BigInteger privateKey = dsa.generatePrivateKey(dsa.q);
        dsa.generatePublicKey(dsa.g,dsa.p,privateKey);
        System.out.println("Enter message");
        String message = keyboard.nextLine();
        BigInteger messageBI = new BigInteger(message.getBytes());
        Pair signature = dsa.sign(messageBI, privateKey);
        System.out.println("Signature: r = " + signature.getFirst() + " s = " + signature.getSecond());
        dsa.verify(signature,messageBI);
    }

}
