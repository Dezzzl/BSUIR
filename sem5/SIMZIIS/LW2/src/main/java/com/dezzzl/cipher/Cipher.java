package com.dezzzl.cipher;

public abstract class Cipher {
    public abstract String encode(String publicText);

    public abstract String decode(String ciphertext);
}
