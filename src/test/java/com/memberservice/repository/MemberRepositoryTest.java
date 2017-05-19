package com.memberservice.repository;

import com.memberservice.entity.Member;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * Created by szagoret
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest {

    private Member member;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;


    @Before
    public void insertMember() {
        member = new Member();
        member.setFirstName("John");
        member = entityManager.persist(member);
    }

    @Test
    public void testFindMemberById() {
        Optional<Member> resultMember = memberRepository.findById(member.getId());

        Assert.assertTrue(resultMember.isPresent());
        Assert.assertEquals(member.getId(), resultMember.get().getId());
    }

    @Test
    public void testDeleteMemberById() {
        int deleted = memberRepository.deleteMemberById(member.getId());

        Assert.assertEquals(deleted, 1);
    }

    @Test
    public void testDeleteNotExistingMember() {
        int deleted = memberRepository.deleteMemberById(member.getId() + 1);
        Assert.assertEquals(deleted, 0);
    }
}
