package com.alpha.DLINK.domain.recommendHistory.service;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.beverage.repository.BeverageRepository;
import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.domain.member.repository.MemberRepository;
import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import com.alpha.DLINK.domain.recommendHistory.dto.findRecommendHistoryResponseDTO;
import com.alpha.DLINK.domain.recommendHistory.repository.RecommendHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendHistoryService {

    /**
     * 추천하는 대로 히스토리 때려 박기 => 사용자 별 추천 히스토리 api
     * 이걸로 할래요 기능 (isLike) 추가 => 이걸로 할래요 히스토리 api
     *
     */

    private final MemberRepository memberRepository;
    private final BeverageRepository beverageRepository;
    private final RecommendHistoryRepository recommendHistoryRepository;

    // 해당 사용자의 즐겨찾기 히스토리 모음
    @Transactional(readOnly = true)
    public List<findRecommendHistoryResponseDTO> findBeverageByMember(Long memberId) {
        return recommendHistoryRepository.findByMemberIdAndIsLikeTrue(memberId).stream().map(findRecommendHistoryResponseDTO::new).collect(Collectors.toList());
    }

    // 이걸로 할래요 히스토리 모음
    @Transactional(readOnly = true)
    public List<findRecommendHistoryResponseDTO> findByLikeThis(Long memberId) {
        return recommendHistoryRepository.findByMemberIdAndIsRecommendedTrue(memberId).stream().map(findRecommendHistoryResponseDTO::new).collect(Collectors.toList());
    }

    // 이걸로 할래요
    public void recommend(Long memberId, Long beverageId, String similarity) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        Beverage beverage = beverageRepository.findById(beverageId).orElseThrow(IllegalArgumentException::new);

        RecommendHistory recommendHistory = RecommendHistory.create(member, beverage);
        recommendHistory.setIsRecommended(true);
        recommendHistoryRepository.save(recommendHistory);
    }

    // 즐겨찾기 설정 + 취소 기능 -> 프롬프트 입력 후 추천 받은 음료에 대한 즐겨찾기 또는 이걸로 할래요 했던 음료에 대한 즐겨찾기
    public void like(Long memberId, Long beverageId) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        Beverage beverage = beverageRepository.findById(beverageId).orElseThrow(IllegalArgumentException::new);
        Optional<RecommendHistory> byMemberAndBeverage = recommendHistoryRepository.findByMemberAndBeverage(member, beverage);

        if (byMemberAndBeverage.isPresent()) { // 이걸로 할래요 했던 음료
            RecommendHistory recommendHistory = byMemberAndBeverage.get();
            Boolean isLike = recommendHistory.getIsLike();
            recommendHistory.setIsLike(!isLike);
        } else { // 이걸로 할래요 안했던 음료.
            RecommendHistory createHistory = RecommendHistory.create(member, beverage);
            createHistory.setIsLike(true);
            recommendHistoryRepository.save(createHistory);
        }
    }


}