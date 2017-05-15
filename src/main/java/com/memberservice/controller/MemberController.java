package com.memberservice.controller;

import com.memberservice.entity.Member;
import com.memberservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

/**
 * Created by szagoret
 */
@RestController
@RequestMapping("/members")
public class MemberController {

	@Autowired private MemberService memberService;

	/**
	 * Find all members
	 */
	@GetMapping
	public Collection<Member> findAllMembers() {
		return memberService.findAllMembers();
	}

	/**
	 * Find member by id
	 */
	@GetMapping("/{memberId}")
	public Member findMember(@PathVariable Long memberId) {
		return memberService.findMemberById(memberId);
	}

	/**
	 * Create new member
	 */
	@PostMapping
	public Member createMember(@RequestBody Member member) {
		return memberService.createMember(member);

	}

	/**
	 * Delete selected member
	 */
	@DeleteMapping("/{memberId}")
	public ResponseEntity<?> deleteMember(@PathVariable Long memberId) {
		memberService.deleteMember(memberId);
		return ResponseEntity.ok("");
	}

	/**
	 * Update specific fields on selected member
	 */
	@PatchMapping("/{memberId}")
	public Member updateMember(@RequestBody Map<String, String> updates, @PathVariable Long memberId) {
		return memberService.updateMember(updates, memberId);
	}

}
