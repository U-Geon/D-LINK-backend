package com.alpha.DLINK.flask.service;


import com.alpha.DLINK.flask.dto.PromptRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FlaskService {

    //데이터를 JSON 객체로 변환하기 위해서 사용
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Transactional
    public String sendToFlask(PromptRequest dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String param;

        try {
            param = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            // JSON 변환 실패 처리
            throw new RuntimeException("JSON 변환에 실패했습니다.", e);
        }

        HttpEntity<String> entity = new HttpEntity<>(param, headers);
        String url = "http://10.223.125.219:8000/prediction";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                // 적절한 에러 처리
                throw new RuntimeException("Flask 서버 응답 오류: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // 네트워크 오류 등 처리
            throw new RuntimeException("Flask 서버 요청 실패", e);
        }
    }
}