package br.com.luisfilipemota.controlegastospessoais.util.seguranca;

import br.com.luisfilipemota.controlegastospessoais.util.converter.LocalDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;


import static org.assertj.core.api.Assertions.assertThat;

public class Sha256Test {
    @Test
    public void testGenerateSenhaSha256() {
        String senha = Sha256.getSenha("123456");
        assertThat(senha).isNotNull();
        assertThat(senha).isEqualTo("jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=");
    }

    @Test
    public void testGetInstanceSha256() {
        MessageDigest digest = Sha256.getInstance("SHA-256");
        assertThat(digest).isNotNull();
    }

    @Test
    public void testGetInstanceMethodInexistente() {
        MessageDigest digest = Sha256.getInstance("SHA-256454545");
        assertThat(digest).isNull();
    }
}
