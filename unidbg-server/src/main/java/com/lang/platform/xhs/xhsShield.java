package com.lang.platform.xhs;

import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.Emulator;
import com.github.unidbg.LibraryResolver;
import com.github.unidbg.Module;
import com.github.unidbg.file.FileResult;
import com.github.unidbg.file.IOResolver;
import com.github.unidbg.linux.android.AndroidARMEmulator;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.*;
import com.github.unidbg.linux.android.dvm.array.ByteArray;
import com.github.unidbg.memory.Memory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

//xhs sheild unidbg调用
//        String params = "channel=YingYongBaodeviceId=ec258cae-33c4-35ca-a909-67bf45c0f73edevice_fingerprint=20191123001415aae06280fc655b5c963e9db249e364ce0160af67ab7f3c61device_fingerprint1=20191123001415aae06280fc655b5c963e9db249e364ce0160af67ab7f3c61filters=[]keyword=你号呀1lang=zh-Hanspage=1page_size=20platform=Androidsearch_id=EED2BE2760DAA985FCA3CE9DA636172Asid=session.1569218578701927068721sign=de46015c85b29d8cff44c3fd1535b28asort=source=search_result_notest=1575460839url=/api/sns/v8/search/notesversionName=5.26.0";
//        String sessionId = "session.1576053277833681415997";
//        String deviceId = "b69a9d77-5e0c-3341-beef-b8c40fde38a6";
//        String userAgent = "Dalvik/2.1.0 (Linux; U; Android 6.0.1; Nexus 5 Build/M4B30Z) Resolution/1080*1776 Version/5.26.0 Build/5260254 Device/(LGE;Nexus 5) NetType/WiFi";
public abstract class xhsShield extends AbstractJni implements IOResolver {

    private static LibraryResolver createLibraryResolver() {
        return new AndroidResolver(23);
    }

    private static AndroidEmulator createARMEmulator() {
        return new AndroidARMEmulator("com.xingin.xhs");
    }

    private final AndroidEmulator emulator;
    private final VM vm;
    private final Module module;
    private final DvmClass RedHttpInterceptor;
    //    private final String soName = "libshield.so";                     //5.44.0
    //    private final String soName = "libshield_update.so";              //5.44.0
    //    private final String soName = "libshield5.26.0.so";               //5.26.0
    //    private final String soName = "libshield5.26.0_update.so";        //5.26.0
    //    private final String soName = "libshield5.26.0_updateS1.so";        //5.26.0
    //    private final String soName = "libshield5.26.0_update2.so";        //5.26.0
    private String shield = null;
    private String params = null;
    private String sessionId = null;
    private String deviceId = null;
    private String userAgent = null;
    private String libName = "libshield526.so";
    private Boolean flag = false;

    public xhsShield() throws IOException {
        emulator = createARMEmulator();
        emulator.getSyscallHandler().addIOResolver(this);
        Memory memory = emulator.getMemory();
        memory.setLibraryResolver(createLibraryResolver());


//        vm = emulator.createDalvikVM(new File("/Users/yuanlang/work/java/project/unidbg-lang/unidbg-server/src/main/resources/xhs/xhs526.apk"));
//        vm.setJni(this);
//        DalvikModule dm = vm.loadLibrary(new File("/Users/yuanlang/work/java/project/unidbg-lang/unidbg-server/src/main/resources/xhs/libshield526.so"), false);

        vm = emulator.createDalvikVM(new File("/data/unidbg/xhs526.apk"));
        vm.setJni(this);
        DalvikModule dm = vm.loadLibrary(new File("/data/unidbg/libshield526.so"), false);
        dm.callJNI_OnLoad(emulator);
        module = dm.getModule();
        RedHttpInterceptor = vm.resolveClass("com/xingin/shield/http/RedHttpInterceptor");
        RedHttpInterceptor.callStaticJniMethod(emulator, "initializeNative()V");
    }

    public String getShield(String params, String sessionId, String deviceId, String userAgent) {
        this.params = params;
        this.sessionId = sessionId;
        this.deviceId = deviceId;
        this.userAgent = userAgent;

        DvmClass Chain = vm.resolveClass("okhttp3/Interceptor$Chain");
        RedHttpInterceptor.newObject(null).callJniMethod(emulator, "process(Lokhttp3/Interceptor$Chain;)Lokhttp3/Response;", Chain.newObject(null));
        return shield;
    }

