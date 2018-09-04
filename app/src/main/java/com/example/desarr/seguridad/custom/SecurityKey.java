package com.example.desarr.seguridad.custom;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Base64;

import com.example.desarr.seguridad.dummy.ConverterString;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecurityKey {

    public static String codificarParametros(String code){
        int pruebaFijo = 0;
        String valor = code;
        Random aleatorio = new Random();
        List<String> arr0 = new ArrayList<>();
        StringBuilder strofsc = new StringBuilder();
        int token = (pruebaFijo == 1 ? 5 : 1+aleatorio.nextInt((9+1)-1));
        for (int index = 0; index < valor.length(); index+=2) {
            arr0.add(valor.substring(index, Math.min(index + 2,valor.length())));
        }
        strofsc.append(String.valueOf(valor.length()%2)).append(String.format("%03d",valor.length()));
        for (int i = (arr0.size() - 1); i > -1; i--) {
            try{
                strofsc
                        .append((arr0.get(i).length() == 1 ? arr0.get(i)+"x" : arr0.get(i) ))
                        .append(token)
                        .append(pruebaFijo == 1 ? generateRandomString(1) : generateRandomString(10 + aleatorio.nextInt((20 + 1) - 10)));
            }catch(Exception ex){}
        }
        for (int i = 1; i < 100 - strofsc.length(); i++) {
            strofsc.append((1 + aleatorio.nextInt( (9+1) - 1)));
        }
        return strofsc.insert(0, arr0.size()).toString();
    }

    private static String generateRandomString(int tokenLen){
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int characterLength = characters.length();
        String randomString = "";
        Random aleatorio1 = new Random();
        int ind;
        for (int x = 0; x < tokenLen; x++) {
            if (tokenLen == 1)
                ind = 10;
            else
                ind = 1 + aleatorio1.nextInt((characterLength) - 1);
            randomString = randomString + String.valueOf(characters.charAt(ind));
        }
        return String.valueOf(tokenLen) + randomString;
    }

    public static String codificarCadena(String code){
        String encodedText = null;
        try {
            encodedText =
                ConverterString.getHexStringConverterInstance()
                    .stringToCode(code);
        }catch(Exception ex){
            ex.getMessage();
        }
        return encodedText;
    }

    public static String decodificarCadena(String code) {
        String decodedText = null;
        try {
            decodedText = ConverterString.getHexStringConverterInstance()
                    .codeToString(code);
        } catch (Exception ex) {
            ex.getMessage();
        }
        return decodedText;
    }

    public static String codificar(String code){
        String resultado = code;
        char[] arrayLetras = {'C', 'G', 'E', 'L', 'I', 'B'};
        System.out.println(String.valueOf(arrayLetras));
        int limite = arrayLetras.length - 1;
        System.out.println(limite);
        int num = (int)(Math.random()*((limite-0)+1))+0;
        System.out.println(num);
        for (int i = 1; i < num; i++) {
            resultado = Base64.encodeToString(resultado.getBytes(),Base64.NO_WRAP);
        }
        System.out.println(resultado);
        resultado = resultado + "+" + arrayLetras[num];
        System.out.println(resultado);
        resultado = Base64.encodeToString(resultado.getBytes(),Base64.NO_WRAP);
        System.out.println(resultado);
        return resultado;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static String getInstanceKey(String var){
        byte[] keyStart;
        KeyGenerator kgen = null;
        SecureRandom sr;
        SecretKey skey;
        byte[] key = new byte[0];
        try {
            keyStart = "encryption key".getBytes();
            kgen = KeyGenerator.getInstance("AES");
            sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(keyStart);
            kgen.init(128, sr);
            skey = kgen.generateKey();
            key = skey.getEncoded();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return new String(key);

// encrypt
        //byte[] encryptedData = encrypt(key,b);
// decrypt
        //byte[] decryptedData = decrypt(key,encryptedData);
    }
}
