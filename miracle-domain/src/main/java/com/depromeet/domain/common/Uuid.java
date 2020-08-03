package com.depromeet.domain.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Uuid {

    @Column(nullable = false)
    private String uuid;

    private Uuid(String uuid) {
        this.uuid = uuid;
    }

    public static Uuid newInstance() {
        return new Uuid(String.format("%s", UUID.randomUUID()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Uuid uuid1 = (Uuid) o;
        return Objects.equals(getUuid(), uuid1.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

}
