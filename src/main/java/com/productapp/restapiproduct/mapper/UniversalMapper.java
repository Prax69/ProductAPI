package com.productapp.restapiproduct.mapper;

import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UniversalMapper {
    private final ModelMapper modelMapper;

    public UniversalMapper() {
        this.modelMapper = new ModelMapper();
    }

    public <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream().map(source -> modelMapper.map(source, targetClass)).collect(Collectors.toList());
    }
}