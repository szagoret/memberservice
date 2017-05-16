package com.memberservice.controller;

import com.memberservice.entity.Member;
import com.memberservice.exceptions.MemberNotFoundException;
import com.memberservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

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
		Optional<Member> member = memberService.findMemberById(memberId);
		if (!member.isPresent()) {
			throw new MemberNotFoundException(memberId);
		}
		return member.get();
	}

	/**
	 * Create a new member or update/overwrite existing
	 */
	@PostMapping
	public ResponseEntity<Member> createOrUpdateMember(@RequestBody Member member) {
		Member createdMember = memberService.createMember(member);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);

	}

	/**
	 * Delete selected member
	 */
	@DeleteMapping("/{memberId}")
	public ResponseEntity<?> deleteMember(@PathVariable Long memberId) {
		int count = memberService.deleteMember(memberId);
		if (count > 0) {
			return ResponseEntity.ok("The member with id: " + memberId + "was deleted with success");
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * Update specific fields on selected member
	 */
	@PatchMapping("/{memberId}")
	public Member partialUpdateMember(@RequestBody Map<String, String> updates, @PathVariable Long memberId) {
		return memberService.updateMember(updates, memberId);
	}

}
