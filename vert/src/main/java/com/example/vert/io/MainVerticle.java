package com.example.vert.io;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/9/28 14:36
 */
public class MainVerticle extends AbstractVerticle {


    private HttpServer httpServer;

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
        httpServer = vertx.createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x!");
        });

        httpServer.listen(8080, res -> {
            if (res.succeeded()) {
                startPromise.complete();
            } else {
                startPromise.fail(res.cause());
            }
        });
    }

    /**
     * Stop the verticle instance.
     * <p>
     * Vert.x calls this method when un-deploying the instance. You do not call it yourself.
     * <p>
     * A promise is passed into the method, and when un-deployment is complete the verticle should either call
     * {@link Promise#complete} or {@link Promise#fail} the future.
     *
     * @param stopPromise the future
     */
    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
    }
}