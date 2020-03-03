package com.luminous.preview.service.download;

/**
 * @Auther: haozhijie
 * @Date: 2019/10/22 11:21
 * @Description:
 */
public interface DownloadNetFileService {

    /**
     * 下载网络文件
     *
     * @param originalUrl        文件的请求url
     * @param fileOriginalName 文件全名（example.txt）
     */
    String download(String originalUrl, String fileOriginalName);
}
