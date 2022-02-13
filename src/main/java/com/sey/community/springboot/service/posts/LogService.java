package com.sey.community.springboot.service.posts;

import com.sey.community.springboot.domain.posts.log.Log;
import com.sey.community.springboot.domain.posts.log.LogRepository;
import com.sey.community.springboot.web.dto.log.LogSaveRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class LogService {
    private final LogRepository logRepository;

    @Transactional
    public Long save(LogSaveRequestDTO logSaveRequestDTO) {
        return logRepository.save(logSaveRequestDTO.toEntity()).getId();
    }

    // 업데이트 하지 않고 쌓기만 함

    @Transactional
    public Long getViewCountByBoardNameAndArticleId(String boardName, Long articleId) {
        Map<Long, List<Log>> filteredMap = logRepository.getListGroupByBoardNameAndArticleId(boardName.toLowerCase(), articleId).stream()
                .collect(Collectors.groupingBy(Log::getUserId, HashMap::new, toList()));
        Map<String, List<Log>> filteredMapOfGuest = logRepository.getListGroupByBoardNameAndArticleIdOfGuest(boardName.toLowerCase(), articleId).stream()
                .collect(Collectors.groupingBy(Log::getIpAddress, HashMap::new, toList()));

        // Log::getUserId
        // x -> x.getUserId()

        System.out.println("filteredMap: " + filteredMap);
        System.out.println("filteredMapOfGuest: " + filteredMapOfGuest);
        return (long) (filteredMap.size() + filteredMapOfGuest.size());

    }
}