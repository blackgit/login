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

    private static String codificarCadena(String code){
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

}
