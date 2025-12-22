package com.dua3.app.keystoremanager;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;

public class PanamaTrigger {
    public static void main(String[] args) {
        try {
            Linker linker = Linker.nativeLinker();
            SymbolLookup stdlib = linker.defaultLookup();
            linker.downcallHandle(
                stdlib.find("printf").get(),
                FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG)
            );
            System.out.println("Panama handle created successfully.");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
