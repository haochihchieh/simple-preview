package com.luminous.preview.service.redis;

import com.luminous.preview.domain.vo.PreviewFileVo;
import com.luminous.preview.domain.vo.RedisVo;

import java.util.List;

/**
 * @Auther: haochihchieh
 * @Date: 2019/10/22 9:21
 * @Description:
 */
public interface RedisService {


    /**
     * 通过key查询文件
     *
     * @param key
     * @return
     */
    List<RedisVo> findByKey(String key);


    /**
     * 保存
     *
     * @param key
     * @param val
     */
    void saveRedis(String key, Object val);

    /**
     * 删除
     *
     * @param key
     */
    void delete(String key);

    /**
     * 保存预览文件
     *
     * @param previewFileVo
     */
    void savePreview(PreviewFileVo previewFileVo);

    /** 获取预览文件
     * @param key
     * @return
     */
    PreviewFileVo findPreviewByKey(String key);

    /**
     * 清空所有缓存
     */
    void flushRedis();
}
