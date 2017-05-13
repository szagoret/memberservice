package com.memberservice.repository;

import com.memberservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by szagoret
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findById(Long id);
}
