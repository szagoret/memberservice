package com.memberservice;

import com.memberservice.entity.Member;
import com.memberservice.utils.MemberTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by szagoret
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberRestServiceIntegrationTest {

    private Member member;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void prepareMember() {
        member = MemberTestUtils.getMember(null);
    }


    @Test
    public void createMember() {
        ResponseEntity<Member> responseEntity =
                restTemplate.postForEntity("/members", member, Member.class);

        Member member = responseEntity.getBody();
        assertThat(HttpStatus.CREATED, is(responseEntity.getStatusCode()));
        assertThat(member, is(notNullValue()));
        assertThat(member.getId(), is(notNullValue()));

    }


    @Test
    public void readMembers() {
        member = restTemplate.postForEntity("/members", member, Member.class).getBody();
        ResponseEntity<Member[]> responseReadEntity = restTemplate.getForEntity("/members", Member[].class);
        Member[] memberList = responseReadEntity.getBody();
        assertThat(HttpStatus.OK, is(responseReadEntity.getStatusCode()));
        assertThat(memberList.length, is(not(0)));
    }

    @Test
    public void readMember() {
        member = restTemplate.postForEntity("/members", member, Member.class).getBody();

        ResponseEntity<Member> responseReadEntity = restTemplate.getForEntity("/members/{:memberId}", Member.class, member.getId());
        assertThat(HttpStatus.OK, is(responseReadEntity.getStatusCode()));
        assertThat(member.getId(), is(responseReadEntity.getBody().getId()));
    }

    @Test
    public void readUnknownMember() {
        ResponseEntity<Void> responseReadEntity = restTemplate.getForEntity("/members/{:memberId}", Void.class, 101);
        assertThat(responseReadEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void deleteMember() {
        member = restTemplate.postForEntity("/members", member, Member.class).getBody();

        restTemplate.delete("/members/{:memberId}", member.getId());

        ResponseEntity<Void> responseReadEntity = restTemplate.getForEntity("/members/{:memberId}", Void.class, member.getId());
        assertThat(responseReadEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void updateMember() {
        member = restTemplate.postForEntity("/members", member, Member.class).getBody();
        assertThat(member.getFirstName(), is(notNullValue()));
        member.setFirstName(null);
        Member resultMember = restTemplate.postForEntity("/members", member, Member.class).getBody();
        assertThat(resultMember.getFirstName(), is(nullValue()));
    }

    @Test
    public void updatePartialMember() {
        String lastName = "ChangedLastName";
        member = restTemplate.postForEntity("/members", member, Member.class).getBody();
        assertThat(member.getLastName(), is(not(lastName)));
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("lastName", lastName);

        Member updatedMember = restTemplate.postForObject("/members/" + member.getId() + "/?_method=patch", updates, Member.class);
        assertThat(updatedMember.getLastName(), is(lastName));
    }

}
