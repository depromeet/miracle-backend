package com.depromeet.domain.member.repository;

import com.depromeet.domain.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.domain.member.QMember.member;
import static com.depromeet.domain.member.QMemberGoal.memberGoal;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findMemberByEmail(String email) {
        return queryFactory.selectFrom(member)
            .where(
                member.email.email.eq(email)
            ).fetchOne();
    }

    @Override
    public Member findMemberById(Long id) {
        return queryFactory.selectFrom(member).distinct()
            .leftJoin(member.memberGoals, memberGoal).fetchJoin()
            .where(
                member.id.eq(id)
            ).fetchOne();
    }

}
