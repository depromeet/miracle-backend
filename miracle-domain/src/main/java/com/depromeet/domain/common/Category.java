package com.depromeet.domain.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 스케쥴 및 목표에 사용되는 카테고리
 */
public enum Category {
    EXERCISE(Arrays.asList("인상에 남는 구절은 무엇인가요?", "오늘 읽은 책에 대한 나의 생각을 적어주세요.")),
    MEDITATION(Arrays.asList("오늘 하늘은 어땠나요?", "내 몸에서 가장 마음에 드는 곳은 어디인가요?")),
    READING(Arrays.asList("오늘은 어떤 생각을 하셨나요?", "어떤 꿈을 자주 꾸나요?")),
    PROMISE(Arrays.asList("5년 뒤 나의 모습은 어떨까요?", "올해 이루고 싶은 일은 무엇인가요?")),
    DIARY(Arrays.asList("어제 가장 행복했던 순간은 무엇이었나요?", "오늘 일기에 등장한 사람은 누구인가요?")),
    PLAN(Arrays.asList("오늘 하루 꼭 이루고 싶은 것은 무엇인가요?", "오늘 계획을 성공한다면, 자신에게 주고 싶은 선물은 무엇인가요?")),
    ETC(Arrays.asList("TBD", "TBD"));

    private final List<String> comments;

    Category(List<String> comments) {
        this.comments = comments;
    }

    public String retrieveRecordComment() {
        Collections.shuffle(comments);
        return comments.get(0);
    }
}
