package android.app;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.*;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import com.github.unidbg.linux.android.dvm.DvmObject;
import com.github.unidbg.linux.android.dvm.VM;

import java.util.List;

public class ApplicationObject extends DvmObject<String> {

    public ApplicationObject(VM vm, String value) {
        super(vm.resolveClass("android/app/Application"), value);
    }
}
