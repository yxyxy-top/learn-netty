package com.example.vert.io;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/9/28 14:26
 */
public class MyFirstVerticle extends AbstractVerticle {

    int i = 0;//属性变量

    /**
     * Start the verticle instance.
     * <p>
     * Vert.x calls this method when deploying the instance. You do not call it yourself.
     * <p>
     * A promise is passed into the method, and when deployment is complete the verticle should either call
     * {@link Promise#complete} or {@link Promise#fail} the future.
     *
     * @param startPromise the future
     */
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            i++;
            req.response().end();//要关闭请求，否则连接很快会被占满
        }).listen(8080);

        vertx.createHttpServer().requestHandler(req -> {
            System.out.println(i);
            req.response().end();//要关闭请求，否则连接很快会被占满
        }).listen(8081);
    }
}