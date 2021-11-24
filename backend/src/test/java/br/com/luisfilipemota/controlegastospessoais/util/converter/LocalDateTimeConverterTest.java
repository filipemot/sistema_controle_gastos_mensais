package br.com.luisfilipemota.controlegastospessoais.util.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;


public class LocalDateTimeConverterTest {

    @Test
    public void testSerializeLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());
        Gson gson = gsonBuilder.create();
        String requestJson = gson.toJson(localDateTime);
        assertThat(requestJson).isEqualTo("\"2015-11-04 17:09:55\"");
    }

    @Test
    public void testDeserializeLocalDateTime() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());
        Gson gson = gsonBuilder.create();
        LocalDateTime localDateTime = gson.fromJson("\"2015-11-04 17:09:55\"", LocalDateTime.class);
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
    }
}
