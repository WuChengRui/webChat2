package com.amayadream.webchat.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Timestamp;
import java.util.Iterator;

/**
 * NAME   :  EmailSystem/com.amayadream.emailsystem.util
 * Author :  Amayadream
 * Date   :  2015.12.05 15:22
 * TODO   :  文件上传工具类
 */
public class UploadUtil {

    /**
     * Spring MVC文件上传,返回的是经过处理的path+fileName
     * @param request    request
     * @param folder    上传文件夹
     * @param userid    用户名
     * @return
     */
    public static String upload(HttpServletRequest request, String folder, String userid){
        FileUtil fileUtil = new FileUtil();
        String file_url = "";
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                String prefix = fileUtil.getFilePrefix(file);
                if(file != null){
                    //取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为"",说明该文件存在，否则说明该文件不存在
                    if(myFileName.trim() !=""){
                        System.out.println(myFileName);
                        //重命名上传后的文件名
                        String fileName =  userid + "." + prefix;
                        //定义上传路径,格式为 upload/Amayadream/Amayadream.jpg
                        String path = request.getServletContext().getRealPath("/") + folder + "/" + userid;
                        File localFile = new File(path, fileName);
                        if(!localFile.exists()){
                            localFile.mkdirs();
                        }
                        try {
                            file.transferTo(localFile);
                            file_url = folder + "/" + userid + "/" + fileName;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return file_url;
    }
    public static void  springUpload(HttpServletRequest request) throws IllegalStateException, IOException
    {
        long  startTime=System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();

            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    String pathUrl = request.getServletContext().getRealPath("/")+"fileUpload"+"\\";
                    System.out.println(pathUrl);
                    String path=pathUrl+file.getOriginalFilename();
                    System.out.println(path);
                    File file1 = new File(path);
                    if (!file1.exists()){
                        file1.mkdirs();
                    }
                    //上传
                    file.transferTo(file1);
                }

            }

        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
    }

    public static void download(String fileName, String filePath,
                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //声明本次下载状态的记录对象
//        DownloadRecord downloadRecord = new DownloadRecord(fileName, filePath, request);
        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        //用于记录以完成的下载的数据量，单位是byte
        long downloadedLength = 0l;
        try {
            //打开本地文件流
            InputStream inputStream = new FileInputStream(filePath);
            //激活下载操作
            OutputStream os = response.getOutputStream();

            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
                downloadedLength += b.length;
            }

            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (Exception e) {
//            downloadRecord.setStatus(DownloadRecord.STATUS_ERROR);
            throw e;
        }
//        downloadRecord.setStatus(DownloadRecord.STATUS_SUCCESS);
//        downloadRecord.setEndTime(new Timestamp(System.currentTimeMillis()));
//        downloadRecord.setLength(downloadedLength);
        //存储记录
    }
    }
