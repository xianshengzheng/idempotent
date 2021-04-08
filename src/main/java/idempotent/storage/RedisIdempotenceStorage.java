package idempotent.storage;

import idempotent.IdempotentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


/**
 * @author zhenghao
 */
@Component
public class RedisIdempotenceStorage implements IdempotenceStorage {

//    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public boolean saveIfAbsent(String idempotenceId, IdempotentInfo idempotentInfo) {
        Optional<Boolean> optional = Optional.ofNullable(stringRedisTemplate.opsForValue()
                .setIfAbsent(idempotenceId, idempotentInfo.getResultJson(), idempotentInfo.getMaxExecutionTime(), TimeUnit.SECONDS));
        return optional.orElse(false);
    }

    @Override
    public void updateAfter(String idempotenceId, IdempotentInfo idempotentInfo) {
        stringRedisTemplate.opsForValue().set(idempotenceId, idempotentInfo.getResultJson(), idempotentInfo.getDuration(), TimeUnit.SECONDS);
    }

    @Override
    public String getResultString(String idempotenceId) {
        return stringRedisTemplate.opsForValue().get(idempotenceId);
    }

    @Override
    public void delete(String idempotenceId) {
        stringRedisTemplate.delete(idempotenceId);
    }
}