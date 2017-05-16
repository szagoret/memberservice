package com.memberservice.service;

import com.memberservice.entity.Member;
import com.memberservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * Created by szagoret
 */
@Service
public class MemberServiceImpl implements MemberService {

	@Value("${spring.jackson.date-format}") private String dateFormat;

	@Autowired private MemberRepository memberRepository;

	@Override
	public Collection<Member> findAllMembers() {
		return memberRepository.findAll();
	}

	@Override
	public Optional<Member> findMemberById(Long id) {
		return memberRepository.findById(id);
	}

	@Override
	public Member createMember(Member member) {
		return memberRepository.save(member);
	}

	@Override
	public int deleteMember(Long id) {
		return memberRepository.deleteMemberById(id);
	}

	@Override
	public Member updateMember(Map<String, String> updates, Long memberId) {

		// Get only reference of selected member
		final Member memberRef = memberRepository.findOne(memberId);

		/**
		 * 1. Iterate over all fields that are annotated with @Column and exists in updates Map
		 * 2. Apply updates on selected fields
		 */
		ReflectionUtils.doWithFields(Member.class, objField -> applyUpdates(updates, memberRef, objField),
				filterField -> filterField.isAnnotationPresent(Column.class) && updates.containsKey(filterField.getName()));
		return memberRepository.save(memberRef);
	}

	private void applyUpdates(final Map<String, String> updates, final Member memberRef, final Field objField) {
		String objFieldName = objField.getName();
		Field fieldRef = ReflectionUtils.findField(memberRef.getClass(), objFieldName);
		fieldRef.setAccessible(true);

		// if field is of Date type, then parse string and create Date object
		if (fieldRef.getType().isAssignableFrom(Date.class)) {
			try {
				ReflectionUtils
						.setField(fieldRef, memberRef, new SimpleDateFormat(dateFormat).parse(updates.get(objFieldName)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			ReflectionUtils.setField(fieldRef, memberRef, updates.get(objFieldName));
		}
	}

}
