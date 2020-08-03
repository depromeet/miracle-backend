package com.depromeet.domain.member;

import com.depromeet.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    private String profileUrl;

    private String phoneNumber; // TODO Convert to Value Object & Regex

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @Builder
    public Member(String email, String name, String profileUrl, String phoneNumber) {
        this.email = Email.of(email);
        this.name = name;
        this.profileUrl = profileUrl;
        this.phoneNumber = phoneNumber;
        this.provider = AuthProvider.GOOGLE;
        this.type = MemberType.FREE;
    }

    public static Member newInstance(String email, String name, String profileUrl, String phoneNumber) {
        return Member.builder()
            .email(email)
            .name(name)
            .profileUrl(profileUrl)
            .phoneNumber(phoneNumber)
            .build();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public void updateInfo(String name, String profileUrl, String phoneNumber) {
        if (StringUtils.hasText(name)) {
            this.name = name;
        }
        if (StringUtils.hasText(profileUrl)) {
            this.profileUrl = profileUrl;
        }
        if (StringUtils.hasText(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        }
    }

}
