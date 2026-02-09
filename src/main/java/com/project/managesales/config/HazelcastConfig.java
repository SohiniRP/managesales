package com.project.managesales.config;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;

public class HazelcastConfig {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.setInstanceName("local-hz");

        config.getMapConfig("default")
                .setTimeToLiveSeconds(600);

        return Hazelcast.newHazelcastInstance(config);
    }
}
