package com.memberservice.controller;

import com.memberservice.entity.Member;
import com.memberservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

/**
 * Created by szagoret
 */
@RestController
@RequestMapping(value = "/members")
public class MemberRestController {

    @Autowired
    private MemberService memberService;

    /**
     * Find all members
     */
    @GetMapping
    public ResponseEntity<Collection<Member>> findAllMembers() {
        Collection<Member> foundMembers = memberService.findAllMembers();
        return ResponseEntity.ok(foundMembers);
    }

    /**
     * Find member by id
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<Member> findMember(@PathVariable Long memberId) {
        Member foundMember = memberService.findMemberById(memberId);
        return ResponseEntity.ok(foundMember);
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
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok("The member with id: " + memberId + " was deleted with success.");

    }

    /**
     * Update specific fields on selected member
     */
    @PatchMapping("/{memberId}")
    public ResponseEntity<Member> partialUpdateMember(@RequestBody Map<String, String> updates, @PathVariable Long memberId) {
        Member updatedMember = memberService.updateMember(updates, memberId);
        return ResponseEntity.ok(updatedMember);
    }

}
