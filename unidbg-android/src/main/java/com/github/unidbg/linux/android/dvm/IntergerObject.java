package com.github.unidbg.linux.android.dvm;

public class IntergerObject extends DvmObject<Integer> {

    public IntergerObject(VM vm, int value) {
        super(vm.resolveClass("java/lang/Integer"), value);
    }
}