package com.luminous.security.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * token缓存工具类
 * 　　* @author 郝江波
 * 　　* @date 2020/3/25 19:04
 *
 */
public class TokenCatchUtils {
    private static final ConcurrentMap<Thread, String> TOKEN_CATCH = new ConcurrentHashMap<>();

    public static String getCurrentToken() {
        String r = TOKEN_CATCH.get(Thread.currentThread());
        return StringUtils.isEmpty(r) ? "" : r;
    }

    public static String setCurrentToken(String token) {
        return TOKEN_CATCH.put(Thread.currentThread(), token);
    }

    public static String clearCurrentToken() {
        return TOKEN_CATCH.remove(Thread.currentThread());
    }

}
