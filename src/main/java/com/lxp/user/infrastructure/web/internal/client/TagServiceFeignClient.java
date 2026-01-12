package com.lxp.user.infrastructure.web.internal.client;

import com.lxp.user.infrastructure.web.internal.client.dto.TagListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@FeignClient(name = "tag-service", url = "${services.tag.url}")
public interface TagServiceFeignClient {

    /**
     * TODO
     *  응답값은 tag service 구현되면 수정 예시
     * @param ids
     * @return
     */
    @GetMapping("")
    ResponseEntity<TagListResponse> findTags(@ModelAttribute List<Long> ids);

}
