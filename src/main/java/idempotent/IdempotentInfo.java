package idempotent;

/**
 * 幂等信息
 * @author zhenghao
 */
public class IdempotentInfo {
    /**
     * 默认20秒
     */
    public static final int DEFAULT_MAX_EXECUTION_TIME =  20;

    /**
     * 默认一周
     */
    public static final int DEFAULT_DURATION =  60 * 60 * 24 * 7;

    public static final String DEFAULT_PREFIX = "idem:";

    public static final int FAILURE_RETRY_SPENDING_MAX_COUNT = 3;

    private String id;

    private int maxExecutionTime;

    private int duration;

    private String prefix;

    private boolean delaySpending;

    private int delaySpendingMaxCount;

    public static class IdempotentInfoBuilder {
        private String id;

        private int maxExecutionTime = DEFAULT_MAX_EXECUTION_TIME;

        private int duration = DEFAULT_DURATION;

        private String prefix = DEFAULT_PREFIX;

        private boolean delaySpending;

        private int delaySpendingMaxCount = FAILURE_RETRY_SPENDING_MAX_COUNT;


        public static IdempotentInfoBuilder builder() {
            return new IdempotentInfoBuilder();
        }

        public IdempotentInfoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public IdempotentInfoBuilder maxExecutionTime(int maxExecutionTime) {
            this.maxExecutionTime = maxExecutionTime;
            return this;
        }

        public IdempotentInfoBuilder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public IdempotentInfoBuilder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public IdempotentInfoBuilder delaySpending(boolean delaySpending) {
            this.delaySpending = delaySpending;
            return this;
        }

        public IdempotentInfoBuilder delaySpendingMaxCount(int delaySpendingMaxCount) {
            this.delaySpendingMaxCount = delaySpendingMaxCount;
            return this;
        }

        public IdempotentInfo build() {
            IdempotentInfo idempotentInfo = new IdempotentInfo();
            idempotentInfo.setId(id);
            idempotentInfo.setMaxExecutionTime(maxExecutionTime);
            idempotentInfo.setDuration(duration);
            idempotentInfo.setPrefix(prefix);
            idempotentInfo.setDelaySpending(delaySpending);
            idempotentInfo.setDelaySpendingMaxCount(delaySpendingMaxCount);
            return idempotentInfo;
        }

        public static IdempotentInfo build(Idempotent idempotent, String id) {
            IdempotentInfo idempotentInfo = new IdempotentInfo();
            idempotentInfo.setId(id);
            idempotentInfo.setMaxExecutionTime(idempotent.maxExecutionTime());
            idempotentInfo.setDuration(idempotent.duration());
            idempotentInfo.setPrefix(idempotent.prefix());
            idempotentInfo.setDelaySpending(idempotent.failureRetry());
            return idempotentInfo;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxExecutionTime() {
        return maxExecutionTime;
    }

    public void setMaxExecutionTime(int maxExecutionTime) {
        this.maxExecutionTime = maxExecutionTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isDelaySpending() {
        return delaySpending;
    }

    public void setDelaySpending(boolean delaySpending) {
        this.delaySpending = delaySpending;
    }

    public int geDelaySpendingMaxCount() {
        return delaySpendingMaxCount;
    }

    public void setDelaySpendingMaxCount(int delaySpendingMaxCount) {
        this.delaySpendingMaxCount = delaySpendingMaxCount;
    }
}
