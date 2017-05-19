package com.memberservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memberservice.entity.Member;
import com.memberservice.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * Created by szagoret
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MemberRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberServiceMock;

    @Autowired
    ObjectMapper objectMapper;

    private static final Long MEMBER_ID = Long.valueOf(42);
    private static final String MEMBER_FIRST_NAME = "Mihai";
    private static final String MEMBER_LAST_NAME = "Eminescu";
    private static final String MEMBER_ZIP_CODE = "032451";
    private static final Date MEMBER_BIRTHDAY;

    static {
        Calendar calendar = new GregorianCalendar();
        calendar.set(1850, 0, 15);
        MEMBER_BIRTHDAY = calendar.getTime();
    }

    private Member member;

    @Before
    public void prepareTestMember() throws ParseException {
        member = new Member();
        member.setId(MEMBER_ID);
        member.setBirthday(new Date());
        member.setFirstName(MEMBER_FIRST_NAME);
        member.setLastName(MEMBER_LAST_NAME);
        member.setBirthday(MEMBER_BIRTHDAY);
        member.setZipCode(MEMBER_ZIP_CODE);
    }


    @Test
    public void testReadMembers() throws Exception {
        given(memberServiceMock.findAllMembers()).willReturn(Arrays.asList(member));
        mockMvc.perform(get("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(MEMBER_ID.intValue())))
                .andExpect(jsonPath("$[0].firstName", is(MEMBER_FIRST_NAME)))
                .andExpect(jsonPath("$[0].lastName", is(MEMBER_LAST_NAME)))
                .andExpect(jsonPath("$[0].birthday", is("1850-01-15")))
                .andExpect(jsonPath("$[0].zipCode", is(MEMBER_ZIP_CODE)));
    }


    @Test
    public void testCreateMemberSuccessfully() throws Exception {
        given(memberServiceMock.createMember(new Member())).willReturn(member);

        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new Member()))
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
