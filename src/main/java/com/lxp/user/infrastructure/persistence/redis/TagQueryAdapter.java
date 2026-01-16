package com.lxp.user.infrastructure.persistence.redis;

import com.lxp.user.application.port.required.TagQueryPort;
import com.lxp.user.application.port.required.query.TagResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TagQueryAdapter implements TagQueryPort {

    private final TagRedisRepository tagRedisRepository;

    @Override
    public List<TagResult> findTags(List<Long> id) {
        return tagRedisRepository.findByIds(id).stream().map(entity ->
            new TagResult(entity.tagId, entity.name, entity.color, entity.variant)
        ).collect(Collectors.toList());
    }
}
