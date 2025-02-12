package com.side.daangn.scheduling;

import com.side.daangn.entitiy.product.Search;
import com.side.daangn.service.service.product.SearchService;
import com.side.daangn.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Component

public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	private final SearchService searchService;

    public ScheduledTasks(SearchService searchService) {
        this.searchService = searchService;
    }

    //매일 자정에 함수 실행
    //검색어 DB에 업데이트.
	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
	public void updateSearchPerDay() {
        log.info("Cron Check{}", dateFormat.format(new Date()));
		searchService.saveSearchToDB();
	}
//    @Scheduled(fixedRate = 5000)
//	public void reportCurrentTime() {
//		log.info("The time is now {}", dateFormat.format(new Date()));
//	}
}
