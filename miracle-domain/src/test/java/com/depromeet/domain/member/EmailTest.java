package com.depromeet.domain.member;

import com.deprommet.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class EmailTest {

    @Test
    void 이메일_정규식_테스트_정상_이메일_포맷() {
        // given
        String email = "will.seungho@gmail.com";

        // when
        Email result = Email.of(email);

        // then
        assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test
    void 이메일_정규식_테스트_도메인_없는_주소() {
        // given
        String email = "will.seungho";

        // when & then
        assertThatThrownBy(() -> {
            Email.of(email);
        }).isInstanceOf(ValidationException.class);
    }

    @Test
    void 이메일_정규식_테스트_최상위_도메인_없는_주소() {
        // given
        String email = "will.seungho@gmail";

        // when & then
        assertThatThrownBy(() -> {
            Email.of(email);
        }).isInstanceOf(ValidationException.class);
    }

    @Test
    void 이메일_정규식_테스트_로컬영역_없는_주소() {
        // given
        String email = "@gmail.com";

        // when & then
        assertThatThrownBy(() -> {
            Email.of(email);
        }).isInstanceOf(ValidationException.class);
    }

}
