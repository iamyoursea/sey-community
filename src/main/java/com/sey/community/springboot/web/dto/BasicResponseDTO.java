package com.sey.community.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BasicResponseDTO {

    private final String name;
    private final int amount;

}
