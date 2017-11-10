package com.h9.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * MyMappingJackson2HttpMessageConverter:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/9
 * Time: 20:02
 */
public class MyMappingJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    private String jsonPrefix;

    public MyMappingJackson2HttpMessageConverter() {
        this(Jackson2ObjectMapperBuilder.json().build());
    }

    public MyMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, new MediaType[]{MediaType.TEXT_PLAIN,MediaType.TEXT_HTML,MediaType.APPLICATION_JSON, new MediaType("application", "*+json")});
    }

    public void setJsonPrefix(String jsonPrefix) {
        this.jsonPrefix = jsonPrefix;
    }

    public void setPrefixJson(boolean prefixJson) {
        this.jsonPrefix = prefixJson?")]}', ":null;
    }

    protected void writePrefix(JsonGenerator generator, Object object) throws IOException {
        if(this.jsonPrefix != null) {
            generator.writeRaw(this.jsonPrefix);
        }

        String jsonpFunction = object instanceof MappingJacksonValue ?((MappingJacksonValue)object).getJsonpFunction():null;
        if(jsonpFunction != null) {
            generator.writeRaw("/**/");
            generator.writeRaw(jsonpFunction + "(");
        }

    }

    protected void writeSuffix(JsonGenerator generator, Object object) throws IOException {
        String jsonpFunction = object instanceof MappingJacksonValue?((MappingJacksonValue)object).getJsonpFunction():null;
        if(jsonpFunction != null) {
            generator.writeRaw(");");
        }

    }
}
