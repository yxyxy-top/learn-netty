package com.vertx.example.mqtt.simple;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/12/4 17:05
 */
public class Client extends AbstractVerticle {

    public static final String MQTT_TOPIC = "/my_topic";
    private static final String MQTT_MESSAGE = "Hello Vert.x MQTT Client";
    private static final String BROKER_HOST = "localhost";
    private static final int BROKER_PORT = 1883;

    public static void main(String[] args) {

        MqttClientOptions options = new MqttClientOptions().setKeepAliveTimeSeconds(2);

        MqttClient mqttClient = MqttClient.create(Vertx.vertx(),options);
        mqttClient.connect(BROKER_PORT, BROKER_HOST, ch -> {
            if (ch.succeeded()) {
                System.out.println("Connected to a server");
                mqttClient.publish(
                        MQTT_TOPIC,
                        Buffer.buffer(MQTT_MESSAGE),
                        MqttQoS.AT_MOST_ONCE,
                        false,
                        false,
                        s -> mqttClient.disconnect(d -> System.out.println("Disconnected from server"))
                );
            } else {
                System.out.println("Failed to connect to a server");
                System.out.println(ch.cause());
            }
        });

    }

}