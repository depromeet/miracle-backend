package com.depromeet.domain.member.deleted;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 테스트용도의 Repository
 */
public interface DeletedMemberGoalRepository extends JpaRepository<DeletedMemberGoal, Long> {

}
