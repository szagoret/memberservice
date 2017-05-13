package com.memberservice.service;

import com.memberservice.entity.Member;
import com.memberservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Created by szagoret on 13.05.2017.
 */
@RestController
@RequestMapping("/members")
public class MemberRestService {

	private final MemberRepository memberRepository;

	@Autowired
	public MemberRestService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<Member> readAllMembers(){
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
}
