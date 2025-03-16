package es.middleware.meteo.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(public * es.middleware.meteo.temperature.infrastructure.inbound.rest.TemperatureController.*(..))")
    private void publicMethodsFromTemperatureRestController() {
    }

    @Around(value = "publicMethodsFromTemperatureRestController()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        logger.info(">>> TemperatureControllerRequest {}() - {}", methodName, Arrays.toString(args));
        Object result = joinPoint.proceed();
        logger.info("<<< TemperatureControllerResponse {}() - {}", methodName, result);
        return result;
    }
}
