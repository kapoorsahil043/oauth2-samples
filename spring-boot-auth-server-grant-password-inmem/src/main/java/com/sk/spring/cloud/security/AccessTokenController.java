package com.sk.springcloud.security;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class AccessTokenController {

    @GetMapping("/accessToken")
    public String getToken(@RequestParam(name = "serviceKey") String serviceKey,@RequestParam String serviceValue){
        System.out.println(serviceKey + " - "+serviceValue);
        String up = serviceKey+":"+serviceValue;

        String uri = "http://localhost:9092/oauth/token";

        String encodedClientData =
                Base64Utils.encodeToString(up.getBytes());

        Mono<JsonNode> jsonNodeMono = WebClient.create()
                .post()
                .uri("http://localhost:9092/oauth/token")
                .header("Authorization", "Basic " + encodedClientData)
                .body(BodyInserters.fromFormData("grant_type", "client_credentials"))
                .retrieve()
                .bodyToMono(JsonNode.class)
                ;

        jsonNodeMono.subscribe(r -> System.out.println("log::my res, "+r.get("access_token").textValue()));



        //extracted(serviceKey, serviceValue, uri);


        return "test1";
    }

    private void extracted(String serviceKey, String serviceValue, String uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(serviceKey, serviceValue);
        //headers.add("Authorization", "Basic " + Base64Utils.encode(up.getBytes()));

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(
                uri, request , String.class);
        System.out.println("log:: response," + response);
    }
}
