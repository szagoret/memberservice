package com.memberservice.service;

import com.memberservice.entity.Member;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Map;

/**
 * Created by szagoret
 */
public interface MemberService {

	Collection<Member> findAllMembers();

	Member findMemberById(Long id);

	Member createMember(Member member);

	void deleteMember(Long id);

	Member updateMember(Map<String, String> updates, @PathVariable Long memberId);

}
