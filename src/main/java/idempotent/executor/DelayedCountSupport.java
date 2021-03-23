package idempotent.executor;


import com.sun.istack.internal.NotNull;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 记录执行次数的延迟消息
 *
 * @author zhenghao
 */
public abstract class DelayedCountSupport implements Delayed {
    /**
     * 延迟时间（秒）
     */
    private static final int DELAY_TIME = 3;

    /**
     * 执行次数
     */
    private int count = 0;

    /**
     * 到期时间
     */
    private long expire;

    public DelayedCountSupport() {
        this.expire = System.currentTimeMillis() + DELAY_TIME * 1000;
    }

    public int getCount() {
        return count;
    }

    public void addCount() {
        this.count++;
    }

    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.SECONDS) - o.getDelay(TimeUnit.SECONDS));
    }

}
