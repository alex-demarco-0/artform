package it.artform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.artform.pojos.Post;

/*
 * Componente necessario per convertire il JSON testuale dal body multipart delle richieste POST in oggetto Post
 */

@Component
public class StringToPostConverter implements Converter<String, Post> {
	
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public Post convert(String source) {
        try {
			return objectMapper.readValue(source, Post.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
    }
}
