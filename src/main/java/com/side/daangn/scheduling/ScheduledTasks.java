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

	private final RedisUtil redisUtil;

	private final SearchService searchService;

    public ScheduledTasks(RedisUtil redisUtil, SearchService searchService) {
        this.redisUtil = redisUtil;
        this.searchService = searchService;
    }

    //매일 자정에 함수 실행
    //검색어 DB에 업데이트.
	@Scheduled(cron = "0 38 0 * * *", zone = "Asia/Seoul")
	public void updateSearchPerDay() {
        log.info("Cron Check{}", dateFormat.format(new Date()));
		Set<String> keys = redisUtil.getKeysByPattern("search_*");
		if (keys != null && !keys.isEmpty()){
			for(String key : keys){
				String search = key.split("_")[1];
				String type = key.split("_")[2];
				long count = Long.parseLong(redisUtil.getToken(key));
				Search searchEntity = searchService.findBySearch(search);
				if(searchEntity!= null){
					searchService.searchPlus(search, count);
				}else{
					searchEntity = new Search();
					searchEntity.setSearch(search);
					searchEntity.setType(Integer.parseInt(type));
					searchEntity.setCount(count);
					searchService.save(searchEntity);
				}
			}
		}
		redisUtil.deleteKeys(keys);
	}
//    @Scheduled(fixedRate = 5000)
//	public void reportCurrentTime() {
//		log.info("The time is now {}", dateFormat.format(new Date()));
//	}
}
