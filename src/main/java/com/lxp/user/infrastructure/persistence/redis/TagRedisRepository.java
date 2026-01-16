package com.lxp.user.infrastructure.persistence.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagRedisRepository {

    private static final String TAG_ID_KEY = "tag:id:";

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    public List<TagRedisEntity> findByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        List<String> keys = ids.stream()
            .map(id -> TAG_ID_KEY + id)
            .toList();

        List<String> cached = redisTemplate.opsForValue().multiGet(keys);

        if (cached == null) {
            return List.of();
        }

        return cached.stream()
            .filter(Objects::nonNull)
            .map(this::toTagRedisEntity)
            .filter(Objects::nonNull)
            .toList();
    }

    private TagRedisEntity toTagRedisEntity(String json) {
        try {
            return objectMapper.readValue(json, TagRedisEntity.class);
        } catch (JsonProcessingException e) {
            log.error("[TagCache] 역직렬화 실패 - json={}", json, e);
            return null;
        }
    }

}
