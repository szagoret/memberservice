package com.memberservice.service;

import com.memberservice.entity.Member;
import com.memberservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * Created by szagoret
 */
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired private MemberRepository memberRepository;

	@Override
	public Collection<Member> findAllMembers() {
		return memberRepository.findAll();
	}

	@Override
	public Member findMemberById(Long id) {
		return memberRepository.findById(id).get();
	}

	@Override
	public Member createMember(Member member) {
		return memberRepository.save(member);
	}

	@Override
	public void deleteMember(Long id) {
		memberRepository.delete(id);
	}

	@Override
	public Member updateMember(Map<String, String> updates, Long memberId) {
		return new Member();
	}
}
