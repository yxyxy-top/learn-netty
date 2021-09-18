package com.example.vert.metadata;

public interface Converter<T> {

    T convert(Object value);
}
