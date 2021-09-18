package com.reactor.example.demo;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/12/28 13:43
 */
public class TestDemo {

    @Test
    public void fluxCreateTest() {
        // 创建 Flux 的方式
        Flux<String> seql = Flux.just("foo", "bar", "foobar");
        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> flux = Flux.fromIterable(iterable);

        // 创建 Mono
        Mono<Object> noData = Mono.empty();
        Mono<String> data = Mono.just("foo");

        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
        Mono<List<Integer>> listMono = numbersFromFiveToSeven.collectList();

    }

    @Test
    public void fluxSubscribeTest() {
        Flux<Integer> ints = Flux.range(1, 4)
                                 .map(i -> {
                                     if (i <= 3) {
                                         return i;
                                     }
                                     throw new RuntimeException("Got to 4");
                                 });
        ints.subscribe(System.out::println,
                System.err::println);
//        ints.subscribe();
//        ints.subscribe(System.out::println);

    }

    @Test
    public void fluxSubscribe2Test() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(System.out::println,
                // 异常执行
                System.err::println,
                // 完成事件处理
                () -> System.out.println("Done"));
    }

    @Test
    public void fluxSubscribe3Test() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(System.out::println,
                // 异常执行
                System.err::println,
                // 完成事件处理
                () -> System.out.println("Done"),
                sub -> sub.request(10));
    }

    @Test
    public void fluxSubscribe4Test() {
        Flux.range(1, 10)
            .doOnRequest(r -> System.out.println("request of " + r))
            .subscribe(new BaseSubscriber<Integer>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(1);
                }

                @Override
                protected void hookOnNext(Integer value) {
                    System.out.println("Cancelling after having received " + value);
                    cancel();
                }
            });

    }

    @Test
    public void generateTest() {
        /*Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3 * state);
                    if (state == 10) {
                        sink.complete();
                    }
                    return state + 1;
                })
            .subscribe(System.out::println);

        Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + state + " = " + 3 * i);
                    if (i == 10) {
                        sink.complete();
                    }
                    return state;
                })
            .subscribe(System.out::println);*/

        Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + state + " = " + 3 * i);
                    if (i == 10) {
                        sink.complete();
                    }
                    return state;
                }, (state) -> System.out.println("state: " + state))
            .subscribe(System.out::println);

    }

    @Test
    public void createTest() {
        Flux.interval(Duration.ofMillis(300), Schedulers.newSingle("test"))
        .subscribe(System.out::println);
    }

}