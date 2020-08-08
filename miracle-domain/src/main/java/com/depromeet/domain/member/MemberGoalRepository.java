package com.depromeet.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 테스트용의 Repository
 */
public interface MemberGoalRepository extends JpaRepository<MemberGoal, Long> {

}
