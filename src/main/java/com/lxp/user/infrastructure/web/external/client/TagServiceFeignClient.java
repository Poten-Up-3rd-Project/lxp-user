package com.lxp.user.infrastructure.web.external.client;

import com.lxp.user.infrastructure.web.external.client.dto.TagListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@FeignClient(name = "tag-service", url = "${services.tag-service.url}")
public interface TagServiceFeignClient {

    /**
     * TODO
     *  응답값은 tag service 구현되면 수정 예정
     * @param ids
     * @return
     */
    @GetMapping("")
    ResponseEntity<TagListResponse> findTags(@ModelAttribute List<Long> ids);

}
