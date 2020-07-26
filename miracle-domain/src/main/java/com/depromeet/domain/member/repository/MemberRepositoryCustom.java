package com.depromeet.domain.member.repository;

import com.depromeet.domain.member.Member;

public interface MemberRepositoryCustom {

    Member findMemberByEmail(String email);

    Member findMemberById(Long id);

}
