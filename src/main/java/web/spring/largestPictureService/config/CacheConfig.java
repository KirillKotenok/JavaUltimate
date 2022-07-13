package web.spring.largestPictureService.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Log4j2
@Configuration
public class CacheConfig {

    @Scheduled(cron = "0 0 5 * * *")
    @CacheEvict(value = "largestPicture", allEntries = true)
    public void clearCache() {
        log.warn("Clear NASA picture cache!");
    }
}
