package com.depromeet.domain.member;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.BaseTimeEntity;
import com.deprommet.exception.ConflictException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.persistence.CascadeType;
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

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProfileIcon profileIcon;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberGoal> memberGoals = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @Builder
    public Member(String email, String name, ProfileIcon profileIcon, List<Category> goals) {
        this.email = Email.of(email);
        this.name = name;
        this.profileIcon = profileIcon;
        this.provider = AuthProvider.GOOGLE;
        this.type = MemberType.FREE;
        addMemberGoals(goals);
    }

    public static Member newInstance(String email, String name, ProfileIcon profileIcon, List<Category> goals) {
        return Member.builder()
            .email(email)
            .name(name)
            .profileIcon(profileIcon)
            .goals(goals)
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

    public void updateMemberGoals(List<Category> goals) {
        removeAllMemberGoals();
        addMemberGoals(goals);
    }

    private void removeAllMemberGoals() {
        this.memberGoals.clear();
    }

    private void addMemberGoals(List<Category> categories) {
        if (categories == null) {
            return;
        }
        for (Category category : categories) {
            addMemberGoal(category);
        }
    }

    private void addMemberGoal(Category category) {
        validateNonExistGoal(category);
        MemberGoal memberGoal = MemberGoal.of(this, category);
        this.memberGoals.add(memberGoal);
    }

    private void validateNonExistGoal(Category category) {
        if (hasGoal(category)) {
            log.info(String.format("멤버 (%s) 는 목표 (%s)을 이미 설정하였습니다", email, category));
            throw new ConflictException("이미 설정한 목표입니다");
        }
    }

    private boolean hasGoal(Category category) {
        return this.memberGoals.stream()
            .anyMatch(memberGoal -> memberGoal.hasSameCategory(category));
    }

}
