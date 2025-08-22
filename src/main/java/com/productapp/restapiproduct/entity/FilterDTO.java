package com.productapp.restapiproduct.entity;

import lombok.Data;

import java.util.Map;

@Data
public class FilterDTO {
    private Map<String, Object> filters;
}
