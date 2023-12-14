package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubJoinRequestDTO;
import com.fiveguys.koguma.data.dto.ClubMemberDTO;
import com.fiveguys.koguma.data.dto.CreateClubDTO;

import java.util.List;

public interface ClubService {

    public Long addClub(CreateClubDTO createClubDTO, Long memberId);

    public List<ClubDTO> listClub(Double latitude, Double longitude);

    public List<ClubDTO> listClub();

    public List<ClubDTO> listMyClub(Long memberId);

    public List<ClubDTO> listClubByCategory(Long categoryId);

    public ClubDTO getClub(Long clubId);

    public void updateClub(ClubDTO clubDTO);

    public Long addJoinRequestClub(ClubJoinRequestDTO clubJoinRequestDTO, Long memberId);

    public ClubJoinRequestDTO getClubJoinRequest(Long clubJoinRequestId);

    public List<ClubJoinRequestDTO> listClubJoinRequest(Long clubId);

    public Long acceptJoinRequest(Long clubJoinRequestId);

    public void rejectJoinRequest(Long cmJoinRequestId);

    public void deleteByMemberId(Long cmJoinRequestId);

    public Long addClubMember(ClubMemberDTO clubMemberDTO);

    public ClubMemberDTO getClubMember(Long clubId, Long memberId);

    public List<ClubMemberDTO> listClubMember(Long clubId);

    public void updateClubMember(ClubMemberDTO clubMemberDTO);

    public void changeClubLeader(Long currentLeaderId, Long newLeaderId);

    public ClubMemberDTO checkJoinClub(Long clubId, Long memberId);

}
