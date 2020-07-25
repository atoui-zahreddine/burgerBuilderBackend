package com.burgerbuilder.backend.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class OrderRequest {
    private Map<String,Integer> ingredients =new HashMap<>();
}