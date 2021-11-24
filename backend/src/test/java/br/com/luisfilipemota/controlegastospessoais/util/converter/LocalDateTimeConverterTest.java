package br.com.luisfilipemota.controlegastospessoais.util.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;


public class LocalDateTimeConverterTest {

    @Test
    public void testSerializeLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55);
        Gson gson = getGson();
        String requestJson = gson.toJson(localDateTime);
        assertThat(requestJson).isEqualTo("\"2015-11-04 17:09:55\"");
    }

    @Test
    public void testDeserializeLocalDateTime() {
        Gson gson = getGson();
        LocalDateTime localDateTime = gson.fromJson("\"2015-11-04 17:09:55\"", LocalDateTime.class);
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2015, Month.NOVEMBER, 4, 17, 9, 55));
    }

    @NotNull
    private Gson getGson() {
        GsonBuilder gsonBuilder = getGsonBuilder();
        return gsonBuilder.create();
    }

    @NotNull
    private GsonBuilder getGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());
        return gsonBuilder;
    }
}
