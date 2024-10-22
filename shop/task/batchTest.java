package co.kr.shop.task;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
@Component
@Log4j
public class batchTest {
	//@Scheduled(cron = "0 * * * * *")
	public void testMethod() throws Exception {
		
		//log.warn("배치 실행 테스트");
	}
}
