package idempotent.storage;

import idempotent.IdempotentInfo;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地测试类，使用map来存储幂等值
 * @author zhenghao
 */
@Component
public class LocalTestIdempotenceStorage implements IdempotenceStorage {

    private static final Map<String,String> idempotenceIdMap = new ConcurrentHashMap<>();


    @Override
    public boolean saveIfAbsent(String idempotenceId, IdempotentInfo idempotentInfo) {
        synchronized(this){
            if (!idempotenceIdMap.containsKey(idempotenceId)) {
                idempotenceIdMap.put(idempotenceId,idempotenceId);
                return true;
            }
            return false;
        }
    }

    @Override
    public void updateAfter(String idempotenceId, IdempotentInfo idempotentInfo) {

    }

    @Override
    public boolean exist(String idempotenceId) {
        return false;
    }

    @Override
    public void delete(String idempotenceId) {
        idempotenceIdMap.remove(idempotenceId);
    }
}
