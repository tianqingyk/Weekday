package com.team3.weekday.controller;

import com.team3.weekday.utils.FastDFSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-28
 */
@Controller
public class FastDFSController {

    @RequestMapping("/testUpload")
    public String test(){
        //classpath:/templates/testNodejs.html
        return "test/testUpload";
    }

    @RequestMapping("/testWebUploader")
    public String testWebUploader() {
        return "test/testWebUploader";
    }

    @RequestMapping("/testUpload.do")
    @ResponseBody
    public String upload(@RequestParam(name ="file") MultipartFile file) throws Exception{

       return FastDFSUtil.uploadFile(file);
    }
}
