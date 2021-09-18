package com.example.vert.network.tcp.security;

import reactor.core.publisher.Mono;

public interface CertificateManager {

    Mono<Certificate> getCertificate(String id);

}
