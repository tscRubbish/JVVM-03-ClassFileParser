package com.njuse.seecjvm.memory.jclass;

import com.njuse.seecjvm.classloader.classfileparser.MethodInfo;
import com.njuse.seecjvm.classloader.classfileparser.attribute.CodeAttribute;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Method extends ClassMember {
    private int maxStack;
    private int maxLocal;
    private int argc;
    private byte[] code;

    public int getMaxLocal() {
        return maxLocal;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getArgc() {
        return argc;
    }

    public byte[] getCode() {
        return code;
    }

    public Method(MethodInfo info, JClass clazz) {
        this.clazz = clazz;
        accessFlags = info.getAccessFlags();
        name = info.getName();
        descriptor = info.getDescriptor();

        CodeAttribute codeAttribute = info.getCodeAttribute();
        if (codeAttribute != null) {
            maxLocal = codeAttribute.getMaxLocal();
            maxStack = codeAttribute.getMaxStack();
            code = codeAttribute.getCode();
        }
        argc = calculateArgcFromDescriptor(descriptor);
    }
    //todo calculateArgcFromDescriptor
    private int calculateArgcFromDescriptor(String descriptor) {
        /**
         * Add some codes here.
         * Here are some examples in README!!!
         *
         * You should refer to JVM specification for more details
         *
         * Beware of long and double type
         */
        int len=descriptor.length(),sum=0;
        for (int i=0;i<len;i++) {
            char ch=descriptor.charAt(i);
            switch (ch){
                case 'J':
                case 'D':{
                    sum+=2;
                    break;
                }
                case 'L':{
                    sum+=1;
                    while (descriptor.charAt(i)!=';') i++;
                    break;
                }
                case '(':break;
                case ')':{
                    i=len;break;
                }
                case '[':break;
                default:{
                    sum+=1;
                    break;
                }
            }
        }
        //System.out.println("Des="+getDescriptor());
        //System.out.println("Name="+getName());
        //System.out.println("sum="+sum);
        return sum;
    }
}
