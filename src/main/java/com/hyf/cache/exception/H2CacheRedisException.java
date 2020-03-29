package com.hyf.cache.exception;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2020/3/29
 * @email 876237770@qq.com
 */
public class H2CacheRedisException extends Exception{

    public H2CacheRedisException() {
        super();
    }

    public H2CacheRedisException(String msg){
        super(msg);
    }

    public H2CacheRedisException(String message, Throwable cause) {
        super(message, cause);
    }

    public H2CacheRedisException(Throwable cause) {
        super(cause);
    }
}
