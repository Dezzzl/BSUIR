import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {

    private final BigInteger p;
    private final BigInteger q;
    private final BigInteger n;
    private final BigInteger phi;
    private final BigInteger d;
    private final BigInteger e;

    public BigInteger getN() {
        return n;
    }

    public BigInteger getPhi() {
        return phi;
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public RSA(int bitLength) {
        SecureRandom random = new SecureRandom();
        p = BigInteger.probablePrime(bitLength, random);
        q = BigInteger.probablePrime(bitLength, random);
        n = p.multiply(q);

        phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        if (CoprimeCheck.areCoprime(phi, BigInteger.valueOf(65537L))) e = BigInteger.valueOf(65537);
        else if (CoprimeCheck.areCoprime(phi, BigInteger.valueOf(257))) e = BigInteger.valueOf(257);
        else e = BigInteger.valueOf(17);
        d = e.modInverse(phi);
    }

    public BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return FastExponentiation.fastModularExponentiation(message, e, n);
    }

    public BigInteger decrypt(BigInteger encryptedMessage, BigInteger d, BigInteger n) {
        return FastExponentiation.fastModularExponentiation(encryptedMessage, d, n);
    }

    public BigInteger sign(BigInteger message) {
        return FastExponentiation.fastModularExponentiation(message, d, n);
    }

    public boolean verify(BigInteger message, BigInteger signature) {
        return message.equals(FastExponentiation.fastModularExponentiation(signature, e, n));
    }

    public void writeKeyToFile(String fileName, BigInteger e, BigInteger n) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(e.toString() + "\n" + n.toString());
        fileWriter.close();
    }


    public void writeToFile(String fileName, BigInteger content) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(content.toString());
        fileWriter.close();
    }

    public static BigInteger readMessageFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        BigInteger message = new BigInteger(br.readLine());
        br.close();
        return message;
    }

    public static BigInteger[] readKeyFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        BigInteger e = new BigInteger(br.readLine());
        BigInteger n = new BigInteger(br.readLine());
        br.close();
        return new BigInteger[]{e, n};
    }

    public static void main(String[] args) throws IOException {
        RSA rsa = new RSA(1024);
        System.out.println("p: " + rsa.getP());
        System.out.println("q: " + rsa.getQ());
        System.out.println("n=p*q: " + rsa.getN());
        System.out.println("Значение функции Эйлера от числа n: " + rsa.getPhi());
        System.out.println("e: " + rsa.getE());
        System.out.println("d: " + rsa.getD());
        System.out.println("{e, n} - открытый ключ");
        System.out.println("{d, n} - секретный ключ");
        String publicKeyFile = "publicKey.txt";
        String privateKeyFile = "privateKey.txt";
        String encryptedFile = "encrypted.txt";
        String decryptedFile = "decrypted.txt";

        String[] testMessages = {
                "12345678901234567890",
                "98765432109876543210",
                "11223344556677889900",
                "99887766554433221100",
                "13579246801357924680",
                "24681357902468135790",
                "31415926535897932384",
                "27182818284590452353",
                "16180339887498948445",
                "12345678912345678901"
        };
        for (String strMessage : testMessages) {
            BigInteger message = new BigInteger(strMessage);

            System.out.println("Запись в файл открытого ключа");
            rsa.writeKeyToFile(publicKeyFile, rsa.getE(), rsa.getN());
            System.out.println("Открытый ключ записан в файл.");

            System.out.println("Запись в файл секретного ключа");
            rsa.writeKeyToFile(privateKeyFile, rsa.getD(), rsa.getN());
            System.out.println("Секретный ключ записан в файл.");

            BigInteger[] publicKey = RSA.readKeyFromFile(publicKeyFile);
            BigInteger e = publicKey[0];
            BigInteger n = publicKey[1];
            System.out.println("Открытый ключ получен из файла: e = " + e + ", n = " + n);

            System.out.println("Зашифрование после получения открытого ключа");
            BigInteger encrypted = rsa.encrypt(message, e, n);
            rsa.writeToFile(encryptedFile, encrypted);

            System.out.println("Зфшифрованное сообщение: " + encrypted);
            System.out.println("Чтение зашифрованного сообщения из файла");
            BigInteger encryptedFromFile = RSA.readMessageFromFile(encryptedFile);

            BigInteger[] privateKey = RSA.readKeyFromFile(privateKeyFile);
            BigInteger d_pr = privateKey[0];
            BigInteger n_pr = privateKey[1];
            BigInteger decrypted = rsa.decrypt(encryptedFromFile, d_pr, n_pr);
            System.out.println("Расшифрованное сообщение: " + decrypted);

            rsa.writeToFile(decryptedFile, decrypted);

            if (message.equals(decrypted)) {
                System.out.println("Расшифровка прошла успешно!");
            } else {
                System.out.println("Ошибка при расшифровке.");
            }
            BigInteger signature = rsa.sign(message);
            System.out.println(signature);
            System.out.println("Подпись корректна: " + rsa.verify(message, signature));
            System.out.println();
        }
    }
}
