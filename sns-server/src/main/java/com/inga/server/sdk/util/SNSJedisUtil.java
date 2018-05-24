package com.inga.server.sdk.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis缓存工具类
 *
 *
 */
public class SNSJedisUtil {
	/**
	 * 连接池
	 */
	private JedisPool jedisPool = null;

	private static SNSJedisUtil instance = null;

	private SNSJedisUtil() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(500);
		jedisPoolConfig.setMaxIdle(100);
		jedisPoolConfig.setMaxWaitMillis(5000);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		this.jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1",6379);
	}

	public static SNSJedisUtil getInstance() {
		if (instance == null) {
			synchronized (SNSJedisUtil.class) {
				if (instance == null) {
					instance = new SNSJedisUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 */
	public void set(String key, Object value) {
		Jedis jedis = getJedis();
		try {
			jedis.set(key.getBytes(), SerializeUtil.serialize(value));
		} finally {
			jedis.close();
		}
	}

	/**
	 * @param key 键
	 * @param minute 过期时间 单位分钟
	 * @param value 值
	 */
	public void setex(String key, int minute, Object value) {
		Jedis jedis = getJedis();
		try {
			jedis.setex(key.getBytes(), minute * 60, SerializeUtil.serialize(value));
		} finally {
			jedis.close();
		}
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 *            键
	 * @param minute
	 *            过期时间 单位分钟
	 */
	public void expire(String key, int minute) {
		Jedis jedis = getJedis();
		try {
			jedis.expire(key.getBytes(), minute * 60);
		} finally {
			jedis.close();
		}
	}

	/**
	 * 是否存在键值
	 * @param key 键
	 * @return ture / false
	 */
	public boolean exists(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.exists(key.getBytes());
		} finally {
			jedis.close();
		}
	}

	/**
	 * 取得缓存值
	 * 
	 * @param key 键
	 * @return 返回缓存值
	 */
	public Object get(String key) {
		Jedis jedis = getJedis();
		try {
			byte[] data = jedis.get(key.getBytes());
			return SerializeUtil.unserialize(data);
		} finally {
			jedis.close();
		}
	}

	/**
	 * 清除缓存
	 * 
	 * @param key
	 *            键
	 */
	public void del(String key) {
		Jedis jedis = getJedis();
		try {
			jedis.del(key.getBytes());
		} finally {
			jedis.close();
		}
	}

	private Jedis getJedis() {
		return jedisPool.getResource();
	}

}
