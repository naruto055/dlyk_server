package com.powernode.util;

import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CacheUtils {
    public static <T> T getCacheData(Supplier<T> cacheSelector, Supplier<T> databaseSelector, Consumer<T> cacheSave) {
        // 从redis中查
        T data = cacheSelector.get();
        if (ObjectUtils.isEmpty(data)) {
            // 从数据库中查
            data = databaseSelector.get();
            // 数据库查到了数据
            if (!ObjectUtils.isEmpty(data)) {
                // 数据存入redis
                cacheSave.accept(data);
            }
        }
        // 返回数据
        return data;
    }
}
