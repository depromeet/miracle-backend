package com.depromeet.service.member.dto.request;

import com.depromeet.domain.member.ProfileIcon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class UpdateMemberInfoRequest {

    @NotBlank(message = "닉네임을 입력해주세요")
    private String name;

    @NotNull(message = "프로필 아이콘을 선택해주세요.")
    private ProfileIcon profileIcon;

    private LocalTime wakeUpTime;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public UpdateMemberInfoRequest(String name, ProfileIcon profileIcon, LocalTime wakeUpTime) {
        this.name = name;
        this.profileIcon = profileIcon;
        this.wakeUpTime = wakeUpTime;
    }

}
