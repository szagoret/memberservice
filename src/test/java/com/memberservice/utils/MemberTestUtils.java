package com.memberservice.utils;

import com.memberservice.entity.Member;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by szagoret
 */
public abstract class MemberTestUtils {


    public static Member getMember(Long memberId) {
        Member member = new Member();
        member.setId(memberId);
        member.setFirstName("Leon");
        member.setLastName("Kluge");
        member.setZipCode("01317");
        member.setBirthday(getDate(1970, 0, 15));
        return member;
    }


    private static Date getDate(int year, int month, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
