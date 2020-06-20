package com.example.demo;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.couchbase.BucketDefinition;
import org.testcontainers.couchbase.CouchbaseContainer;

import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
public class CouchbaseTestContainerExtension implements BeforeAllCallback {

    private static AtomicBoolean started = new AtomicBoolean(false);

    private static CouchbaseContainer couchbase;

    private String getBucketName() {
        return "testBucket";
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (!started.get()) {
            couchbase = new CouchbaseContainer("couchbase:6.5.1")
                    .withBucket(new BucketDefinition(getBucketName()).withQuota(100))
                    .withCredentials("user", "secret");
            couchbase.start();
            System.setProperty("spring.couchbase.connection-string", couchbase.getConnectionString());
            System.setProperty("spring.couchbase.username", couchbase.getUsername());
            System.setProperty("spring.couchbase.password", couchbase.getPassword());
            started.set(true);
        }
    }

}
