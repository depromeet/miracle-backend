package com.depromeet.controller.schedule;

import com.depromeet.controller.InMemoryLoginMemberArgumentResolver;
import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.domain.schedule.Schedule;
import com.depromeet.service.schedule.ScheduleService;
import com.depromeet.service.schedule.dto.*;
import com.depromeet.util.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
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

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduleController 테스트")
class ScheduleControllerTest {

    private final long memberId = 1L;
    private final LocalTime startTime = LocalTime.of(8, 0);
    private final LocalTime endTime = LocalTime.of(9, 0);

    private MockMvc mockMvc;

    @Mock
    private ScheduleService service;

    @InjectMocks
    private ScheduleController controller;

    private static final class LocalTimeAdapter extends TypeAdapter<LocalTime> {
        @Override
        public void write(JsonWriter out, LocalTime value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public LocalTime read(JsonReader in) throws IOException {
            return LocalTime.parse(in.nextString());
        }
    }

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
        CreateScheduleRequest request = new CreateScheduleRequest(Category.EXERCISE, "description", Arrays.asList(DayOfTheWeek.MON), startTime, endTime);
        List<Schedule> schedules = request.toEntity(memberId);

        Class clazz = Class.forName("com.depromeet.domain.schedule.Schedule");
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        field.set(schedules.get(0), memberId);

        // given
        given(service.createSchedule(memberId, request)).willReturn(CreateScheduleResponse.of(schedules));

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/schedule")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new GsonBuilder().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter().nullSafe()).create().toJson(request))
        );

        // then
        resultActions.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data.scheduleIds[0]").isNumber())
            .andExpect(jsonPath("$.data.scheduleIds[0]").value(1L));
    }

    @DisplayName("스케쥴을 조회할 수 있다")
    @Test
    void retrieveSchedule_ShouldSuccess() throws Exception {
        // given
        given(service.retrieveDailySchedule(memberId, DayOfTheWeek.MON)).willReturn(Arrays.asList(new GetScheduleResponse(1L, Category.EXERCISE, "description", DayOfTheWeek.MON, LocalTime.now(), LocalTime.now())));

        // when
        final ResultActions resultActions = mockMvc.perform(
            get("/api/v1/schedule")
                .param("dayOfTheWeek", "MON")
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data.[0].scheduleId").value(1L))
            .andExpect(jsonPath("$.data.[0].category").value(Category.EXERCISE.name()))
            .andExpect(jsonPath("$.data.[0].description").value("description"))
            .andExpect(jsonPath("$.data.[0].dayOfTheWeek").value("MON"))
            .andExpect(jsonPath("$.data.[0].startTime").exists())
            .andExpect(jsonPath("$.data.[0].endTime").exists());
    }

    @DisplayName("스케쥴을 수정할 수 있다")
    @Test
    void updateSchedule_ShouldSuccess() throws Exception {
        UpdateScheduleRequest request = new UpdateScheduleRequest(Category.EXERCISE, "description", DayOfTheWeek.MON, startTime, endTime);
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
