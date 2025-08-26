package cc.sika.file.util;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存工具类
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Component
@SuppressWarnings("unused")
public final class CacheUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    // ============================== 基础操作 ===============================

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存并指定过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间（秒）
     */
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存并指定过期时间（带单位）
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间数值
     * @param unit    时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取缓存值
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取缓存值（指定类型）
     *
     * @param key   键
     * @param clazz 值类型
     * @return 值对象
     */
    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        return clazz.isInstance(value) ? clazz.cast(value) : null;
    }

    /**
     * 删除缓存
     *
     * @param key 键
     * @return 是否删除成功
     */
    @SuppressWarnings("UnusedReturnValue")
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 设置过期时间
     *
     * @param key     键
     * @param timeout 过期时间（秒）
     * @return 是否设置成功
     */
    @SuppressWarnings("UnusedReturnValue")
    public Boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取剩余过期时间
     *
     * @param key 键
     * @return 剩余时间（秒），-1表示永久有效，-2表示键不存在
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    // ============================== 原子操作 ===============================

    /**
     * 原子自增
     *
     * @param key   键
     * @param delta 增量
     * @return 自增后的值
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 原子自减
     *
     * @param key   键
     * @param delta 减量
     * @return 自减后的值
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    // ============================== Hash操作 ===============================

    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void hDel(String key, Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    public boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    // ============================== 分布式锁 ===============================

    private static final String LOCK_PREFIX = "LOCK:";
    private static final String LOCK_SCRIPT =
            "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then " +
                    "   return redis.call('pexpire', KEYS[1], ARGV[2]) " +
                    "else " +
                    "   return 0 " +
                    "end";

    /**
     * 获取分布式锁（简单实现）
     *
     * @param lockKey    锁key
     * @param lockValue  锁值（建议UUID）
     * @param expireTime 锁过期时间（毫秒）
     * @return 是否获取成功
     */
    public boolean tryLockSimple(String lockKey, String lockValue, long expireTime) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(
                LOCK_PREFIX + lockKey,
                lockValue,
                Duration.ofMillis(expireTime)
        );
        return Boolean.TRUE.equals(result);
    }

    /**
     * 获取分布式锁（原子操作实现）
     *
     * @param lockKey    锁key
     * @param lockValue  锁值（建议UUID）
     * @param expireTime 锁过期时间（毫秒）
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, String lockValue, long expireTime) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(LOCK_SCRIPT, Long.class);
        Object result = redisTemplate.execute(
                script,
                Collections.singletonList(LOCK_PREFIX + lockKey),
                lockValue,
                String.valueOf(expireTime)
        );
        return Long.valueOf(1).equals(result);
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁key
     * @param lockValue 锁值
     * @return 是否释放成功
     */
    public Boolean unlock(String lockKey, String lockValue) {
        String key = LOCK_PREFIX + lockKey;
        if (lockValue.equals(redisTemplate.opsForValue().get(key))) {
            return redisTemplate.delete(key);
        }
        return false;
    }

    // ============================== 对象缓存辅助 ===============================

    /**
     * 缓存对象（JSON序列化）
     *
     * @param key 缓存键
     * @param obj 缓存对象
     * @param ttl 过期时间（秒）
     */
    public void cacheObject(String key, Object obj, long ttl) {
        redisTemplate.opsForValue().set(key, obj, ttl, TimeUnit.SECONDS);
    }

    /**
     * 缓存集合
     *
     * @param key  缓存键
     * @param list 集合数据
     * @param ttl  过期时间（秒）
     */
    public <T> void cacheList(String key, List<T> list, long ttl) {
        for (T t : list) {
            redisTemplate.opsForList().rightPush(key, t);
        }
        expire(key, ttl);
    }

    /**
     * 获取对象集合
     *
     * @param key   缓存键
     * @param clazz 对象类型
     * @return 对象列表
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        List<Object> range = redisTemplate.opsForList().range(key, 0, -1);
        if (range == null) return Collections.emptyList();

        List<T> result = new ArrayList<>();
        for (Object o : range) {
            if (clazz.isInstance(o)) {
                result.add(clazz.cast(o));
            }
        }
        return result;
    }
}
