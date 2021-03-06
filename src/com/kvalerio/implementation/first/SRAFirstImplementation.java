package com.kvalerio.implementation.first;

import com.kvalerio.Utils;

import java.util.Random;

public class SRAFirstImplementation {

    private static final int primeNumber = 2129;
    private final int encryptionKey;
    private final int decryptionKey;

    public SRAFirstImplementation() {
        this.encryptionKey = getEncryptionKeys();
        this.decryptionKey = moduloInverseBruteforce(encryptionKey, primeNumber - 1);
    }

    /*
    Permet de calculer le modulo inverse utilisé pour calculer la clé de déchiffrement
    Utilise une simple méthode de bruteforce
    On essaye de trouver quel est le result qui fait que a*result % mod vaut 1
     */
    private int moduloInverseBruteforce(int a, int mod) {
        a %= mod;
        int result = 1;
        for (; result < mod; result++) {
            if ((a * result) % mod == 1)
                break;
        }
        return result;
    }

    private int getEncryptionKeys() {
        int phi = primeNumber - 1;
        int x = new Random().nextInt(500);

        while (Utils.GCD(phi, x) != 1) {
            x = new Random().nextInt(500);
        }
        return x;
    }

    /*
    Algorithme d'exponentiation modulaire
    Méthode right-to-left
    Se base sur l'exponentiation au carré
        ==> Si exponent est pair -> puissance(x², n/2)
        ==> Sinon -> x * puissance(x², (n-1) / 2))
     On calcule des modulos intermediaire afin de préserver la mémoire de très grands nombres
    */
    private long moduloPow(long x, long exponent) {
        long result = 1;
        while (exponent > 0) {
            if ((exponent & 1) > 0) {
                result = (result * x) % primeNumber;
            }
            exponent >>= 1;
            x = (x * x) % primeNumber;
        }
        return result;
    }

    public long encrypt(long message) {
        return moduloPow(message, encryptionKey);
    }

    public long decrypt(long cypher) {
        return moduloPow(cypher, decryptionKey);
    }
}