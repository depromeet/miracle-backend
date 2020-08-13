package com.depromeet.domain.member;

import com.depromeet.domain.BaseTimeEntity;
import com.deprommet.exception.ConflictException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

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
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfileIcon profileIcon;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberGoal> memberGoals = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @Builder
    public Member(String email, String name, ProfileIcon profileIcon) {
        this.email = Email.of(email);
        this.name = name;
        this.profileIcon = profileIcon;
        this.provider = AuthProvider.GOOGLE;
        this.type = MemberType.FREE;
    }

    public static Member newInstance(String email, String name, ProfileIcon profileIcon) {
        return Member.builder()
            .email(email)
            .name(name)
            .profileIcon(profileIcon)
            .build();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public void updateInfo(String name, ProfileIcon profileIcon) {
        if (StringUtils.hasText(name)) {
            this.name = name;
        }
        if (profileIcon != null) {
            this.profileIcon = profileIcon;
        }
    }

    public void updateMemberGoals(List<MemberGoal> memberGoals) {
        if (hasSameMemberGoals(memberGoals)) {
            return;
        }
        removeAllMemberGoals();
        addMemberGoals(memberGoals);
    }

    private boolean hasSameMemberGoals(List<MemberGoal> others) {
        if (this.memberGoals.size() != others.size()) {
            return false;
        }
        return this.memberGoals.containsAll(others);
    }

    private void removeAllMemberGoals() {
        this.memberGoals.clear();
    }

    public void addMemberGoals(List<MemberGoal> memberGoals) {
        for (MemberGoal memberGoal : memberGoals) {
            addMemberGoal(memberGoal);
        }
    }

    private void addMemberGoal(MemberGoal memberGoal) {
        validateNonExistGoal(memberGoal);
        memberGoal.setMember(this);
        this.memberGoals.add(memberGoal);
    }

    private void validateNonExistGoal(MemberGoal memberGoal) {
        if (hasGoal(memberGoal)) {
            throw new ConflictException(String.format("멤버 (%s) 는 목표 (%s)을 이미 설정하였습니다", email, memberGoal.getCategory()), "이미 설정한 목표입니다");
        }
    }

    private boolean hasGoal(MemberGoal other) {
        return this.memberGoals.stream()
            .anyMatch(memberGoal -> memberGoal.equals(other));
    }

}
