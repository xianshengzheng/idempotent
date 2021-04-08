package idempotent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 幂等接口:
 * 1. id支持EL表达式，根据prefix+id作为幂等值，其中prefix非必传，可用于区分不同的业务。
 * 2. 支持最大重试次数的延迟队列，默认关闭
 * 3. 当重复调用的时候，目前无法返回上一次成功数据。而是直接抛出{@code RejectedException}.
 * 4. 支持使用不同的数据源实现，目前只实现了redis
 * @author zhenghao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Idempotent {

    /**
     * id的前缀(用以区分不同业务)
     */
    String prefix() default IdempotentInfo.DEFAULT_PREFIX;

    /**
     * 幂等操作的唯一标识，使用spring el表达式 用#来引用方法参数
     */
    String id();

    /**
     * 是否开启幂等方法结果保存
     */
    boolean saveResult() default false;

    /**
     * 幂等操作最大执行时间（秒）
     */
    int maxExecutionTime() default IdempotentInfo.DEFAULT_MAX_EXECUTION_TIME;

    /**
     * 幂等持续时间（秒）
     */
    int duration() default IdempotentInfo.DEFAULT_DURATION;

    /**
     * 是否开启失败重试
     */
    boolean failureRetry() default false;

    /**
     * 失败重试最大次数
     */
    int failureRetryMaxCount() default IdempotentInfo.FAILURE_RETRY_SPENDING_MAX_COUNT;
}