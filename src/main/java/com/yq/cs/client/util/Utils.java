package com.yq.cs.client.util;

import com.yq.cs.message.struct.Request;

/**
 * Created by nyq on 2017/4/3.
 */
public class Utils {
    public static Request buildRequest(String className, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        Request request = new Request();
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameterTypes(parameterTypes);
        request.setParameters(parameters);
        return request;
    }

    public static boolean isBasicType(Class<?> classType) {
        return classType.equals(Byte.TYPE) || classType.equals(Short.TYPE) || classType.equals(Integer.TYPE) || classType.equals(Long.TYPE) ||
                classType.equals(Double.TYPE) || classType.equals(Float.TYPE)
                || classType.equals(Boolean.TYPE) || classType.equals(Character.TYPE);
    }

    public static Class<?> getClassType(Object obj){
        Class<?> classType = obj.getClass();
        String typeName = classType.getName();
        switch (typeName){
            case "java.lang.Integer":
                return Integer.TYPE;
            case "java.lang.Long":
                return Long.TYPE;
            case "java.lang.Float":
                return Float.TYPE;
            case "java.lang.Double":
                return Double.TYPE;
            case "java.lang.Character":
                return Character.TYPE;
            case "java.lang.Boolean":
                return Boolean.TYPE;
            case "java.lang.Short":
                return Short.TYPE;
            case "java.lang.Byte":
                return Byte.TYPE;
        }

        return classType;
    }
}
