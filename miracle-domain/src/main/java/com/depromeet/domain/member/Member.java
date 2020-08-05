package com.depromeet.domain.member;

import com.depromeet.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

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

    private String name;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @Builder
    public Member(String email, String name) {
        this.email = Email.of(email);
        this.name = name;
        this.provider = AuthProvider.GOOGLE;
        this.type = MemberType.FREE;
    }

    public static Member newInstance(String email, String name) {
        return Member.builder()
            .email(email)
            .name(name)
            .build();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public void updateInfo(String name) {
        if (StringUtils.hasText(name)) {
            this.name = name;
        }
    }

}
