package idempotent;

import idempotent.businesspolocy.BusinessErrorPolicyEnum;

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

    /**
     * 业务错误，默认重试重试次数
     */
    public static final int delaySpendingMaxCount = 3;

    private String id;

    private int maxExecutionTime;

    private int duration;

    private String prefix;

    private String resultJson = "";

    private boolean saveResult;

    private BusinessErrorPolicyEnum businessErrorPolicyEnum;


    public static class IdempotentInfoBuilder {
        private String id;

        private int maxExecutionTime = DEFAULT_MAX_EXECUTION_TIME;

        private int duration = DEFAULT_DURATION;

        private String prefix = DEFAULT_PREFIX;

        private boolean saveResult;

        private BusinessErrorPolicyEnum businessErrorPolicyEnum;


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

        public IdempotentInfoBuilder saveResult(boolean saveResult) {
            this.saveResult = saveResult;
            return this;
        }

        public IdempotentInfoBuilder businessErrorPolicyEnum(BusinessErrorPolicyEnum businessErrorPolicyEnum) {
            this.businessErrorPolicyEnum = businessErrorPolicyEnum;
            return this;
        }

        public IdempotentInfo build() {
            IdempotentInfo idempotentInfo = new IdempotentInfo();
            idempotentInfo.setId(id);
            idempotentInfo.setMaxExecutionTime(maxExecutionTime);
            idempotentInfo.setDuration(duration);
            idempotentInfo.setPrefix(prefix);
            idempotentInfo.setBusinessErrorPolicyEnum(businessErrorPolicyEnum);
            idempotentInfo.setSaveResult(saveResult);
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

    public int geDelaySpendingMaxCount() {
        return delaySpendingMaxCount;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public boolean isSaveResult() {
        return saveResult;
    }

    public void setSaveResult(boolean saveResult) {
        this.saveResult = saveResult;
    }

    public BusinessErrorPolicyEnum getBusinessErrorPolicyEnum() {
        return businessErrorPolicyEnum;
    }

    public void setBusinessErrorPolicyEnum(BusinessErrorPolicyEnum businessErrorPolicyEnum) {
        this.businessErrorPolicyEnum = businessErrorPolicyEnum;
    }
}
