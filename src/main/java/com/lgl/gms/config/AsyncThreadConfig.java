package com.lgl.gms.config;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 비동기 처리에 대한 설정
 */
@Configuration
@EnableAsync
public class AsyncThreadConfig implements AsyncConfigurer {

	@Value("${async-thread.core-pool-size}")
	int aCorePoolSize;

	@Value("${async-thread.max-pool-size}")
	int aMaxPoolSize;

	@Value("${async-thread.queue-capacity}")
	int aQueueCapacity;

	/**
	 * Max 쓰레드가 모두 돌고있는 도중 추가 요청이 들어오게 되면
	 * QueueCapacity에서 설정한 사이즈 만큼 대기열로 들어가 처리를 기다리고,
	 * 돌고 있는 쓰레드가 종료되면 순차적으로 처리된다.
	 * 쓰레드와 큐가 모두 꽉차게 되면 Exception이 발생한다.
	 */

	@Bean(name = "ASyncExecutor")
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		/** 기본 쓰레드 사이즈 */
		taskExecutor.setCorePoolSize(aCorePoolSize);
		/** 최대 쓰레드 사이즈 */
		taskExecutor.setMaxPoolSize(aMaxPoolSize);
		/** Max쓰레드가 동작하는 경우 대기하는 queue 사이즈 */
		taskExecutor.setQueueCapacity(aQueueCapacity);
		taskExecutor.setThreadNamePrefix("ASyncExecutor-");
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncExceptionHandler();
	}
}
