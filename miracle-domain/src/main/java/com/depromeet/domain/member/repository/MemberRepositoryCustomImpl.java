package com.depromeet.domain.member.repository;

import com.depromeet.domain.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findMemberByEmail(String email) {
        return queryFactory.selectFrom(member)
            .where(
                member.email.eq(email)
            ).fetchOne();
    }

}
