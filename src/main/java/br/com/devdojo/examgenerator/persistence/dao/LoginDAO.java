package br.com.devdojo.examgenerator.persistence.dao;

import br.com.devdojo.examgenerator.persistence.model.Token;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.Serializable;

import static org.springframework.http.HttpMethod.POST;

public class LoginDAO implements Serializable {
    private final String BASE_URL = "http://localhost:8085/login";
    private final RestTemplate restTemplat;

    @Inject
    public LoginDAO(RestTemplate restTemplat) {
        this.restTemplat = restTemplat;
    }

    public Token loginReturningToken(String username, String password) {
        String loginJson = "{\"username\":" + addQuotes(username) + ",\"password\":" + addQuotes(password) + "}";
        ResponseEntity<Token> tokenExchange = restTemplat
                .exchange(BASE_URL, POST, new HttpEntity<>(loginJson, createJsonHeader()), Token.class);
        return tokenExchange.getBody();
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private String addQuotes(String value) {
        return new StringBuilder(300).append("\"").append(value).append("\"").toString();
    }
    private HttpHeaders createJsonHeader(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return header;
    }
}
