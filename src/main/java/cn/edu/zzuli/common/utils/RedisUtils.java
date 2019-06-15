package cn.edu.zzuli.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;


public class RedisUtils {	
	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
	@Autowired
    private JedisSentinelPool jedisPool;
	
	



	private static RedisUtils redisUtils;
	@PostConstruct  
    public void init() {  
		redisUtils = this;  
		redisUtils.jedisPool = this.jedisPool;   
    }
	
	

    /**
     * 过去时间 两周
     */
    public final static int EXPIRED_SECONDS_TWO_WEEK = 2*7*24*60*60;

    /**
     * 过期时间 两天
     */
    public final static int EXPIRED_SECONDS_TWO_DAY = 2*24*60*60;


    /**
     * 刷新redis缓存
     *
     * @param key
     * @param value
     * @param timeout 过期时间秒,如果小于等于0，则永不过期
     * @return 
     */
    public  static void set(String key, String value, Integer timeout) {
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            jedis.set(key, value);
            if(timeout > 0){
            	jedis.expire(key, timeout);      
            }
                  
            
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }
    
    
    
    
    
    
     

    /**
     * 获取redis缓存的值
     *
     * @param key
     * @return
     */
    public static  String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
        return value;
    }
    
    
    /**
     * 刷新redis缓存
     *
     * @param key
     * @param value
     * @param timeout 过期时间秒,如果小于等于0，则永不过期
     * @return 
     */
    public  static void sset(String key, String value, Integer timeout) {
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            jedis.sadd(key, value);
            
            
            
            if(timeout > 0){
            	jedis.expire(key, timeout);      
            }
                  
            
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }
    
    
    /**
     * 刷新redis缓存
     *
     * @param key
     * @param value
     * @param timeout 过期时间秒,如果小于等于0，则永不过期
     * @return 
     */
    public  static void zset(String key, Object value, Integer timeout) {
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            jedis.zadd(key.getBytes(), 0,  SerializeUtil.serialize(value));
            
            
            if(timeout > 0){
            	jedis.expire(key, timeout);      
            }
                  
            
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }
    
    /**
     * 正排序的名次, 与 zrevrank (String key, Object value)返回值相反
     * @param key
     * @param value
     * @return
     */
    public  static Long zrank (String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            return jedis.zrank(key.getBytes(), SerializeUtil.serialize(value));
            
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
        return null;
    }
    
    /**
     * 查看vlalue在sorted set中倒排序时排在第几名,查询结果按照INDEX,所以INDEX是3表示排在第四名
     * @param key 
     * @param value
     * @return
     */
    public  static Long zrevrank (String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            return jedis.zrevrank(key.getBytes(), SerializeUtil.serialize(value));
            
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
        return null;
    }
    
    
    /**
     * 获取redis缓存的值
     *
     * @param key
     * @return
     */
    public static  Set<String> smembers(String key) {
        Set<String> values = null;
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            values = jedis.smembers(key);
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
        return values;
    }
    
    /**
     * 刷新redis缓存
     *
     * @param key
     * @param value
     * @param timeout 过期时间秒,如果小于等于0，则永不过期
     * @return 
     */
    public  static void sset(String key, Object value, Integer timeout) {
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            jedis.sadd(key.getBytes(), SerializeUtil.serialize(value));
            
            if(timeout > 0){
            	jedis.expire(key, timeout);      
            }
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }
    
    @SuppressWarnings("unchecked")
	public static <T> Set<T> smembersObject(String key,Class<T> t) {
		Set<T> values = null;
		Set<byte[]> bytesvalues = null;
		Jedis jedis = null;
		try {
			jedis = redisUtils.jedisPool.getResource();
			bytesvalues = jedis.smembers(key.getBytes());
			values = new HashSet<T>();
			for(byte[] b : bytesvalues){
				values.add((T)SerializeUtil.unserialize(b));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			redisUtils.jedisPool.returnResource(jedis);
		}
		return values;
	}

    
    
    /**
     * 刷新redis缓存
     *
     * @param key
     * @param value
     * @param timeout 过期时间秒,如果小于等于0，则永不过期
     * @return 
     */
    public  static void setObject(String key, Object value, Integer timeout) {
    	Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();            
            jedis.set(key.getBytes(), SerializeUtil.serialize(value));
          
            
            if(timeout > 0){
            	jedis.expire(key.getBytes(), timeout);        
            }
                     
            
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }
    
    
    public  static void fullAll() {
    	Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();            
            jedis.flushAll();                     
            
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }
    
    public static RedisUtils getRedisUtils() {
		return redisUtils;
	}

	public static void setRedisUtils(RedisUtils redisUtils) {
		RedisUtils.redisUtils = redisUtils;
	}
	
	
	








	/**
     * 获取redis缓存的值
     *
     * @param key
     * @return
     */
    public static  Object getObject(String key) {
    	Object value = null;
    	Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            value = SerializeUtil.unserialize(jedis.get(key.getBytes()));
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
        return value;
    }
    
    
    /**
     * 删除redis缓存中的信息；
     *
     * @param key
     */
    public static void del(String key) {
    	Jedis jedis = null;

        try {
            jedis = redisUtils.jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }
    
    /**
     * 删除一个集合中的一个元素
     * 
     * @param key
     * @param value
     * @param timeout
     */
    public  static void srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            jedis.srem(key, value);  
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }
    
    
    /**
     * 删除一个集合中的一个元素
     * 
     * @param key
     * @param value
     * @param timeout
     */
    public  static void srem(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            jedis.srem(key.getBytes(), SerializeUtil.serialize(value));  
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }

    public JedisSentinelPool getJedisPool() {
		return jedisPool;
	}

    /**
     * 获取redis缓存的值 【结合】
     *
     * @param key
     * @return
     */
    public static Set<String> keys(String key) {
        Set<String> value = null;
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            value = jedis.keys(key);
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
        return value;
    }
    
    
    /**
     * key改名
     *
     * @param key
     * @param value
     * @param timeout 过期时间秒,如果小于等于0，则永不过期
     * @return 
     */
    public  static void rename(String oldkey, String newkey, Integer timeout) {
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            jedis.rename(oldkey, newkey);
            if(timeout > 0){
            	jedis.expire(newkey, timeout);      
            }
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }
    
    public static final long ONE_MILLI_NANOS = 1000000L;
    public static final long DEFAULT_TIME_OUT = 3000;
    //锁的超时时间（秒），过期删除
    public static final int EXPIRE = 5 * 60;
    public static final String LOCKED = "TRUE";
    public static final Random r = new Random();
    private static boolean locked = false;
    
    public static boolean lock( String key,long timeout) {
        long nano = System.nanoTime();
        timeout *= ONE_MILLI_NANOS;
        Jedis jedis = null;
        try {
        	jedis = redisUtils.jedisPool.getResource();
            while ((System.nanoTime() - nano) < timeout) {
                if (jedis.setnx(key, LOCKED) == 1 ){
                    jedis.expire(key, EXPIRE);
                    locked = true;
                    return locked;
                }
                // 短暂休眠，nano避免出现活锁
                Thread.sleep(3, r.nextInt(500));
            }
        } catch (Exception e) {
        }
        locked= false;
        return locked;
    }
    
    /**
     * 刷新redis缓存
     *
     * @param key
     * @param value
     * @param timeout 过期时间秒,如果小于等于0，则永不过期
     * @return 
     */
    public  static void rpushObject(String key, Object value, Integer timeout) {
        Jedis jedis = null;
        try {
            jedis = redisUtils.jedisPool.getResource();
            jedis.rpush(key.getBytes(), SerializeUtil.serialize(value));
                        
            if(timeout > 0){
            	jedis.expire(key, timeout);      
            }
        } catch (Exception e) {
        	 logger.error(e.getMessage());
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }
    
    @SuppressWarnings("unchecked")
	public static <T> List<T> lrangeObject(String key,Class<T> t) {
    	List<T> values = null;
		List<byte[]> bytesvalues = null;
		Jedis jedis = null;
		try {
			jedis = redisUtils.jedisPool.getResource();
			Long end=jedis.llen(key);
			bytesvalues = (List<byte[]>) jedis.lrange(key.getBytes(), 0, end.intValue());
			
			values = new ArrayList<T>();
			for(byte[] b : bytesvalues){
				values.add((T)SerializeUtil.unserialize(b));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			redisUtils.jedisPool.returnResource(jedis);
		}
		return values;
	}    
    
    
    
    
    public static boolean lock(String key) {
        return lock(key,DEFAULT_TIME_OUT);
    }

    // 无论是否加锁成功，必须调用
    public static void unlock(String key) {
    	Jedis jedis = null;
        try {
            if (locked){
            	jedis = redisUtils.jedisPool.getResource();
                jedis.del(key);
            }
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
    }
    
    
    /**
     * 设置过期时间
     * @param key
     * @param expireTime 单位秒
     * @return
     */
    public static Long expire(String key,int expireTime){
    	Jedis jedis = null;
    	Long value = null;
        try {
            	jedis = redisUtils.jedisPool.getResource();
            	value = jedis.expire(key, expireTime);
        } finally {
        	redisUtils.jedisPool.returnResource(jedis);
        }
        return value;
    }

	public void setJedisPool(JedisSentinelPool jedisPool) {
		this.jedisPool = jedisPool;
	}
}
