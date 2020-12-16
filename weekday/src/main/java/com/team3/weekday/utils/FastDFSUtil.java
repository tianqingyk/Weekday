package com.team3.weekday.utils;

import org.csource.fastdfs.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-28
 */
public class FastDFSUtil {

    public static TrackerServer trackerServer = null;
    public static TrackerClient trackerClient = null;
    public static StorageServer storageServer = null;
    public static StorageClient storageClient = null;

    public static String address = null;

    static {

        try {
            ClientGlobal.init("fdfs_client.conf");
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getTrackerServer();
            storageClient = new StorageClient(trackerServer, storageServer);
            address = "http:/" + trackerServer.getInetSocketAddress().getAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String uploadFile(MultipartFile file) throws Exception{
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".")+1);


        String[] pngs = storageClient.upload_file(file.getBytes(), suffix, null);
        StringBuilder sb = new StringBuilder();
        sb.append(address).append("/");
        for(String s : pngs ){
            sb.append(s).append("/");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
