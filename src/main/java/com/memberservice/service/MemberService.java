package com.memberservice.service;

import com.memberservice.entity.Member;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Created by szagoret
 */
public interface MemberService {

	Collection<Member> findAllMembers();

	Optional<Member> findMemberById(Long id);

	Member createMember(Member member);

	int deleteMember(Long id);

	Member updateMember(Map<String, String> updates, Long memberId);

}
