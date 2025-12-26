package com.lxp.user.infrastructure.persistence.write.entity;

import com.lxp.common.infrastructure.persistence.BaseJpaEntity;
import com.lxp.user.infrastructure.persistence.vo.InfraLevel;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Persistable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "user_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(
    name = "UserProfileJpaEntity.withTags",
    attributeNodes = @NamedAttributeNode("tags")
)
public class UserProfileJpaEntity extends BaseJpaEntity implements Persistable<Long> {

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @Setter
    @Column(name = "learner_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private InfraLevel level;

    @Setter
    @ElementCollection
    @CollectionTable(
        name = "profile_tags",
        joinColumns = @JoinColumn(name = "id"),
        indexes = @Index(name = "idx_profile_tags_tag_id", columnList = "tag_id")
    )
    @Column(name = "tag_id")
    @BatchSize(size = 100)
    private List<Long> tags = new ArrayList<>();

    @Transient
    private boolean isNew = true;

    @Builder
    public UserProfileJpaEntity(UserJpaEntity user, InfraLevel level, List<Long> tags) {
        this.user = user;
        this.level = level;
        this.tags = tags;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @PostLoad
    @PostPersist
    public void markNotNew() {
        this.isNew = false;
    }
}
