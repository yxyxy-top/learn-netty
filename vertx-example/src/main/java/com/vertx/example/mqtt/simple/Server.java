package com.vertx.example.mqtt.simple;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttServer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/12/4 15:43
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        MqttServer server = MqttServer.create(Vertx.vertx());
        server.endpointHandler(endpoint -> {
            System.out.println("connected client " + endpoint.clientIdentifier());

            endpoint.publishHandler(message ->
                    System.out.println("Just received message on [" +
                            message.topicName() + "] payload [" +
                            message.payload() + "] with QoS [" +
                            message.qosLevel() + "]"));
            endpoint.accept(false);

            endpoint.disconnectHandler(v -> System.out.println("Received disconnect from client"));

            endpoint.subscribeHandler(subscribe -> {
                List<MqttQoS> grantedQosLevels = new ArrayList<>();
                subscribe.topicSubscriptions().forEach(s -> {
                    System.out.println("Subscription for " + s.topicName() + " with QoS " + s.qualityOfService());
                    grantedQosLevels.add(s.qualityOfService());
                });
                endpoint.subscribeAcknowledge(subscribe.messageId(), grantedQosLevels);
            });

            endpoint.unsubscribeHandler(unsubscribe -> {
                unsubscribe.topics().forEach(t -> System.out.println("Unsubscription for " + t));
                endpoint.unsubscribeAcknowledge(unsubscribe.messageId());
            });

            endpoint.publishHandler(message -> {
                System.out.println("Just received message [" + message.payload().toString(Charset.defaultCharset()) + "] with QoS [" + message.qosLevel() + "]");
                if (message.qosLevel() == MqttQoS.AT_LEAST_ONCE) {
                    endpoint.publishAcknowledge(message.messageId());
                } else if (message.qosLevel() == MqttQoS.EXACTLY_ONCE) {
                    endpoint.publishRelease(message.messageId());
                }
            }).publishReleaseHandler(endpoint::publishComplete);

            endpoint.publish("my_topic", Buffer.buffer("Hello from the Vert.x MQTT server"), MqttQoS.EXACTLY_ONCE, false, false);

            endpoint.publishAcknowledgeHandler(messageId -> System.out.println("publishAcknowledgeHandler ack for message = " + messageId))
                    .publishReceivedHandler(endpoint::publishRelease)
                    .publishCompletionHandler(messageId -> System.out.println("publishCompletionHandler ack for message = " + messageId));

        });

        server.listen(ar -> {
            if (ar.succeeded()) {
                System.out.println("MQTT server started and listening on port " + server.actualPort());
            } else {
                System.err.println("MQTT server error on start" + ar.cause().getMessage());
            }
        });

        /*server.close(c -> {
            if (c.succeeded()) {
                System.out.println("Success");
            } else {
                System.out.println("Failure");
            }
        });*/


    }
}