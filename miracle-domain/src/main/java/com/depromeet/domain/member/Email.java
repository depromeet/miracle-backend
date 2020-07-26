package com.depromeet.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {

    private final static Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9,-]+\\.[a-zA-Z]{2,6}$");

    @Column(nullable = false)
    private String email;

    private Email(String email) {
        verifyEmailFormat(email);
        this.email = email;
    }

    private void verifyEmailFormat(String email) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException(String.format("(%s)은  이메일 포맷에 맞지 않습니다", email));
        }
    }

    public static Email of(String email) {
        return new Email(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(getEmail(), email1.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

}
