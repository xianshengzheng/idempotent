package idempotent.executor;


import idempotent.Idempotent;
import idempotent.IdempotentInfo;
import idempotent.spel.AspectSupportUtils;

import java.lang.reflect.Method;

/**
 * @author zhenghao
 */
public abstract class AbstractIdempotentDelayedExecutor extends DelayedCountSupport implements IdempotentExecutor {

    private IdempotentInfo idempotentInfo;

    public AbstractIdempotentDelayedExecutor(Idempotent idempotentAnnotation, Method method, Object[] args) {
        this.idempotentInfo = IdempotentInfo.IdempotentInfoBuilder
                .builder()
                .id(idempotentAnnotation.prefix().concat(AspectSupportUtils.parseExpression(idempotentAnnotation.id(),method,args)))
                .maxExecutionTime(idempotentAnnotation.maxExecutionTime())
                .duration(idempotentAnnotation.duration())
                .delaySpending(idempotentAnnotation.failureRetry())
                .delaySpendingMaxCount(idempotentAnnotation.failureRetryMaxCount())
                .saveResult(idempotentAnnotation.saveResult())
                .build();
    }

    public abstract Object doExecute()throws Throwable;

    @Override
    public Object execute() throws Throwable{
        super.addCount();
        return this.doExecute();
    }

    @Override
    public IdempotentInfo getIdempotentInfo() {
        return idempotentInfo;
    }

}
