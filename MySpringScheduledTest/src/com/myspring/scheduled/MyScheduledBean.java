package com.myspring.scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import com.myspring.anno.Component;
import com.myspring.anno.Scheduled;

@Component
public class MyScheduledBean {

	@Scheduled(initialDelay=1000,period=1000,unit=TimeUnit.MILLISECONDS)
	public static void timeReport() {
		System.out.println("定时任务为您报时："+LocalDateTime.now());
	}
}
