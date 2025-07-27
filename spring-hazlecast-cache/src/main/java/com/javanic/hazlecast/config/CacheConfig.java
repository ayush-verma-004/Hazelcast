package com.javanic.hazlecast.config;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    public Config configure() {
        return new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig()
                        .setName("userCache")
                        .setEvictionConfig(new EvictionConfig()
                                .setSize(200)
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                                .setEvictionPolicy(EvictionPolicy.LRU))
                        .setTimeToLiveSeconds(2000)
                );
    }
}