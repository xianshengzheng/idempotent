package idempotent;

import idempotent.storage.IdempotenceStorage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhenghao
 */
@Component
public class IdempotentManager {
    @Resource(name="redisClusterIdempotenceStorage")
    private IdempotenceStorage idempotenceStorage;


    public void prepare(IdempotentInfo idempotentInfo) {
        String id = idempotentInfo.getId();
        if (!idempotenceStorage.saveIfAbsent(id, idempotentInfo)){
            throw new RejectedException(idempotentInfo.getId());
        }
    }

    public void after(IdempotentInfo idempotentInfo) {
        idempotenceStorage.updateAfter(idempotentInfo.getId(), idempotentInfo);
    }

    public void afterThrowing(IdempotentInfo idempotentInfo, Throwable ex) {
            idempotenceStorage.delete(idempotentInfo.getId());
    }

    void delete(String id){
        idempotenceStorage.delete(id);
    }
}
