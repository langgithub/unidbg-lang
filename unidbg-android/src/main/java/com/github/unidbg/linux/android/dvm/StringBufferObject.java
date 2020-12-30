package com.github.unidbg.linux.android.dvm;

public class StringBufferObject extends DvmObject<String> {

    public StringBufferObject(VM vm, String value) {
        super(vm.resolveClass("java/lang/StringBuffer"), value);
        if (value == null) {
            throw new NullPointerException();
        }
    }

    public synchronized StringBufferObject append(String str) {
        value += str;
        return this;
    }


    @Override
    public String toString() {
        if (value == null) {
            return null;
        } else {
            return '"' + value + '"';
        }
    }
}