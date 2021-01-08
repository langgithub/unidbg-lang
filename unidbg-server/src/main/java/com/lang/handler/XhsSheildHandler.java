package com.lang.handler;

import com.alibaba.fastjson.JSON;
import com.github.unidbg.Emulator;
import com.github.unidbg.file.FileResult;
import com.lang.platform.xhs.XhsSign;
import com.lang.platform.xhs.w;
import com.lang.platform.xhs.xhsShield;
import com.lang.sekiro.api.SekiroRequest;
import com.lang.sekiro.api.SekiroRequestHandler;
import com.lang.sekiro.api.SekiroResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

public class XhsSheildHandler implements SekiroRequestHandler {

    xhsShield test = null;
    public XhsSheildHandler(xhsShield test){
        this.test = test;
    }

    @Override
    public void handleRequest(SekiroRequest sekiroRequest, SekiroResponse sekiroResponse) {
        String result = "";
        try {
            String url = "";
            String urlLeft = "";
            String cawlName= sekiroRequest.getString("cawlName");
            if ("userinfo".equals(cawlName)){
                String uid= sekiroRequest.getString("uid");
                url = "https://www.xiaohongshu.com/api/sns/v3/user/"+uid+"/info?platform=Android&deviceId=e45ea788-06f6-3d2f-bf6c-03f68152c851&device_fingerprint=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&device_fingerprint1=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&versionName=5.26.2&channel=GooglePlay&sid=session.1608775132099963252820&lang=zh-Hans&t=1608779046&sign=7a588ee508eebcf136eb1f58ab09dbd3";
                urlLeft = "https://www.xiaohongshu.com/api/sns/v3/user/"+uid+"/info";
            }
            if ("upload_contact".equals(cawlName)){
                String phones= sekiroRequest.getString("phones");
//                System.out.println(URLEncoder.encode(w.a("[[\"15775691981\",\"15775691981\"]]","8C3264A3E83D0134D5427EC216FC93D4")));
                System.out.println(phones);
                url = "https://www.xiaohongshu.com/api/sns/v1/system_service/upload_contacts?data="+URLEncoder.encode(w.a(phones,"8C3264A3E83D0134D5427EC216FC93D4"))+"&platform=Android&deviceId=e45ea788-06f6-3d2f-bf6c-03f68152c851&device_fingerprint=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&device_fingerprint1=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&versionName=5.26.2&channel=GooglePlay&sid=session.1608775132099963252820&lang=zh-Hans&t=1608861879&sign=fabb77bcd8381f75f8ddc9193009d1fb";
                urlLeft = "https://www.xiaohongshu.com/api/sns/v1/system_service/upload_contacts";
            }
            if ("contact".equals(cawlName)){
                String phone= sekiroRequest.getString("phone");
                String page= sekiroRequest.getString("page");
                System.out.println(String.format("查询页数：%s， 查询关键字：%s", page, phone));
                url = "https://www.xiaohongshu.com/api/sns/v1/recommend/user/contacts?page="+page+"&keyword="+phone+"&platform=Android&deviceId=e45ea788-06f6-3d2f-bf6c-03f68152c851&device_fingerprint=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&device_fingerprint1=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&versionName=5.26.2&channel=GooglePlay&sid=session.1608775132099963252820&lang=zh-Hans&t=1608865768&sign=b3b2daa11646675df411267d7d219c34";
                urlLeft = "https://www.xiaohongshu.com/api/sns/v1/recommend/user/contacts";
            }
            Map<String, String> map = XhsSign.getParams(url);
            String params = XhsSign.getSheildParams(map);
            System.out.println("sheild sign>>>"+params);
            String sessionId = map.get("sid");
            String deviceId = map.get("deviceId");
            String userAgent = "Dalvik/2.1.0 (Linux; U; Android 6.0.1; Nexus 6P Build/MTC20L) Resolution/1440*2392 Version/5.26.2 Build/5260361 Device/(Huawei;Nexus 6P) NetType/WiFi";
            String shield = test.getShield(params,sessionId,deviceId,userAgent);
            Map<String, String> stringStringMap = XhsSign.resultMap(map, urlLeft, shield);
            result = JSON.toJSONString(stringStringMap);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                test.destroy();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }finally {
            sekiroResponse.success(result);
        }
    }
}
