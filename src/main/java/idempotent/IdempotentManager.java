package idempotent;

import idempotent.storage.IdempotenceStorage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhenghao
 */
@Component
public class IdempotentManager {

    @Resource(name = "localTestIdempotenceStorage")
    private IdempotenceStorage idempotenceStorage;


    public void prepare(IdempotentInfo idempotentInfo) {
        String id = idempotentInfo.getId();
        if (!idempotenceStorage.saveIfAbsent(id, idempotentInfo)){
            throw new RejectedException(id, idempotenceStorage.getResultString(id));
        }
    }

    public void after(IdempotentInfo idempotentInfo) {
        idempotenceStorage.updateAfter(idempotentInfo.getId(), idempotentInfo);
    }

    public void delete(IdempotentInfo idempotentInfo) {
        idempotenceStorage.delete(idempotentInfo.getId());
    }
}
