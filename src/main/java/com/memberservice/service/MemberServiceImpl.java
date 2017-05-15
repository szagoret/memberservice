package com.memberservice.service;

import com.memberservice.entity.Member;
import com.memberservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by szagoret on 15.05.2017.
 */
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired private MemberRepository memberRepository;

	@Override
	public Collection<Member> findAllMembers() {
		Member memberSergiu = new Member();
		memberSergiu.setId(12L);
		memberSergiu.setBirthday(new Date());
		memberSergiu.setFirstName("Sergiu");
		memberSergiu.setLastName("Zagoret");
		memberSergiu.setZipCode(123456);
		Member memberDumitru = new Member();
		memberDumitru.setId(1L);
		memberDumitru.setBirthday(new Date());
		memberDumitru.setFirstName("Dumitru");
		memberDumitru.setLastName("Galit");
		memberDumitru.setZipCode(993456);
		return Arrays.asList(memberSergiu, memberDumitru);
	}

	@Override
	public Member findMemberById(Long id) {
		return new Member();
	}

	@Override
	public Member createMember(Member member) {
		return new Member();
	}

	@Override
	public Long deleteMember(Long id) {
		return 1L;
	}

	@Override
	public Member updateMember(Map<String, String> updates, Long memberId) {
		return new Member();
	}
}
