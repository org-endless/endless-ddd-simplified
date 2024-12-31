package org.endless.ddd.simplified.starter.common.config.data.cache.redis.queue;

import org.endless.ddd.simplified.starter.common.utils.id.IdGenerator;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * RedisQueue
 * <p>
 * create 2024/11/28 23:06
 * <p>
 * update 2024/11/28 23:06
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
public class RedisQueue<T> {

    private final RedisTemplate<String, T> redisTemplate;

    private final String queueId;

    private final String lockId;

    private final Long lockTimeout;

    private final Long lockExpireTime;

    public RedisQueue(RedisTemplate<String, T> redisTemplate, String queueId, String lockId, Long lockTimeout, Long lockExpireTime) {
        this.redisTemplate = redisTemplate;
        this.queueId = queueId;
        this.lockId = lockId;
        this.lockTimeout = lockTimeout;
        this.lockExpireTime = lockExpireTime;
    }

    /**
     * 向队列尾部添加元素（带锁）
     *
     * @param element 要添加的元素
     * @return 是否成功添加
     */
    public boolean push(T element) {
        if (acquireLock()) {
            try {
                redisTemplate.opsForList().rightPush(queueId, element);
                return true;
            } finally {
                releaseLock();
            }
        }
        return false; // 获取锁失败
    }

    /**
     * 从队列头部弹出元素（带锁）
     *
     * @return 队列头部的元素
     */
    public T pop() {
        if (acquireLock()) {
            try {
                return redisTemplate.opsForList().leftPop(queueId);
            } finally {
                releaseLock();
            }
        }
        return null; // 获取锁失败
    }

    /**
     * 从队列头部弹出元素（带阻塞）
     *
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return 队列头部的元素
     */
    public T popBlocking(long timeout, TimeUnit unit) {
        if (acquireLock()) {
            try {
                return redisTemplate.opsForList().leftPop(queueId, timeout, unit);
            } finally {
                releaseLock();
            }
        }
        return null; // 获取锁失败
    }

    /**
     * 获取队列长度
     *
     * @return 队列长度
     */
    public Long size() {
        return redisTemplate.opsForList().size(queueId);
    }

    /**
     * 获取队列中所有元素
     *
     * @return 队列元素列表
     */
    public List<T> getAllElements() {
        return redisTemplate.opsForList().range(queueId, 0, -1);
    }

    /**
     * 清空队列
     */
    public void clear() {
        redisTemplate.delete(queueId);
    }

    /**
     * 尝试获取锁
     *
     * @return 是否成功获取锁
     */
    private boolean acquireLock() {
        String lockValue = IdGenerator.of();
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockId, (T) lockValue, lockExpireTime, TimeUnit.SECONDS);

        if (Boolean.TRUE.equals(success)) {
            // 锁成功，返回 true
            return true;
        }

        // 锁失败，检查锁是否已过期
        String currentLockValue = (String) redisTemplate.opsForValue().get(lockId);
        if (currentLockValue != null) {
            // 如果锁值相同，说明锁已经过期，可以重置锁
            String oldLockValue = (String) redisTemplate.opsForValue().getAndSet(lockId, (T) lockValue);
            return currentLockValue.equals(oldLockValue); // 成功获取锁
        }
        return false; // 锁被其他线程占用
    }

    /**
     * 释放锁
     */
    private void releaseLock() {
        // 获取当前锁值
        String currentLockValue = (String) redisTemplate.opsForValue().get(lockId);
        if (currentLockValue != null) {
            // 如果锁值匹配，删除锁
            redisTemplate.delete(lockId);
        }
    }
}
