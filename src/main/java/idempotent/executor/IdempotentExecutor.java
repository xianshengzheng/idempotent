package idempotent.executor;


import idempotent.IdempotentInfo;

/**
 * @author zhenghao
 */
public interface IdempotentExecutor{
    Object execute() throws Throwable;

    IdempotentInfo getIdempotentInfo();
}
