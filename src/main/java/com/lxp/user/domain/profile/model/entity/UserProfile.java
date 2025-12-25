package com.lxp.user.domain.profile.model.entity;

import com.lxp.user.domain.common.model.vo.Level;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.profile.model.vo.Tags;

import java.util.List;
import java.util.Objects;

public class UserProfile {

    private UserId userId;
    private Level level;
    private Tags tags;

    private UserProfile(UserId userId, Level level, Tags tags) {
        this.userId = Objects.requireNonNull(userId, "userId는 null일 수 없습니다.");
        this.level = Objects.requireNonNull(level, "level은 null일 수 없습니다.");
        this.tags = Objects.requireNonNull(tags, "tags는 null일 수 없습니다.");
    }

    public static UserProfile create(UserId userId, Level level, Tags tags) {
        return new UserProfile(userId, level, tags);
    }

    public void update(Level level, List<Long> tags) {
        this.level = level == null ? this.level : level;
        this.tags = tags == null ? this.tags : this.tags.withTags(tags);
    }

    public UserId userId() {
        return this.userId;
    }

    public Level level() {
        return this.level;
    }

    public Tags tags() {
        return this.tags;
    }

}
