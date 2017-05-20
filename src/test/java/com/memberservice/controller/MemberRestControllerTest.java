package com.memberservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memberservice.entity.Member;
import com.memberservice.exceptions.DateFormatException;
import com.memberservice.exceptions.MemberNotFoundException;
import com.memberservice.service.MemberService;
import com.memberservice.utils.MemberTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by szagoret
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MemberRestController.class)
public class MemberRestControllerTest {

    private static final Long MEMBER_ID = 42L;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    MemberService memberServiceMock;
    @Autowired
    ObjectMapper objectMapper;
    Member member;
    private MediaType jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));

    @Before
    public void prepareTestMember() throws ParseException {
        member = MemberTestUtils.getMember(MEMBER_ID);
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
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(MEMBER_ID.intValue())))
                .andExpect(jsonPath("$[0].firstName", is("Leon")))
                .andExpect(jsonPath("$[0].lastName", is("Kluge")))
                .andExpect(jsonPath("$[0].birthday", is("1970-01-15")))
                .andExpect(jsonPath("$[0].zipCode", is("01317")));
    }


    @Test
    public void testCreateMemberFromJSON() throws Exception {
        given(memberServiceMock.createMember(member)).willReturn(member);

        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(member))
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    public void testCreateMemberFromXML() throws Exception {
        given(memberServiceMock.createMember(member)).willReturn(member);

        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content("<member>\n" +
                        "<firstName>Jean</firstName>\n" +
                        "</member>")
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testReadExistingMember() throws Exception {
        given(memberServiceMock.findMemberById(MEMBER_ID)).willReturn(member);

        mockMvc.perform(get("/members/" + MEMBER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(MEMBER_ID.intValue())));
    }

    @Test
    public void testReadNonExistingMember() throws Exception {
        given(memberServiceMock.findMemberById(MEMBER_ID)).willThrow(new MemberNotFoundException(MEMBER_ID));
        mockMvc.perform(get("/members/" + MEMBER_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateExistingMember() throws Exception {
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("lastName", "Drescher");
        given(memberServiceMock.updateMember(updates, MEMBER_ID)).willReturn(member);
        mockMvc.perform(patch("/members/" + MEMBER_ID)
                .contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"Drescher\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateNotExistingMember() throws Exception {
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("lastName", "Drescher");
        given(memberServiceMock.updateMember(updates, MEMBER_ID)).willThrow(new MemberNotFoundException(MEMBER_ID));

        mockMvc.perform(patch("/members/" + MEMBER_ID)
                .contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"Drescher\"}"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateMemberWithWrongDateFormat() throws Exception {
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("birthday", "f2001l");
        given(memberServiceMock.updateMember(updates, MEMBER_ID)).willThrow(new DateFormatException("f2001l", "yyyy-MM-dd"));

        mockMvc.perform(patch("/members/" + MEMBER_ID)
                .contentType(MediaType.APPLICATION_JSON).content("{\"birthday\": \"f2001l\"}"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
