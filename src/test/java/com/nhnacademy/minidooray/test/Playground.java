package com.nhnacademy.minidooray.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public class Playground {
    @Test
    void Test() {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        UriComponents components = builder.scheme("http").host("localhost").port(3434)
                .path("/projects/{projectId}/tasks/{taskId}").encode()
                .buildAndExpand(1231L, 34234L);

        log.info("{}", components.toString());
    }
}
