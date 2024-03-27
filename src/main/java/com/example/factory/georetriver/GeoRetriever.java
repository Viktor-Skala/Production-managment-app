package com.example.factory.georetriver;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GeoRetriever {

    private static final String URL = "http://ipinfo.io/";

    public Location getLocation(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(ip);
        return restTemplate.getForEntity(URL + ip, Location.class).getBody();
    }

}
