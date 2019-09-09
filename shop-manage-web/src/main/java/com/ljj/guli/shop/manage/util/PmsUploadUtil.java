package com.ljj.guli.shop.manage.util;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author liujingjie
 * @date 2019/9/8 19:24
 * @since V1.0
 **/
public class PmsUploadUtil {

    private static final String SERVER_URL = "http://192.168.177.129";

    /**
     * 图片上传
     * @param multipartFile
     * @return
     */
    public static String uploadImage(MultipartFile multipartFile){

        // 获取配置文件路径
        String path = PmsUploadUtil.class.getResource("/tracker.conf").getPath();

        try {
            // 加载配置信息（全局）,配置信息中包含服务端信息
            ClientGlobal.init(path);

            // 创建 tracker 客户端
            TrackerClient trackerClient = new TrackerClient();

            // 创建 tracker 服务端，获取与客户端的连接
            TrackerServer trackerServer = trackerClient.getConnection();

            // 创建 storage 客户端
            StorageClient storageClient = new StorageClient(trackerServer,null);

            // 获取文件及文件信息
            // 文件的字节对象
            byte[] bytes = multipartFile.getBytes();
            //文件名称
            String originalFilename = multipartFile.getOriginalFilename();
            // 文件后缀
            String substring = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            // 上传图片
            String[] strings = storageClient.upload_file(bytes, substring, null);

            StringBuilder imgUrl = new StringBuilder(SERVER_URL);
            // 返回图片路径
            for (String string : strings) {
                imgUrl.append("/").append(string);
            }
            return imgUrl.toString();


        } catch (Exception e) {
            e.fillInStackTrace();
        }

        return null;
    }

}
