package com.fiveguys.koguma.member;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberRelationship;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;
import com.fiveguys.koguma.repository.member.MemberRelationshipRepository;
import com.fiveguys.koguma.service.member.MemberRelationshipService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.member.MemberServiceImpl;
import com.fiveguys.koguma.service.member.MemberRelationshipServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest

public class MemberRelationshipApplicationTests {

    @Autowired
    private MemberRelationshipService memberRelationshipService;

    @Autowired
    private MemberRelationshipRepository memberRelationshipRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }

    @Test
    @DisplayName("차단 추가 테스트")
    @Transactional
    void addBlockTest() {
        Member sourceMember = addMember("sourceUser");
        Member targetMember = addMember("targetUser");

        MemberRelationshipDTO relationshipDTO = createMemberRelationshipDTO(
                sourceMember, targetMember, "Blocking content");

        memberRelationshipService.addBlock(relationshipDTO, sourceMember, targetMember, "Blocking content");

        MemberRelationshipDTO addedBlock = memberRelationshipService.getBlock(relationshipDTO.getId());

        assertAll(
                () -> assertEquals(sourceMember.getId(), addedBlock.getSourceMemberId().getId()),
                () -> assertEquals(targetMember.getId(), addedBlock.getTargetMemberId().getId()),
                () -> assertEquals("Blocking content", addedBlock.getContent()),
                () -> assertEquals(MemberRelationshipType.BLOCK, addedBlock.getMemberRelationshipType())
        );
    }


    @Test
    @DisplayName("차단 삭제 테스트")
    @Transactional
    void deleteBlockTest() {
        Member sourceMember = addMember("sourceUser");
        Member targetMember = addMember("targetUser");

        MemberRelationshipDTO relationshipDTO = createMemberRelationshipDTO(
                sourceMember, targetMember, "Blocking content");

        memberRelationshipService.addBlock(relationshipDTO, sourceMember, targetMember, "Blocking content");

        memberRelationshipService.deleteBlock(relationshipDTO);

        MemberRelationshipDTO deletedBlock = memberRelationshipService.getBlock(relationshipDTO.getId());

        assertNull(deletedBlock.getSourceMemberId());
        assertNull(deletedBlock.getTargetMemberId());
        assertNull(deletedBlock.getContent());
        assertNull(deletedBlock.getMemberRelationshipType());
    }

    @Test
    @DisplayName("차단 목록 테스트")
    @Transactional
    void listBlockTest() {
        Member sourceMember = addMember("sourceUser");
        Member targetMember = addMember("targetUser");

        MemberRelationshipDTO relationshipDTO1 = createMemberRelationshipDTO(
                sourceMember, targetMember, "Blocking content 1");
        MemberRelationshipDTO relationshipDTO2 = createMemberRelationshipDTO(
                sourceMember, targetMember, "Blocking content 2");

        memberRelationshipService.addBlock(relationshipDTO1, sourceMember, targetMember, "Blocking content 1");
        memberRelationshipService.addBlock(relationshipDTO2, sourceMember, targetMember, "Blocking content 2");

        List<MemberRelationshipDTO> blockList = memberRelationshipService.listBlock();

        assertEquals(2, blockList.size());
    }

    // Helper methods


    private MemberRelationshipDTO createMemberRelationshipDTO(Member sourceMember, Member targetMember, String content) {
        return MemberRelationshipDTO.builder()
                .sourceMemberId(sourceMember)
                .targetMemberId(targetMember)
                .content(content)
                .memberRelationshipType(MemberRelationshipType.BLOCK)
                .build();
    }

    private Member addMember(String nickname) {
        MemberDTO memberDTO = MemberDTO.builder()
                .nickname(nickname)
                .pw("password")
                .phone("010-1234-5678")
                .score(36.5F)
                .email(nickname + "@example.com")
                .roleFlag(false)
                .socialFlag(false)
                .build();
        memberService.addMember(memberDTO, nickname, "password", "010-1234-5678", 36.5F, nickname + "@example.com", false, false);
        return null;
    }
}