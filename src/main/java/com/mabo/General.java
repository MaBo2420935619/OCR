package com.mabo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mabo.AuthService;
import com.mabo.BaseImg64;
import com.mabo.HttpUtil;

import java.io.File;
import java.net.URLEncoder;

/**
 * OCR 通用识别
 */
public class General {

    public static String checkFile(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new NullPointerException("图片不存在");
        }
        String image = BaseImg64.getImageStrFromPath(path);

        return image;
    }


    public static void main(String[] args) {
        // 通用识别url
        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
        // 本地图片路径
        String filePath = "F:\\测试文件\\test\\test2.jpg";

        try {
            String imgStr = BaseImg64.getImageStrFromPath(filePath);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + imgStr;
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = AuthService.getAuth();
            String result = HttpUtil.post(otherHost, accessToken, params);
            System.out.println(result);

            //解析JSON串
            JSONObject data = JSONObject.parseObject(result);
            JSONArray jsonArray = data.getJSONArray("words_result");
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jObject=jsonArray.getJSONObject(i);
                String str = jObject.getString("words");
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
