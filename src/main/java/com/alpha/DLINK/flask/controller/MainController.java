package com.alpha.DLINK.flask.controller;


import com.alpha.DLINK.flask.dto.PromptRequest;
import com.alpha.DLINK.flask.service.FlaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
@Slf4j
public class MainController {

    private final FlaskService flaskService;

    @PostMapping
    public String prompt(@RequestBody PromptRequest promptRequest) throws JsonProcessingException {
        return flaskService.sendToFlask(promptRequest);
    }
}
