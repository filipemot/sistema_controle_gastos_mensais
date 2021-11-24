package br.com.luisfilipemota.controlegastospessoais.util.seguranca;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Sha256 {
    public static String getSenha(String texto){
        
        MessageDigest digest = getInstance("SHA-256");
        byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static MessageDigest getInstance(String method) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(method);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return messageDigest;
    }
}
