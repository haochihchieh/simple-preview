package com.luminous.preview.service.redis.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luminous.preview.domain.vo.PreviewFileVo;
import com.luminous.preview.domain.vo.RedisVo;
import com.luminous.preview.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: haochihchieh
 * @Date: 2019/10/22 9:21
 * @Description:
 */

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    /**
     * redis缓存时长90分钟
     */
    private static long TIMEOUT_MINUTES = 90;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<RedisVo> findByKey(String key) {
        List<RedisVo> redisVos = new ArrayList<>();
        if (!"*".equals(key)) {
            key = "*" + key + "*";
        }
        for (Object object : redisTemplate.keys(key)) {
            RedisVo redisVo = new RedisVo(object.toString(),
                    redisTemplate.opsForValue().get(object.toString()).toString());
            redisVos.add(redisVo);
        }
        return redisVos;
    }

    @Override
    public void saveRedis(String key, Object val) {
        redisTemplate.opsForValue().set(key, val);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void savePreview(PreviewFileVo previewFileVo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
//          String key = previewFileVo.getFileHashCode();
            String key = previewFileVo.getOriginalUrl();
            String value = objectMapper.writeValueAsString(previewFileVo);
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(value, TIMEOUT_MINUTES, TimeUnit.MINUTES);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public PreviewFileVo findPreviewByKey(String key) {

        if (redisTemplate.hasKey(key)) {
            String value = redisTemplate.opsForValue().get(key).toString();
            if (StringUtils.isEmpty(value)) {
                log.info("redis中不存在【" + key + "】有效记录");
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                PreviewFileVo previewFileVo = objectMapper.readValue(value,
                        PreviewFileVo.class);
                log.info("redis中找到【" + key + "】有效记录");
                return previewFileVo;
            } catch (Exception ex) {
                log.info("redis中查找失败" + ex.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void flushRedis() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }
}
