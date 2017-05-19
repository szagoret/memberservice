package com.memberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemberServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApplication.class, args);
    }

//    @Bean
//    CommandLineRunner init(MemberRepository memberRepository) {
//        return (evt) -> {
//            Member memberSergiu = new Member();
//            memberSergiu.setBirthday(new Date());
//            memberSergiu.setFirstName("Sergiu");
//            memberSergiu.setLastName("Zagoret");
//            memberSergiu.setZipCode("123456");
//            Member memberDumitru = new Member();
//            memberDumitru.setBirthday(new Date());
//            memberDumitru.setFirstName("Dumitru");
//            memberDumitru.setLastName("Galit");
//            memberDumitru.setZipCode("0993456");
//            memberRepository.save(memberDumitru);
//            memberRepository.save(memberSergiu);
//        };
//    }

}
