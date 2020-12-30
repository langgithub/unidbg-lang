package com.lang.platform.xhs;

import android.net.Uri;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class XhsSign {
    public static void main(String[] args) throws Exception {
        // get
        String url = "https://www.xiaohongshu.com/api/sns/v3/user/5ca227310000000011005682/info?platform=Android&deviceId=e45ea788-06f6-3d2f-bf6c-03f68152c851&device_fingerprint=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&device_fingerprint1=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&versionName=5.26.2&channel=GooglePlay&sid=session.1608775132099963252820&lang=zh-Hans&t=1608779046&sign=7a588ee508eebcf136eb1f58ab09dbd3";
        url  = "https://www.xiaohongshu.com/api/sns/v1/system_service/upload_contacts?data=0W/MaAqyo2GqdDlMuPnXyUtdrC2okK8/9rs1qMV4iQnb8HhEh+6MH4aWQf0SgJbdtZOw6BIHVDv9L65O6c92Vg==&platform=Android&deviceId=e45ea788-06f6-3d2f-bf6c-03f68152c851&device_fingerprint=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&device_fingerprint1=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&versionName=5.26.2&channel=GooglePlay&sid=session.1608775132099963252820&lang=zh-Hans&t=1608801122";
//        "channel=GooglePlaydata=0W/MaAqyo2GqdDlMuPnXyfge6ncEkH0jmxQC/FSX+Hk=deviceId=e45ea788-06f6-3d2f-bf6c-03f68152c851device_fingerprint=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9adevice_fingerprint1=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9alang=zh-Hansplatform=Androidsid=session.1608775132099963252820sign=9465cc682efd840b99183c9f95d1418et=1608800016url=/api/sns/v1/system_service/upload_contactsversionName=5.26.2"
        Map<String, String> params = getParams(url);
        System.out.println(getSheildParams(params));





    }
    public static void te() throws UnsupportedEncodingException {

    }

    /**
     * test
     * @return
     * @throws Exception
     */
    public static Map<String, String> getParams(String params) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        URL url = new URL(params);
        String queryParam = url.getQuery();
        for (String param : queryParam.split("&")) {
            String[] mStr = param.split("=");
            if("data".equals(mStr[0])){
                map.put(mStr[0], URLDecoder.decode(mStr[1],"utf-8"));
            }else {
                if(mStr.length == 1){
                    map.put(mStr[0],"");
                }else {
                    map.put(mStr[0],mStr[1]);
                }

            }
            System.out.println(String.format("map.put(\"%s\",\"%s\");", mStr[0],map.get(mStr[0])));
        }
        String signOld = map.remove("sign");
        String signNew = XhsSign.a(map);
        System.out.println(signOld);
        System.out.println(signNew);
        if(!signOld.equals(signNew)){
            System.out.println("<<<<<<<<<<<<<<<<<当前接口sign签名不正确，注意检查>>>>>>>>>>>>>>>>>>>");
        }
//        String signT = map.remove("t");
//        System.out.println("remove t="+signT);
//         计算新sign
//        map.put("t",String.valueOf(System.currentTimeMillis()/1000));
        map.put("sign", XhsSign.a(map));
        map.put("url", url.getPath());
        return map;
    }

    public static String a(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        String str = map.get("deviceId");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(map.keySet());
        Collections.sort(arrayList);
        StringBuilder sb = new StringBuilder();
        for (String str2 : arrayList) {
            sb.append(str2);
            sb.append("=");
            sb.append(map.get(str2));
        }
        byte[] bArr = new byte[1];
        try {
            System.out.println(sb.toString());
            System.out.println(ac.a(sb.toString(), "UTF-8"));
            bArr = ac.a(sb.toString(), "UTF-8").getBytes();
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        byte[] bytes = str.getBytes();
        StringBuilder sb2 = new StringBuilder();
        int i = 0;
        for (byte b2 : bArr) {
            sb2.append(b2 ^ bytes[i]);
            i = (i + 1) % bytes.length;
        }
        return l.a(l.a(sb2.toString()).toLowerCase() + str).toLowerCase();
    }


    public static String getSheildParams(Map<String, String> map) {
        ArrayList<String> arrayList = new ArrayList();
        arrayList.addAll(map.keySet());
        Collections.sort(arrayList);
        StringBuilder sb = new StringBuilder();
        for (String str : arrayList) {
            sb.append(str);
            sb.append("=");
            sb.append(map.get(str));
        }
        return sb.toString();
    }

    public static Map<String,String> resultMap(Map<String, String> map,String urlLeft, String sheild){
        map.remove("url");
        ArrayList<String> arrayList = new ArrayList();
        arrayList.addAll(map.keySet());
        StringBuilder sb = new StringBuilder();
        for (String str : arrayList) {
            sb.append(str);
            sb.append("=");
            sb.append(map.get(str));
            sb.append("&");
        }

        String params = sb.toString();
        HashMap<String,String> resultM = new HashMap<>();
        resultM.put("url",urlLeft);
        resultM.put("params", params.substring(0,params.length()-1));
        resultM.put("authorization",map.get("sid"));
        resultM.put("device_id",map.get("deviceId"));
        resultM.put("user-agent","Dalvik/2.1.0 (Linux; U; Android 6.0.1; Nexus 6P Build/MTC20L) Resolution/1440*2392 Version/5.26.2 Build/5260361 Device/(Huawei;Nexus 6P) NetType/WiFi");
        resultM.put("sheild",sheild);
        return resultM;
    }
}
