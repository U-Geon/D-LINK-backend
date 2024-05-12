package com.alpha.DLINK.flask.controller;


import com.alpha.DLINK.flask.dto.PromptRequest;
import com.alpha.DLINK.flask.service.FlaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    @Operation(summary = "메인 기능", description = "입력된 프롬프트를 Model Server에 전송")
    public String prompt(@RequestBody @Valid PromptRequest promptRequest) {
        return flaskService.sendToFlask(promptRequest);
    }

}
