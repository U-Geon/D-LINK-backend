package com.alpha.DLINK.domain.recommendHistory.service;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.beverage.repository.BeverageRepository;
import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.domain.member.repository.MemberRepository;
import com.alpha.DLINK.domain.recommendHistory.domain.RecommendHistory;
import com.alpha.DLINK.domain.recommendHistory.dto.RecommendHistoryResponseDTO;
import com.alpha.DLINK.domain.recommendHistory.repository.RecommendHistoryRepository;
import com.alpha.DLINK.prompt.dto.HomeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendHistoryService {

    /**
     * 추천하는 대로 히스토리 때려 박기 => 사용자 별 추천 히스토리 api
     * 이걸로 할래요 기능 (isLike) 추가 => 이걸로 할래요 히스토리 api
     */

    private final MemberRepository memberRepository;
    private final BeverageRepository beverageRepository;
    private final RecommendHistoryRepository recommendHistoryRepository;

    // 현재 날짜 기준 지난 일주일 간의 히스토리 모음
    @Transactional(readOnly = true)
    public List<RecommendHistoryResponseDTO> historyByWeek(Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfToday = now.with(LocalTime.MIN); // 오늘 자정
        LocalDateTime oneWeekAgo = startOfToday.minusWeeks(1); // 일주일 전 자정

        return recommendHistoryRepository.findRecommendHistoriesFromLastWeek(memberId, oneWeekAgo, now)
                .stream().map(RecommendHistoryResponseDTO::new).toList();
    }

    // 홈 화면 (이걸로 할래요 히스토리 페이징)
    @Transactional(readOnly = true)
    public HomeResponseDTO home(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Pageable pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<RecommendHistory> content = recommendHistoryRepository.findByMemberIdAndIsRecommendedTrue(memberId, pageable).getContent();
        return new HomeResponseDTO(member.getNickname(), content);
    }

    // 이걸로 할래요
    public void recommend(Long memberId, Long beverageId, String similarity) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        Beverage beverage = beverageRepository.findById(beverageId).orElseThrow(IllegalArgumentException::new);

        RecommendHistory recommendHistory = RecommendHistory.create(member, beverage);
        recommendHistory.setSimilarity(similarity);
        recommendHistory.setIsRecommended(true);
        recommendHistoryRepository.save(recommendHistory);
    }

    // 추천 받은 음료 중에서 즐겨찾기 설정 + 취소 기능 (과거에 즐겨찾기 했었던 히스토리를 불러올 수 있음)
    public void likeAtMain(Long memberId, Long beverageId) {

        Optional<RecommendHistory> recommendHistoryOptional = recommendHistoryRepository.findCurrentByMemberIdAndBeverageId(memberId, beverageId);

        if (recommendHistoryOptional.isEmpty()) {
            Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
            Beverage beverage = beverageRepository.findById(beverageId).orElseThrow(IllegalArgumentException::new);

            RecommendHistory recommendHistory = RecommendHistory.create(member, beverage);
            recommendHistory.setIsLike(true);
            recommendHistoryRepository.save(recommendHistory);
        } else {
            RecommendHistory recommendHistory = recommendHistoryOptional.get();
            recommendHistory.setIsLike(!recommendHistory.getIsLike());
        }
    }

    // 히스토리 페이지에서 즐겨찾기 설정 + 해제
    public void likeAtHistoryPage(Long historyId) {
        RecommendHistory recommendHistory = recommendHistoryRepository.findById(historyId).orElseThrow(IllegalArgumentException::new);
        recommendHistory.setIsLike(!recommendHistory.getIsLike());
    }
}