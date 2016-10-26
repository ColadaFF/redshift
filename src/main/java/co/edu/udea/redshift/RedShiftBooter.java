package co.edu.udea.redshift;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bson.types.ObjectId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = {JacksonAutoConfiguration.class})
public class RedShiftBooter {
    public static void main(String[] args) {
        SpringApplication.run(RedShiftBooter.class, args);
    }

    /*
     * Teachers{Domain} => Teachers{Repository => [create, read, update, delete]} => Teachers{Services => [create => fn(), ...]} => Teachers{Controller => [url:"teachers", method: "GET" => service.readAll, url: "teachers", method: "POST" => service.create, ...]}
     */

    private static class ObjectIdTypeAdapter extends TypeAdapter<ObjectId> {
        @Override
        public void write(final JsonWriter out, final ObjectId value) throws IOException {
            System.out.println(out);
            System.out.println(value);
            out.beginObject()
                    .name("$oid")
                    .value(value.toString())
                    .endObject();
        }

        @Override
        public ObjectId read(final JsonReader in) throws IOException {
            in.beginObject();
            assert "$oid".equals(in.nextName());
            String objectId = in.nextString();
            in.endObject();
            return new ObjectId(objectId);
        }
    }


    @Configuration
    @ConditionalOnClass(Gson.class)
    @ConditionalOnMissingClass(value = "com.fasterxml.jackson.core.JsonGenerator")
    @ConditionalOnBean(Gson.class)
    protected static class GsonHttpMessageConverterConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
            GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
            Gson gsonObjectId = new GsonBuilder().registerTypeAdapter(ObjectId.class, new ObjectIdTypeAdapter()).create();
            converter.setGson(gsonObjectId);
            return converter;
        }
    }
}
