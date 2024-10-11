import java.math.BigInteger;

public class CoprimeCheck {

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger temp = b;
            b = a.mod(b);
            a = temp;
        }
        return a;
    }

    public static boolean areCoprime(BigInteger a, BigInteger b) {
        return gcd(a, b).equals(BigInteger.ONE);
    }
}
