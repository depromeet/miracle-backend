package com.depromeet.domain.member;

import com.depromeet.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
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

    private String email; // TODO convert to Value Object

    private String name;

    private String profileUrl;

    @Builder
    public Member(String email, String name, String profileUrl) {
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
    }

    public static Member newInstance(String email, String name, String profileUrl) {
        return Member.builder()
            .email(email)
            .name(name)
            .profileUrl(profileUrl)
            .build();
    }

}
