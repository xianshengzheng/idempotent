package idempotent.storage;


import idempotent.IdempotentInfo;

/**
 * @author zhenghao
 */
public interface IdempotenceStorage {

    /**
     * 原子性的插入idempotenceId
     * @param idempotenceId 幂等记录唯一值
     * @param idempotentInfo 幂等信息
     * @return
     */
    boolean saveIfAbsent(String idempotenceId, IdempotentInfo idempotentInfo);

    /**
     * 完成业务代码之后调用
     * @param idempotenceId 幂等记录唯一值
     * @param idempotentInfo 幂等信息
     */
    void updateAfter(String idempotenceId, IdempotentInfo idempotentInfo);

    /**
     * key对应的记录是否存在
     * @param idempotenceId 幂等记录唯一值
     * @return
     */
    boolean exist(String idempotenceId);

    /**
     * 删除指定idempotenceId的记录
     * @param idempotenceId 幂等记录唯一值
     */
    void delete(String idempotenceId);

}
