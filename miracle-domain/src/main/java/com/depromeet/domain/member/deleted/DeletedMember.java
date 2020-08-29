package com.depromeet.domain.member.deleted;

import com.depromeet.domain.BaseTimeEntity;
import com.depromeet.domain.member.AuthProvider;
import com.depromeet.domain.member.Email;
import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberGoal;
import com.depromeet.domain.member.MemberType;
import com.depromeet.domain.member.ProfileIcon;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 회원 탈퇴한 멤버 정보를 특정 기간동안 보관하는 도메인 (백업 용도)
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DeletedMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long backupId; // 삭제되기 전의 id

    @Embedded
    private Email email;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileIcon profileIcon;

    @OneToMany(mappedBy = "deletedMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeletedMemberGoal> deletedMemberGoals = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    private LocalTime wakeUpTime;

    public String getEmail() {
        return this.email.getEmail();
    }

    @Builder
    public DeletedMember(Long backupId, String email, String name, ProfileIcon profileIcon, AuthProvider provider, MemberType type, LocalTime wakeUpTime) {
        this.backupId = backupId;
        this.email = Email.of(email);
        this.name = name;
        this.profileIcon = profileIcon;
        this.provider = provider;
        this.type = type;
        this.wakeUpTime = wakeUpTime;
    }

    public static DeletedMember of(Member member) {
        DeletedMember deletedMember = DeletedMember.builder()
            .backupId(member.getId())
            .email(member.getEmail())
            .name(member.getName())
            .profileIcon(member.getProfileIcon())
            .provider(member.getProvider())
            .type(member.getType())
            .wakeUpTime(member.getWakeUpTime())
            .build();
        deletedMember.addDeletedMemberGoals(member.getMemberGoals());
        return deletedMember;
    }

    private void addDeletedMemberGoals(List<MemberGoal> memberGoals) {
        for (MemberGoal memberGoal : memberGoals) {
            addDeletedMemberGoal(memberGoal);
        }
    }

    private void addDeletedMemberGoal(MemberGoal memberGoal) {
        DeletedMemberGoal deletedMemberGoal = DeletedMemberGoal.of(this, memberGoal);
        this.deletedMemberGoals.add(deletedMemberGoal);
    }

}
