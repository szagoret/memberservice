package com.memberservice.service;

import com.memberservice.entity.Member;
import com.memberservice.exceptions.DateFormatException;
import com.memberservice.exceptions.MemberNotFoundException;
import com.memberservice.repository.MemberRepository;
import com.memberservice.utils.MemberTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

/**
 * Created by szagoret
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MemberServiceTest {

    private static final Long MEMBER_ID = 42L;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    MemberRepository memberRepositoryMock;
    private Member member;
    @Autowired
    private MemberService memberService;

    @Before
    public void prepareTestMember() {
        member = MemberTestUtils.getMember(MEMBER_ID);
    }

    @Test(expected = MemberNotFoundException.class)
    public void testUpdateNotExistingMember() {
        given(memberRepositoryMock.save(member)).willReturn(member);
        given(memberRepositoryMock.findById(MEMBER_ID)).willReturn(Optional.empty());

        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("lastName", "Drescher");

        memberService.updateMember(updates, MEMBER_ID);
    }

    @Test(expected = DateFormatException.class)
    public void testUpdateMemberWrongDate() {
        given(memberRepositoryMock.save(member)).willReturn(member);
        given(memberRepositoryMock.findById(MEMBER_ID)).willReturn(Optional.of(member));

        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("birthday", "@@##$$s");

        memberService.updateMember(updates, MEMBER_ID);
    }

}
