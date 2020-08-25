package com.depromeet.controller.schedule;

import com.depromeet.controller.InMemoryLoginMemberArgumentResolver;
import com.depromeet.domain.common.Category;
import com.depromeet.domain.schedule.LoopType;
import com.depromeet.domain.schedule.Schedule;
import com.depromeet.service.schedule.ScheduleService;
import com.depromeet.service.schedule.dto.*;
import com.depromeet.util.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduleController 테스트")
class ScheduleControllerTest {

    private final long memberId = 1L;
    private final LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 12, 8, 0, 0);
    private final LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 12, 9, 0, 0);
    private final LocalTime startTime = LocalTime.of(8, 0);
    private final LocalTime endTime = LocalTime.of(9, 0);

    private MockMvc mockMvc;

    @Mock
    private ScheduleService service;

    @InjectMocks
    private ScheduleController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .addFilter(new CharacterEncodingFilter("UTF-8"))
            .setCustomArgumentResolvers(new InMemoryLoginMemberArgumentResolver(memberId))
            .build();
    }

    @DisplayName("스케쥴을 등록할 수 있다")
    @Test
    void createSchedule_ShouldSuccess() throws Exception {
        CreateScheduleRequest request = new CreateScheduleRequest(startDateTime, endDateTime, Category.EXERCISE, "description", LoopType.NONE);
        Schedule schedule = request.toEntity(memberId);

        Class clazz = Class.forName("com.depromeet.domain.schedule.Schedule");
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        field.set(schedule, memberId);

        // given
        given(service.createSchedule(memberId, request)).willReturn(CreateScheduleResponse.of(schedule));

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/schedule")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(request))
        );

        // then
        resultActions.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data.scheduleId").isNumber())
            .andExpect(jsonPath("$.data.scheduleId").value(1L));
    }

    @DisplayName("스케쥴을 조회할 수 있다")
    @Test
    void retrieveSchedule_ShouldSuccess() throws Exception {
        // given
        given(service.retrieveDailySchedule(memberId, LocalDate.of(2020, 8, 12))).willReturn(Arrays.asList(new GetScheduleResponse(1L, 2020, 8, 12, 4, startTime, endTime, Category.EXERCISE, "description", LoopType.NONE)));

        // when
        final ResultActions resultActions = mockMvc.perform(
            get("/api/v1/schedule")
                .param("year", "2020")
                .param("month", "8")
                .param("day", "12")
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data.[0].id").value(1L))
            .andExpect(jsonPath("$.data.[0].year").value(2020))
            .andExpect(jsonPath("$.data.[0].month").value(8))
            .andExpect(jsonPath("$.data.[0].day").value(12))
            .andExpect(jsonPath("$.data.[0].dayOfWeek").value(4))
            .andExpect(jsonPath("$.data.[0].startTime").exists())
            .andExpect(jsonPath("$.data.[0].endTime").exists())
            .andExpect(jsonPath("$.data.[0].category").value(Category.EXERCISE.name()))
            .andExpect(jsonPath("$.data.[0].description").value("description"))
            .andExpect(jsonPath("$.data.[0].loopType").exists());
    }

    @DisplayName("스케쥴을 수정할 수 있다")
    @Test
    void updateSchedule_ShouldSuccess() throws Exception {
        UpdateScheduleRequest request = new UpdateScheduleRequest(startDateTime, endDateTime, Category.EXERCISE, "description", LoopType.NONE);
        Schedule schedule = request.toEntity(memberId);

        Class clazz = Class.forName("com.depromeet.domain.schedule.Schedule");
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        field.set(schedule, memberId);

        // given
        given(service.updateSchedule(memberId, 1L, request)).willReturn(UpdateScheduleResponse.of(schedule));

        // when
        final ResultActions resultActions = mockMvc.perform(put("/api/v1/schedule/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(request))
        );

        // then
        resultActions.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data.scheduleId").isNumber())
            .andExpect(jsonPath("$.data.scheduleId").value(1L));
    }
}
