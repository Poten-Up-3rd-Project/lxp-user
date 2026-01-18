package com.lxp.user.application.event.policy;

import com.lxp.common.domain.event.BaseDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class DeliveryPolicyResolver {

    /**
     * Domain Event에 따라 전달 정책 결정
     * 핵심 이벤트(user.created, user.updated, user.deleted)는 OUTBOX_REQUIRED
     * 기타 이벤트는 FIRE_AND_FORGET
     *
     * todo recommend 완료되면 수정할 것
     */
    public DeliveryPolicy resolve(BaseDomainEvent event) {
        return DeliveryPolicy.FIRE_AND_FORGET;
//        String eventType = event.getClass().getSimpleName();
//
//        return switch (eventType) {
//            case "UserCreatedEvent", "UserUpdatedEvent", "UserWithdrawEvent" -> DeliveryPolicy.OUTBOX_REQUIRED;
//            default -> DeliveryPolicy.FIRE_AND_FORGET;
//        };
    }
}
