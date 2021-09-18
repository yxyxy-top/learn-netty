package com.example.vert.network.tcp.security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import java.security.cert.X509Certificate;

public interface Certificate {

    String getId();

    String getName();

    KeyManagerFactory getKeyManagerFactory();

    TrustManagerFactory getTrustManagerFactory();

    X509KeyManager getX509KeyManager(String serverName);

    X509KeyManager[] getX509KeyManagers();

    X509Certificate[] getCertificateChain(String serverName);

    X509Certificate[] getTrustCerts();

    TrustManager[] getTrustManager(String serverName);
}
