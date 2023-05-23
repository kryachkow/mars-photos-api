package com.example.marsphotosapi.task;

import java.util.concurrent.TimeUnit;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheClearingTask {

  @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 10)
  @CacheEvict(cacheNames = "largest-picture", allEntries = true)
  public void evictLargestPictureCache(){
  }

}
