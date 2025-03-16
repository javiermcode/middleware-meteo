package es.middleware.meteo.common.cache;

import es.middleware.meteo.temperature.application.port.input.GetCurrentTemperatureCache;
import es.middleware.meteo.temperature.application.port.input.SaveCurrentTemperature;
import es.middleware.meteo.temperature.domain.model.Temperature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
public class ForecastCacheableAspect {

    static final Logger logger = LoggerFactory.getLogger(ForecastCacheableAspect.class);

    private final GetCurrentTemperatureCache getCurrentTemperatureCache;

    private final SaveCurrentTemperature saveCurrentTemperature;

    public ForecastCacheableAspect(GetCurrentTemperatureCache getCurrentTemperatureCache, SaveCurrentTemperature saveCurrentTemperature) {
        this.getCurrentTemperatureCache = getCurrentTemperatureCache;
        this.saveCurrentTemperature = saveCurrentTemperature;
    }

    @Around("@annotation(forecastCacheable)")
    public Object forecastCacheable(ProceedingJoinPoint joinPoint, ForecastCacheable forecastCacheable) throws Throwable {

        if(ForecastCacheEnum.TEMPERATURE.value.equalsIgnoreCase(forecastCacheable.value())
                && joinPoint.getArgs().length == 2){
            return handleForecastTemperatureCacheable(joinPoint, forecastCacheable.value());
        }
        return joinPoint.proceed();
    }

    private Object handleForecastTemperatureCacheable(ProceedingJoinPoint joinPoint, String cacheName) throws Throwable {
        Object[] args = joinPoint.getArgs();
        double latitude = (double)args[0];
        double longitude = (double)args[1];

        final var currentTemperatureFromCache = getCurrentTemperatureCache.getCurrentTemperatureFromCache(latitude, longitude);
        if(currentTemperatureFromCache.isPresent()) {
            logger.info("CACHE forecastCacheGet [{}] {} -> {}", cacheName, joinPoint.getSignature().getName(), Arrays.toString(args));
            return currentTemperatureFromCache;
        }

        Object result = joinPoint.proceed();
        Optional<Temperature> optionalResult = (Optional<Temperature>) result;
        optionalResult.ifPresent(value -> {
            saveCurrentTemperature.saveCurrentTemperature(latitude, longitude,  value);
            logger.info("CACHE forecastCachePut [{}] {} -> {}", cacheName, joinPoint.getSignature().getName(), Arrays.toString(args));
        });
       return result;
    }
}