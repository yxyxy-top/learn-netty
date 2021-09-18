package com.example.vert.network;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefaultNetworkType implements NetworkType {

    TCP_CLIENT("TCP客户端"),
    TCP_SERVER("TCP服务"),

    MQTT_CLIENT("MQTT客户端"),
    MQTT_SERVER("MQTT服务"),

    HTTP_CLIENT("HTTP客户端"),
    HTTP_SERVER("HTTP服务"),

    WEB_SOCKET_CLIENT("WebSocket客户端"),
    WEB_SOCKET_SERVER("WebSocket服务"),

    UDP("UDP"),

    COAP_CLIENT("CoAP客户端"),
    COAP_SERVER("CoAP服务"),

    ;

    private String name;

    @Override
    public String getId() {
        return name();
    }
}
