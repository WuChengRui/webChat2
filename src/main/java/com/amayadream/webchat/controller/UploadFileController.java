package com.amayadream.webchat.controller;

import com.amayadream.webchat.utils.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class UploadFileController {
    /*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping("/springUpload")
    public String  springUpload(HttpServletRequest request) throws IllegalStateException, IOException
    {
        // 上传文件
        UploadUtil.springUpload(request);
        return "/chat";
    }
}
