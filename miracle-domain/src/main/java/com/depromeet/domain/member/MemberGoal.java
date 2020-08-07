package com.depromeet.domain.member;

import com.depromeet.domain.common.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 멤버가 선택한 n개의 목표를 관리하는 도메인
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Category category;

    private MemberGoal(Member member, Category category) {
        this.member = member;
        this.category = category;
    }

    public static MemberGoal of(Member member, Category category) {
        return new MemberGoal(member, category);
    }

}
