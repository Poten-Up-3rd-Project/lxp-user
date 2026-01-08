package com.lxp.user.infrastructure.web.external.client.adapter;

import com.lxp.user.application.port.required.TagServicePort;
import com.lxp.user.application.port.required.query.TagResult;
import com.lxp.user.domain.common.exception.UserErrorCode;
import com.lxp.user.domain.common.exception.UserException;
import com.lxp.user.infrastructure.web.external.client.TagServiceFeignClient;
import com.lxp.user.infrastructure.web.external.client.dto.TagListResponse;
import com.lxp.user.infrastructure.web.external.client.support.ResponseUnwrapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagServiceAdapter implements TagServicePort {

    private final TagServiceFeignClient tagServiceFeignClient;

    @Override
    @CircuitBreaker(name = "tagService", fallbackMethod = "findTagsFallback")
    public List<TagResult> findTags(List<Long> ids) {
        TagListResponse tagListResponse = ResponseUnwrapper.unwrapResponse(tagServiceFeignClient.findTags(ids));

        List<TagResult> tagResults = tagListResponse.tags().stream().map(tag ->
            new TagResult(tag.id(), tag.name(), tag.color(), tag.variant())
        ).toList();

        log.info("find tags success, ids={}, tags={}", ids, tagResults);
        return tagResults;
    }

    private List<TagResult> findTagsFallback(List<Long> ids, Throwable t) {
        log.warn("CircuitBreaker fallback - findTags: ids={}", ids, t);
        throw new UserException(UserErrorCode.EXTERNAL_SERVICE_ERROR, "Tag service is temporarily unavailable", t);
    }

}
