package com.depromeet.domain.member.deleted;

import com.depromeet.domain.BaseTimeEntity;
import com.depromeet.domain.common.Category;
import com.depromeet.domain.member.MemberGoal;
import lombok.AccessLevel;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DeletedMemberGoal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_member_id")
    private DeletedMember deletedMember;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    private DeletedMemberGoal(DeletedMember deletedMember, Category category) {
        this.deletedMember = deletedMember;
        this.category = category;
    }

    public static DeletedMemberGoal of(DeletedMember deletedMember, MemberGoal memberGoal) {
        return new DeletedMemberGoal(deletedMember, memberGoal.getCategory());
    }

}
