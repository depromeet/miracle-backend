package com.depromeet.controller.member;

import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberCreator;
import com.depromeet.service.member.MemberService;
import com.depromeet.service.member.dto.response.MemberInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void setUp() {
        member = MemberCreator.create("will.seungho@gmail.com", "강승호", "profileUrl");
    }

    @Test
    void 회원정보_불러오는_API_테스트() throws Exception {
        // given
        given(memberService.getMemberInfo(any())).willReturn(MemberInfoResponse.of(member));

        // when & then
        this.mockMvc.perform(get("/api/v1/member")
            .param("memberId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("data.id", is(equalTo(member.getId()))))
            .andExpect(jsonPath("data.email", is(equalTo(member.getEmail()))))
            .andExpect(jsonPath("data.name", is(equalTo(member.getName()))))
            .andExpect(jsonPath("data.profileUrl", is(equalTo(member.getProfileUrl()))));
    }

}
