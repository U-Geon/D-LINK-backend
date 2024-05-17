package com.alpha.DLINK.prompt.controller;


import com.alpha.DLINK.prompt.dto.PromptRequest;
import com.alpha.DLINK.prompt.dto.PromptResponse;
import com.alpha.DLINK.prompt.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
@Slf4j
public class MainController {

    private final MainService mainService;

    @PostMapping
    public Mono<ResponseEntity<PromptResponse>> sendPromptToFastApi(@RequestBody PromptRequest promptRequest) {
        return mainService.sendToModelServer(promptRequest).map(ResponseEntity::ok);
    }

}
