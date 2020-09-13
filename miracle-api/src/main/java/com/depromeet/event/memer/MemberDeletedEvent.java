package com.depromeet.event.memer;

public class MemberDeletedEvent {

    private final Long memberId;

    private MemberDeletedEvent(Long memberId) {
        this.memberId = memberId;
    }

    public static MemberDeletedEvent of(Long memberId) {
        return new MemberDeletedEvent(memberId);
    }

    public Long getMemberId() {
        return memberId;
    }
    
}
