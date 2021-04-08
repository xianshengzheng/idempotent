package idempotent.businesspolocy;

/**
 * @author zhenghao
 */

public enum BusinessErrorPolicyEnum{
        /**
         * 删除幂等值
         */
        delete(1),
        /**
         * 保留幂等值
         */
        save(2),
        /**
         * 删除幂等值并重试
         */
        delete_and_retry(3),
        ;
        private final Integer code;

        BusinessErrorPolicyEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }