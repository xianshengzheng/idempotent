package idempotent.executor;


import idempotent.IdempotentInfo;

import java.util.concurrent.Delayed;

/**
 * @author zhenghao
 */
public interface IdempotentExecutor extends Delayed {
    Object execute() throws Throwable;

    IdempotentInfo getIdempotentInfo();
}
