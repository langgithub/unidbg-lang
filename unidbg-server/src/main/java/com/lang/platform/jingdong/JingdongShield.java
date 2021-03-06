package com.lang.platform.jingdong;

import android.app.Application;
import android.app.ApplicationObject;
import android.content.Context;
import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.Emulator;
import com.github.unidbg.LibraryResolver;
import com.github.unidbg.Module;
import com.github.unidbg.file.FileResult;
import com.github.unidbg.file.IOResolver;
import com.github.unidbg.linux.android.AndroidARMEmulator;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.*;
import com.github.unidbg.linux.android.dvm.array.ArrayObject;
import com.github.unidbg.linux.android.dvm.array.ByteArray;
import com.github.unidbg.memory.Memory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public abstract class JingdongShield<callObjectMethod> extends AbstractJni implements IOResolver {

    private static LibraryResolver createLibraryResolver() {
        return new AndroidResolver(23);
    }

    private static AndroidEmulator createARMEmulator() {
        return new AndroidARMEmulator("com.jingdong.app.mall");
    }

    private final AndroidEmulator emulator;
    private final VM vm;
    private final Module module;
    private final DvmClass BitmapkitUtils;

    public JingdongShield() throws IOException {
        emulator = createARMEmulator();
        emulator.getSyscallHandler().addIOResolver(this);
        Memory memory = emulator.getMemory();
        memory.setLibraryResolver(createLibraryResolver());

        vm = emulator.createDalvikVM(new File("/Users/yuanlang/work/java/project/unidbg-lang/unidbg-server/src/main/resources/jingdong/京东.apk"));
//        vm = emulator.createDalvikVM(null);
        vm.setJni(this);
        vm.setVerbose(true); // 设置是否打印Jni调用细节
        DalvikModule dm = vm.loadLibrary(new File("/Users/yuanlang/work/java/project/unidbg-lang/unidbg-server/src/main/resources/jingdong/libjdbitmapkit.so"), false);
        dm.callJNI_OnLoad(emulator);
        module = dm.getModule();
        BitmapkitUtils = vm.resolveClass("com/jingdong/common/utils/BitmapkitUtils");
    }

    public String getSignFromJni(Context context, String arg2, String arg3, String arg4, String arg5, String arg6) {
        vm.resolveClass("com/jingdong/common/utils/BitmapkitUtils");
        DvmObject<?> dvmObject = BitmapkitUtils.callStaticJniMethodObject(emulator, "getSignFromJni(Ljava/lang/String;)Ljava/lang/String;");
        System.out.println(dvmObject.getValue());
        return "";
    }

    public void destroy() throws IOException {
        emulator.close();
    }

    @Override
    public DvmObject<?> newObjectV(BaseVM vm, DvmClass dvmClass, String signature, VaList vaList) {
        System.out.println("call: " + signature);
        switch (signature) {
            case "java/lang/StringBuffer-><init>()V":
                return vm.resolveClass("java/lang/StringBuffer").newObject(new StringBuffer());
            case "java/lang/Integer-><init>(I)V":
                int anInt = vaList.getInt(0);
                return vm.resolveClass("java/lang/Integer").newObject(new Integer(anInt));
//                return new IntergerObject(vm, 0);
        }
        return super.newObjectV(vm, dvmClass, signature, vaList);
    }

    @Override
    public DvmObject callObjectMethodV(BaseVM vm, DvmObject dvmObject, String signature, VaList vaList) {
        System.out.println("call: " + signature);
        switch (signature) {
            case "java/lang/StringBuffer->append(Ljava/lang/String;)Ljava/lang/StringBuffer;":
                StringObject object = vaList.getObject(0);
                if (object == null){
                    return dvmObject;
                }else {
                    return vm.resolveClass("java/lang/StringBuffer").newObject(((StringBuffer)dvmObject.getValue()).append(object.getValue()));
                }
            case "java/lang/Integer->toString()Ljava/lang/String;":
                return new StringObject(vm, String.valueOf(dvmObject.getValue()));

            case "java/lang/StringBuffer->toString()Ljava/lang/String;":
                return new StringObject(vm, dvmObject.getValue().toString());
        }

        return super.callObjectMethodV(vm, dvmObject, signature, vaList);
    }

    @Override
    public DvmObject<?> callObjectMethod(BaseVM vm, DvmObject<?> dvmObject, String signature, VarArg varArg) {
        System.out.println("call: " + signature);
        switch (signature) {
            case "android/app/Application->getPackageManager()Landroid/content/pm/PackageManager;":
                DvmClass clazz = vm.resolveClass("android/content/pm/PackageManager");
                return clazz.newObject(signature);
            case "android/app/Application->getPackageName()Ljava/lang/String;":
//                String packageName = vm.getPackageName();
                return new StringObject(vm, "com.jingdong.app.mall");
            case "android/app/Application->getApplicationInfo()Landroid/content/pm/ApplicationInfo;":
                DvmClass clazz1 = vm.resolveClass("android/content/pm/ApplicationInfo");
                return clazz1.newObject(signature);
            case "sun/security/pkcs/PKCS7->getCertificates()[Ljava/security/cert/X509Certificate;":
                DvmClass clazz2 = vm.resolveClass("java/security/cert/X509Certificate");
                return new ArrayObject(clazz2.newObject(signature));


        }
        return super.callObjectMethod(vm, dvmObject, signature, varArg);
    }

    @Override
    public DvmObject<?> getStaticObjectField(BaseVM vm, DvmClass dvmClass, String signature) {
        System.out.println("call: " + signature);
        switch (signature) {
            case "com/jingdong/common/utils/BitmapkitUtils->a:Landroid/app/Application;":
                return new ApplicationObject(vm, signature);
        }
        return super.getStaticObjectField(vm, dvmClass, signature);
    }

    @Override
    public DvmObject<?> callStaticObjectMethod(BaseVM vm, DvmClass dvmClass, String signature, VarArg varArg) {
        System.out.println("call: " + signature);
        switch (signature) {
            case "com/jingdong/common/utils/BitmapkitZip->unZip(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)[B":
                return new ByteArray(vm,"ok".getBytes());
            case "com/jingdong/common/utils/BitmapkitZip->objectToBytes(Ljava/lang/Object;)[B":
                return new ByteArray(vm,"ok".getBytes());
        }
        return super.callStaticObjectMethod(vm, dvmClass, signature, varArg);
    }

    @Override
    public DvmObject<?> newObject(BaseVM vm, DvmClass dvmClass, String signature, VarArg varArg) {
        System.out.println("call: " + signature);
        switch (signature) {
            case "sun/security/pkcs/PKCS7-><init>([B)V":
                DvmClass clazz1 = vm.resolveClass("sun/security/pkcs/PKCS7");
                return clazz1.newObject(signature);
        }
        return super.newObject(vm, dvmClass, signature, varArg);
    }

    @Override
    public DvmObject<?> getObjectField(BaseVM vm, DvmObject<?> dvmObject, String signature) {
        System.out.println("call: " + signature);
        switch (signature) {
            case "android/content/pm/ApplicationInfo->sourceDir:Ljava/lang/String;":
                return new StringObject(vm, "/data/data/com.jingdong.app.mall");
        }
        return super.getObjectField(vm,dvmObject,signature);
    }

    @Override
    public int callIntMethodV(BaseVM vm, DvmObject dvmObject, String signature, VaList vaList) {
        if ("okhttp3/Response->code()I".equals(signature)) {
            return 200;
        }
        return super.callIntMethodV(vm, dvmObject, signature, vaList);
    }

    public static void main(String[] args) throws Exception {

        JingdongShield test = new JingdongShield() {
            @Override
            public FileResult resolve(Emulator emulator, String pathname, int oflags) {
                return null;
            }
        };
        System.out.println(test.getSignFromJni(new Context(), "2","3","4","5","6"));
        test.destroy();
    }
}