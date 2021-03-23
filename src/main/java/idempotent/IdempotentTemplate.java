package idempotent;

import idempotent.delay.DelayQueueManager;
import idempotent.executor.AbstractIdempotentDelayedExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhenghao
 */
@Component
public class IdempotentTemplate {
    @Autowired
    private IdempotentManager idempotentManager;

    private DelayQueueManager DelayQueueManager;

    @Autowired
    public void setDelayQueueManager(DelayQueueManager delayQueueManager) {
        DelayQueueManager = delayQueueManager;
    }

    public Object execute(AbstractIdempotentDelayedExecutor executor) throws Throwable {
        IdempotentInfo idempotentInfo = executor.getIdempotentInfo();
        if (idempotentInfo == null) {
            throw new RuntimeException("幂等信息为空");
        }
        idempotentManager.prepare(idempotentInfo);

        Object result = null;
        try {
            result = executor.execute();
        } catch (Throwable ex) {
            idempotentManager.afterThrowing(idempotentInfo, ex);
            if (idempotentInfo.isDelaySpending() && executor.getCount() <= idempotentInfo.geDelaySpendingMaxCount()) {
                //延迟队列
                DelayQueueManager.offer(executor);
            }
            throw ex;
        }
        idempotentManager.after(idempotentInfo);
        return result;
    }
}
