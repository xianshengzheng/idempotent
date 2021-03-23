package idempotent.delay;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import idempotent.IdempotentTemplate;
import idempotent.aspect.IdempotentAspect;
import idempotent.executor.AbstractIdempotentDelayedExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 失败重试延迟队列
 * @author zhenghao
 */
@Component
public class DelayQueueManager {
    static Logger logger = LoggerFactory.getLogger(DelayQueueManager.class);

    final DelayQueue<AbstractIdempotentDelayedExecutor> delayQueue = new DelayQueue<>();

    private IdempotentTemplate idempotentTemplate;

    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("idempotent-thread-%d").build();

    private static ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 60,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new AbortPolicy());

    @PostConstruct
    public void start() {
        singleThreadPool.execute(() -> {
            while(true) {
                try {
                    AbstractIdempotentDelayedExecutor element = delayQueue.poll();
                    if(element != null){
                        idempotentTemplate.execute(element);
                    }
                    Thread.sleep(1000);
                }  catch (Throwable throwable) {
                    logger.error("DelayQueueManager error:{}",throwable.getMessage());
                    continue;
                }
            }
        });
    }

    @Autowired
    public void setIdempotentTemplate(IdempotentTemplate idempotentTemplate) {
        this.idempotentTemplate = idempotentTemplate;
    }


    public void offer(AbstractIdempotentDelayedExecutor delayedExecutor) {
        delayQueue.offer(delayedExecutor);
    }


}

