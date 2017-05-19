package com.memberservice;

import com.memberservice.entity.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by szagoret
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberRestServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createMember() {
        ResponseEntity<Member> responseEntity =
                restTemplate.postForEntity("/members", new Member(), Member.class);

        Member member = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull("Member creation has failed.", member);

    }


    @Test
    public void readMembers() {
        ResponseEntity<Member[]> responseEntity = restTemplate.getForEntity("/members", Member[].class);
        Member[] memberList = responseEntity.getBody();
    }

}
