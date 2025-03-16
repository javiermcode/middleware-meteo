package es.middleware.meteo.temperature.infrastructure.cache;

import es.middleware.meteo.temperature.application.port.input.DeleteCurrentTemperature;
import es.middleware.meteo.temperature.application.port.input.GetCurrentTemperatureCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ForecastCacheEvictAspect {

    static final Logger logger = LoggerFactory.getLogger(ForecastCacheEvictAspect.class);

    private final GetCurrentTemperatureCache getCurrentTemperatureCache;

    private final DeleteCurrentTemperature deleteCurrentTemperature;

    public ForecastCacheEvictAspect(GetCurrentTemperatureCache getCurrentTemperatureCache,
                                    DeleteCurrentTemperature deleteCurrentTemperature) {
        this.getCurrentTemperatureCache = getCurrentTemperatureCache;
        this.deleteCurrentTemperature = deleteCurrentTemperature;
    }

    @Around("@annotation(forecastCacheEvict)")
    public Object forecastCacheEvict(ProceedingJoinPoint joinPoint, ForecastCacheEvict forecastCacheEvict) throws Throwable {

        if(ForecastCacheEnum.TEMPERATURE.value.equalsIgnoreCase(forecastCacheEvict.value())
                && joinPoint.getArgs().length == 2){
            return handleTemperatureCacheEvict(joinPoint, forecastCacheEvict.value());
        }
        return joinPoint.proceed();
    }

    private Object handleTemperatureCacheEvict(ProceedingJoinPoint joinPoint, String cacheName) throws Throwable {
        Object[] args = joinPoint.getArgs();
        double latitude = (double)args[0];
        double longitude = (double)args[1];

        final var currentTemperatureFromCache = getCurrentTemperatureCache.getCurrentTemperatureFromCacheIncludingExpired(latitude, longitude);
        if(currentTemperatureFromCache.isPresent()) {
            deleteCurrentTemperature.deleteCurrentTemperatureById(latitude, longitude);
            logger.info("CACHE forecastCacheEvict [{}] {} -> {}", cacheName, joinPoint.getSignature().getName(), Arrays.toString(args));
            return currentTemperatureFromCache;
        }

        return joinPoint.proceed();
    }
}