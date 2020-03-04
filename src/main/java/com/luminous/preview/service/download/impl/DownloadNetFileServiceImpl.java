package com.luminous.preview.service.download.impl;

import com.luminous.preview.common.utils.FileUtil;
import com.luminous.preview.service.download.DownloadNetFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Auther: haochihchieh
 * @Date: 2019/10/22 11:22
 * @Description:
 */

@Service
@Slf4j
public class DownloadNetFileServiceImpl implements DownloadNetFileService {

    @Value("${converter.source}")
    private String source;

    @Override
    public String download(String originalUrl, String fileOriginalName) {
        log.info("尝试下载文件：" + originalUrl);
        if (StringUtils.isBlank(originalUrl)) {
            throw new RuntimeException("download url can not be empty!");
        }

        try {
            URL url = new URL(originalUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            connection.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            connection.setRequestMethod("GET");
            //得到输入流
            InputStream inputStream = connection.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(source);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }

            if (StringUtils.isBlank(fileOriginalName)) {
                fileOriginalName = FileUtil.getFileNameFromUrl(originalUrl);
            }

            String filePathName = saveDir + File.separator + fileOriginalName;
            File file = new File(filePathName);
            FileOutputStream out = new FileOutputStream(file);
            out.write(getData);
            if (out != null) {
                out.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            log.info("下载文件成功：" + originalUrl + "。已保存至：" + fileOriginalName);
            return filePathName;
        } catch (Exception ex) {
            log.error("下载文件失败：" + originalUrl + "。" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
