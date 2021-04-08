package idempotent.businesspolocy;

import idempotent.IdempotentInfo;
import idempotent.IdempotentManager;
import idempotent.executor.DelayedCountSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhenghao
 */
@Component
public class BusinessErrorHandler {
    @Autowired
    private IdempotentManager idempotentManager;

    @Autowired
    private DelayQueueManager delayQueueManager;

    public void handle(IdempotentInfo idempotentInfo, DelayedCountSupport executor) {

        BusinessErrorPolicyEnum businessErrorPolicyEnum = idempotentInfo.getBusinessErrorPolicyEnum();

        switch (businessErrorPolicyEnum){
            case delete:
                idempotentManager.delete(idempotentInfo);
                break;
            case save:
                idempotentManager.after(idempotentInfo);
                break;
            case delete_and_retry:
                idempotentManager.delete(idempotentInfo);
                if (executor.getCount() <= idempotentInfo.geDelaySpendingMaxCount()) {
                    //延迟队列
                    delayQueueManager.offer(executor);
                }
                break;
            default:
        }


    }

}
