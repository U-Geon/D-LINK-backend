package com.alpha.DLINK.flask.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class PromptRequest {
    private final String prompt;
}