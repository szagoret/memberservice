package com.memberservice.service;

import com.memberservice.entity.Member;
import com.memberservice.exceptions.DateFormatException;
import com.memberservice.exceptions.MemberNotFoundException;
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

    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Collection<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member findMemberById(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return member.orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    @Override
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public int deleteMember(Long memberId) {
        int deleted = memberRepository.deleteMemberById(memberId);
        if (deleted == 0) {
            throw new MemberNotFoundException("Member with ID: " + memberId + " does not exists.");
        }
        return deleted;
    }

    @Override
    public Member updateMember(Map<String, String> updates, Long memberId) {

        // get selected member or throw an error
        final Member memberToUpdate = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));

        /**
         * 1. Iterate over all fields that are annotated with @Column and exists in updates Map
         * 2. Apply updates on selected fields
         */
        ReflectionUtils.doWithFields(Member.class, objField -> applyUpdates(updates, memberToUpdate, objField),
                filterField -> filterField.isAnnotationPresent(Column.class) && updates.containsKey(filterField.getName()));
        return memberRepository.save(memberToUpdate);
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
                throw new DateFormatException(updates.get(objFieldName), dateFormat);
            }
        } else {
            ReflectionUtils.setField(fieldRef, memberRef, updates.get(objFieldName));
        }
    }

}
