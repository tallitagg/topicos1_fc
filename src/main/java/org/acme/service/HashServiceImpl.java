package org.acme.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashServiceImpl implements HashService {

    private String salt = "#$127732&";
    private Integer iterationCount = 403;
    private Integer keyLength = 512;

    @Override
    public String getHashSenha(String senha) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(senha.toCharArray(),
                            salt.getBytes(),
                            iterationCount,
                            keyLength))
                    .getEncoded();

            return Base64.getEncoder().encodeToString(result);

        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Erro ao gerar o hash");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar o hash");
        }
    }

    public static void main(String[] args) {
        HashService hash = new HashServiceImpl();
        System.out.println(hash.getHashSenha("123"));
        System.out.println(hash.getHashSenha("234"));
        System.out.println(hash.getHashSenha("345"));
        System.out.println(hash.getHashSenha("456"));
        System.out.println(hash.getHashSenha("567"));
        System.out.println(hash.getHashSenha("678"));
    }

}