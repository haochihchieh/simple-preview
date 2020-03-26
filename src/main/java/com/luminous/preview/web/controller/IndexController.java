package com.luminous.preview.web.controller;

import com.luminous.preview.common.utils.FileUtil;
import com.luminous.preview.domain.dto.TargetFileNature;
import com.luminous.preview.domain.vo.PreviewFileVo;
import com.luminous.preview.service.FilePreviewHandle;
import com.luminous.preview.service.download.DownloadNetFileService;
import com.luminous.preview.service.fileconverter.FileConverterService;
import com.luminous.preview.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * @Auther: haochihchieh
 * @Date: 2019/10/23 8:59
 * @Description:
 */

@Slf4j
@Controller
public class IndexController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private DownloadNetFileService downloadFileService;

    @Autowired
    private FileConverterService converterService;

    @Autowired
    private FilePreviewHandle filePreviewHandle;


    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String index(Map<String, Object> map) {
        map.put("version", "v1.0");
        return "index";
    }

    /**
     * http://192.168.0.50:8900/preview?url=http://192.168.0.50/files/2020/03/25/e9b805ec-c311-49e7-b7ef-0f68213cf978.docx
     * http://192.168.0.50:8900/preview?url=http://192.168.0.50/files/2020/03/26/13ad9be9-642b-42d7-9986-314044b4e2fb.xls
     * @param url
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public String previewGet(@RequestParam("url") String url,
                             Model model,
                             HttpServletResponse response) {

        try {
            log.info("开始尝试预览：" + url);
            PreviewFileVo redisFile = redisService.findPreviewByKey(url);
            String extension = FileUtil.getFileExtensionFromUrl(url);
            if (redisFile != null && redisFile.getFileExtension() != null && redisFile.getFileExtension().equalsIgnoreCase(extension)) {
                File previewFile = new File(redisFile.getPathFileName());
                if (previewFile.exists() && previewFile.length() == redisFile.getFileBytes()) {
                    return filePreviewHandle.charge(redisFile, model);
                }
            }
            //文件不存在的话，先下载文件
            String downloadFilePathName =
                    downloadFileService.download(url, "");
            //进行文件转换
            TargetFileNature targetFileNature =
                    converterService.converter(downloadFilePathName);
            //加入缓存
            redisFile = new PreviewFileVo();
            redisFile.setOriginalUrl(url).setPathFileName(targetFileNature.getPathFileName())
                    .setFileBytes(targetFileNature.getFileBytes()).setFileExtension(targetFileNature.getFileExtension());
            redisService.savePreview(redisFile);
            return filePreviewHandle.charge(targetFileNature, model);
        } catch (Exception ex) {
            model.addAttribute("exception", "error");
            log.error("预览失败：" + url);
            ex.printStackTrace();
            return "error";
        }
    }


}
