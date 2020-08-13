package com.depromeet.domain.member;

import com.depromeet.domain.BaseTimeEntity;
import com.depromeet.domain.common.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

/**
 * 멤버가 선택한 n개의 목표를 관리하는 도메인
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberGoal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Builder
    private MemberGoal(Member member, Category category) {
        this.member = member;
        this.category = category;
    }

    public static MemberGoal of(Category category) {
        return MemberGoal.builder()
            .category(category)
            .build();
    }

    void setMember(Member member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberGoal that = (MemberGoal) o;
        return Objects.equals(getMember(), that.getMember()) &&
            getCategory() == that.getCategory();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMember(), getCategory());
    }

}
