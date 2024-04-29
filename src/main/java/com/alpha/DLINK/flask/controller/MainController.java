package com.alpha.DLINK.flask.controller;


import com.alpha.DLINK.flask.dto.PromptRequest;
import com.alpha.DLINK.flask.service.FlaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
@Slf4j
public class MainController {

    private final FlaskService flaskService;

    @GetMapping("/")
    public String prompt(@RequestBody PromptRequest promptRequest) throws JsonProcessingException {
        String s = flaskService.sendToFlask(promptRequest);
        return s;
    }
}
