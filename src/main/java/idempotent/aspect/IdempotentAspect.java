package idempotent.aspect;


import idempotent.Idempotent;
import idempotent.IdempotentTemplate;
import idempotent.RejectedException;
import idempotent.executor.AbstractIdempotentDelayedExecutor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zhenghao
 */
@Aspect
@Component
public class IdempotentAspect {
    static Logger logger = LoggerFactory.getLogger(IdempotentAspect.class);

    @Autowired
    private IdempotentTemplate idempotentTemplate;

    @Pointcut("@annotation(idempotent.Idempotent)")
    private void IdempotentAspect() {
    }


    @Around("IdempotentAspect()")
    public Object IdempotentStart(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Object[] args = pjp.getArgs();
        Method method = signature.getMethod();
        Idempotent idempotentAnnotation = method.getAnnotation(Idempotent.class);

        try {
            return idempotentTemplate.execute(new AbstractIdempotentDelayedExecutor(idempotentAnnotation, method, args) {
                @Override
                public Object doExecute() throws Throwable {
                    Object[] args = pjp.getArgs();
                    return pjp.proceed(args);
                }
            });

        } catch (RejectedException t) {
            logger.info("拒绝执行方法[{}],幂等操作id[{}]", method.getName(), t.getId());
            //todo 拒绝后，默认抛出Rejected异常，应该返回上一次成功数据
            throw t;
        }
    }
}
