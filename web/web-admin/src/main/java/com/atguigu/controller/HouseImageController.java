package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.FileUtil;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Date : 2022/5/20
 * Author : cc
 * Description :
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController {

    @Reference
    private HouseImageService houseImageService;

    private final static String PAGE_UPLOAD_SHOW = "house/upload";
    private static final String PAGE_ACTION = "redirect:/house/";

    @GetMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable("houseId")Long houseId, @PathVariable("type")Integer type, Model model){

        model.addAttribute("houseId",houseId);

        model.addAttribute("type",type);

        return PAGE_UPLOAD_SHOW;

    }

    @PostMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable("houseId")Long houseId, @PathVariable("type")Integer type, @RequestParam("file")MultipartFile[] multipartFiles) throws IOException {

        for (MultipartFile multipartFile : multipartFiles) {

            //1.将图片上传到七牛云
            //1.1获取文件名
            String filename = multipartFile.getOriginalFilename();

            //1.2生成唯一文件名
            String uuidName = FileUtil.getUUIDName(filename);

            //1.3将文件上传到七牛云
            QiniuUtils.upload2Qiniu(multipartFile.getBytes(),uuidName);

            //2.将文件的url保存到数据库
            //2.1拼接图片的url
            String url = QiniuUtils.getUrl(uuidName);

            //2.2创建图片对象
            HouseImage houseImage = new HouseImage();

            houseImage.setImageName(uuidName);

            houseImage.setImageUrl(url);

            houseImage.setHouseId(houseId);

            houseImage.setType(type);

            //2.3保存到数据库
            houseImageService.insert(houseImage);


        }

        return Result.ok();

    }

    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId")Long houseId,@PathVariable("id")Long id,Model model){
        
        //从七牛云删除
        HouseImage houseImage = houseImageService.getById(id);

        QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());
        
        //从后端删除
        houseImageService.delete(id);
        
        return PAGE_ACTION + houseId;

    }

}