    public void destroy() throws IOException {
        emulator.close();
    }

    @Override
    public DvmObject callObjectMethodV(BaseVM vm, DvmObject dvmObject, String signature, VaList vaList) {
        System.out.println("call: " + signature);
        switch (signature) {
            case "com/xingin/shield/http/RedHttpInterceptor->deviceId()Ljava/lang/String;":
                return new StringObject(vm, deviceId);
            case "com/xingin/shield/http/RedHttpInterceptor->sessionId()Ljava/lang/String;":
                return new StringObject(vm, sessionId);
            case "com/xingin/shield/http/RedHttpInterceptor->userAgent()Ljava/lang/String;":
                return new StringObject(vm, userAgent);
            case "okhttp3/Interceptor$Chain->request()Lokhttp3/Request;":
                DvmClass clazz = vm.resolveClass("okhttp3/Request");
                return clazz.newObject(null);
            case "okhttp3/Request->newBuilder()Lokhttp3/Request$Builder;":
                clazz = vm.resolveClass("okhttp3/Request$Builder");
                return clazz.newObject(null);
            case "com/xingin/shield/http/RedHttpInterceptor->getBytesOfParams(Lokhttp3/Request;)[B":
                byte[] bytes = (params).getBytes();
                return new ByteArray(vm,bytes);
            case "okhttp3/Request$Builder->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;":
                StringObject name = vaList.getObject(0);
                StringObject value = vaList.getObject(4);
                if (name.getValue().equals("shield")) {
                    shield = value.getValue();
                }
                System.err.println("okhttp3/Request$Builder->header name=" + name.getValue() + ", value=" + value.getValue());
                return dvmObject;
            case "okhttp3/Request$Builder->build()Lokhttp3/Request;":
                clazz = vm.resolveClass("okhttp3/Request");
                return clazz.newObject(null);
            case "okhttp3/Interceptor$Chain->proceed(Lokhttp3/Request;)Lokhttp3/Response;":
                clazz = vm.resolveClass("okhttp3/Response");
                return clazz.newObject(null);
        }

        return super.callObjectMethodV(vm, dvmObject, signature, vaList);
    }


    @Override
    public int callIntMethodV(BaseVM vm, DvmObject dvmObject, String signature, VaList vaList) {
        if ("okhttp3/Response->code()I".equals(signature)) {
            return 200;
        }
        return super.callIntMethodV(vm, dvmObject, signature, vaList);
    }

    public static void main(String[] args) throws Exception {

        xhsShield test = new xhsShield() {
            @Override
            public FileResult resolve(Emulator emulator, String pathname, int oflags) {
                return null;
            }
        };

        String url = "https://www.xiaohongshu.com/api/sns/v5/note/5fec96fa00000000010045a9/comment/list?start=&num=3&show_priority_sub_comments=1&platform=Android&deviceId=e45ea788-06f6-3d2f-bf6c-03f68152c851&device_fingerprint=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&device_fingerprint1=2020113017502087b8779cc6128a65eebd7ad984d6994801bf8ba1b5e84b9a&versionName=5.26.2&channel=GooglePlay&sid=session.1609234760057874883808&lang=zh-Hans&t=1610009987&sign=087b61019db28fda036b5a119e97d1e4";
        Map<String, String> map = XhsSign.getParams(url);
        String params = XhsSign.getSheildParams(map);
        System.out.println(params);
        String sessionId = map.get("sid");
        System.out.println(sessionId);
        String deviceId = map.get("deviceId");
        System.out.println(deviceId);
        String userAgent = "Dalvik/2.1.0 (Linux; U; Android 6.0.1; Nexus 6P Build/MTC20L) Resolution/1440*2392 Version/5.26.2 Build/5260361 Device/(Huawei;Nexus 6P) NetType/WiFi";
        System.out.println(test.getShield(params, sessionId, deviceId, userAgent));
        Thread.sleep(5000);

        test.destroy();
    }
}