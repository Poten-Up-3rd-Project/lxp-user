package com.lxp.user.infrastructure.web.internal.client;

import com.lxp.user.application.port.required.query.TagResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "tag-service", url = "${services.tag.url}")
public interface TagServiceFeignClient {

    @GetMapping("/api-v1/tags/findByIds")
    ResponseEntity<List<TagResult>> findTags(@RequestParam List<Long> ids);

}
