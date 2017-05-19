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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by szagoret
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testFindMemberById() {
        Member member = new Member();
        member.setFirstName("John");
        member = entityManager.persist(member);

        Optional<Member> resultMember = memberRepository.findById(member.getId());

        Assert.assertTrue(resultMember.isPresent());
        Assert.assertEquals(member.getId(), resultMember.get().getId());
    }
}
