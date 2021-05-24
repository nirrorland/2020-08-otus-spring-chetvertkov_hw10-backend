package ru.otus.spring.metric;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class VeryCustomHealthIndicator implements HealthIndicator {

    @Value("${server.port}")
    private String port;

    private final String LOCALHOST_URL = "http://localhost:";

    @Override
    public Health health() {

        long result = 0;
        try {
            result = checkSomething(port);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (result <= 0) {
            return Health.down().withDetail("Something Result", result).build();
        }
        return Health.up().build();
    }

    private long checkSomething(String httpPort) throws URISyntaxException {

        final String baseUrl = LOCALHOST_URL + httpPort + "/api/book";
        URI uri = new URI(baseUrl);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        if (result.getStatusCodeValue() == 200) {
            return 100;
        } else {
            return -100;
        }
    }
}


