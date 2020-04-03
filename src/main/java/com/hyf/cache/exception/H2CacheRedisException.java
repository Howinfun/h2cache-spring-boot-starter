package com.hyf.cache.exception;

/**
 * custom exception
 * @author howinfun
 * @since 2020-04-01
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
