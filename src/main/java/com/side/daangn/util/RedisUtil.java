package com.side.daangn.util;

import com.side.daangn.exception.NotFoundException;
import com.side.daangn.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class RedisUtil {
     private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long getRemainingValidTime(String key){
        try {
            return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        } catch (RedisConnectionFailureException e) {
            System.err.println("Redis 서버에 연결할 수 없습니다: " + e.getMessage());
            // 로깅 및 알림 추가 가능
            throw new NotFoundException(e.getMessage());
        } catch (DataAccessException e) {
            System.err.println("Redis 데이터 액세스 예외 발생: " + e.getMessage());
            // 예외 전파 또는 기본 처리
            throw new NotFoundException(e.getMessage());
        }
    }

    public void saveToken(String key, String token, long expirationTimeInMinutes) {
        try {
            redisTemplate.opsForValue().set(key, token, Duration.ofMinutes(expirationTimeInMinutes));

        } catch (RedisConnectionFailureException e) {
            System.err.println("Redis 서버에 연결할 수 없습니다: " + e.getMessage());
            // 로깅 및 알림 추가 가능
            throw new NotFoundException(e.getMessage());
        } catch (DataAccessException e) {
            System.err.println("Redis 데이터 액세스 예외 발생: " + e.getMessage());
            // 예외 전파 또는 기본 처리
            throw new NotFoundException(e.getMessage());
        }
    }

    public String getToken(String key) {
        try {
            return (String) redisTemplate.opsForValue().get(key);
        }catch (RedisConnectionFailureException e) {
            System.err.println("Redis 서버에 연결할 수 없습니다: " + e.getMessage());
            throw new NotFoundException(e.getMessage());
        }catch (DataAccessException e) {
            System.err.println("Redis 데이터 액세스 예외 발생: " + e.getMessage());
            throw new NotFoundException(e.getMessage());
//        }catch (NullPointerException e){
//            throw new NullPointerException("unAuthorized key");
        }catch (Exception e){
            System.out.println("null exception");
            throw new RuntimeException(e);
        }
    }

    public void deleteToken(String key) {
        try {
            if (redisTemplate.opsForValue().get(key).toString().isEmpty()) {
                throw new NotFoundException("The user is already logged out or the token is invalid.");
            }
            redisTemplate.delete(key);
        } catch (RedisConnectionFailureException e) {
            System.err.println("Redis 서버에 연결할 수 없습니다: " + e.getMessage());
            throw new NotFoundException(e.getMessage());
        } catch (DataAccessException e) {
            System.err.println("Redis 데이터 액세스 예외 발생: " + e.getMessage());
            throw new NotFoundException(e.getMessage());
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    public void matchedToken(String key, String value) {
        if(!this.getToken(key).equals(value)){
            throw new UnauthorizedException("Redis Not Matched");
        }
    }

}
