package idempotent;

import com.alibaba.fastjson.JSON;
import idempotent.businesspolocy.BusinessErrorHandler;
import idempotent.executor.DelayedCountSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhenghao
 */
@Component
public class IdempotentTemplate {
    @Autowired
    private IdempotentManager idempotentManager;

    @Autowired
    private BusinessErrorHandler businessErrorHandler;

    public Object execute(DelayedCountSupport executor) throws Throwable {
        IdempotentInfo idempotentInfo = executor.getIdempotentInfo();
        if (idempotentInfo == null) {
            throw new RuntimeException("幂等信息为空");
        }

        idempotentManager.prepare(idempotentInfo);
        Object result = null;
        try {
            result = executor.execute();
        } catch (Throwable ex) {
            businessErrorHandler.handle(idempotentInfo, executor);
            throw ex;
        }
        if (idempotentInfo.isSaveResult()) {
            idempotentInfo.setResultJson(JSON.toJSONString(result));
        }
        idempotentManager.after(idempotentInfo);
        return result;
    }
}
