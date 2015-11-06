package hxk.dao.redis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
 

/**
 * 
 * @author hxk
 * @description 缓存的工具类
 *2015年11月6日  下午2:17:06
 */
public class RedisCache implements Cache {
    private static Log logger = LogFactory.getLog(RedisCache.class);
    //这里可以根据不同的构造方法去构造链接
    private JedisPool pool = new JedisPool(getPoolConfig(),"localhost");
    /** The ReadWriteLock. */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
 
    private String id;
 
    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id=" + id);
        this.id = id;
    }
 
    public String getId() {
        return this.id;
    }
 
    public int getSize() {
	Jedis jedis = pool.getResource();
	try {
	    return Integer.valueOf(jedis.dbSize().toString()); 
	} finally {
	    pool.returnResource(jedis);
	}
        
    }
 
    public void putObject(Object key, Object value) {
	Jedis jedis = pool.getResource();
	try {
	    logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>putObject:" + key + "=" + value);
	    jedis.set(SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
	} finally {
	    pool.returnResource(jedis);
	}
    }
 
    public Object getObject(Object key) {
	Jedis jedis = pool.getResource();
	try {
	    Object value = SerializeUtil.unserialize(jedis.get(SerializeUtil.serialize(key.toString())));
	    logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>getObject:" + key + "=" + value);
	    return value;
	} finally {
	    pool.returnResource(jedis);
	}
    }
 
    public Object removeObject(Object key) {
	Jedis jedis = pool.getResource();
	try {
	    return jedis.expire(SerializeUtil.serialize(key.toString()), 0);
	} finally {
	    pool.returnResource(jedis);
	}
    }
 
    public void clear() {
	Jedis jedis = pool.getResource();
	try {
	    jedis.flushDB();
	} finally {
	    pool.returnResource(jedis);
	}
    }
 
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
 
    

    /** @description 获取连接池参数	
     *2015年11月6日  下午1:59:32
     *返回类型:void	
     */
    private  JedisPoolConfig getPoolConfig() {
	JedisPoolConfig config = new JedisPoolConfig();
	config.setMaxActive(32);
	config.setMaxIdle(6);
	config.setMaxWait(18000);
	config.setMinEvictableIdleTimeMillis(300000);
	config.setNumTestsPerEvictionRun(3);
	config.setTimeBetweenEvictionRunsMillis(60000);
	config.setWhenExhaustedAction((byte) 1);
	config.setTestOnBorrow(true);
	return config;
    }
}
