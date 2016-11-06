package com.tony.router.util;

import android.util.Log;

/**
 * Created by tony on 11/6/16.
 */
public class RouterLogUtils {
    private static boolean isPrint = true;
    private static String className;//类名
    private static String methodName;//方法名
    private static int lineNumber;//行数

    private RouterLogUtils(){
        throw new AssertionError();
    }

    /**
     * 根据应用自行判断Debug状态
     */
    private static boolean isPrint() {
        return RouterLogUtils.isPrint;
    }

    private static String createLog(String log ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")==>");
        buffer.append(log);
        return buffer.toString();
    }

    /**
     * 获取当前Log所处的位置
     * @param sElements
     */
    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void e(Object arg){
        if (!isPrint())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(String.valueOf(arg)));
    }


    public static void i(Object arg){
        if (!isPrint())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(String.valueOf(arg)));
    }

    public static void d(Object arg){
        if (!isPrint())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(String.valueOf(arg)));
    }

    public static void v(Object arg){
        if (!isPrint())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(String.valueOf(arg)));
    }

    public static void w(Object arg){
        if (!isPrint())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(String.valueOf(arg)));
    }

    public static void wtf(Object arg){
        if (!isPrint())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(String.valueOf(arg)));
    }


    public static void e(String tag, Object arg){
        if (!isPrint())
            return;
        Log.e(tag, createLog(String.valueOf(arg)));
    }


    public static void i(String tag, Object arg){
        if (!isPrint())
            return;
        Log.i(tag, createLog(String.valueOf(arg)));
    }

    public static void d(String tag, Object arg){
        if (!isPrint())
            return;
        Log.d(tag, createLog(String.valueOf(arg)));
    }

    public static void v(String tag, Object arg){
        if (!isPrint())
            return;
        Log.v(tag, createLog(String.valueOf(arg)));
    }

    public static void w(String tag, Object arg){
        if (!isPrint())
            return;
        Log.w(tag, createLog(String.valueOf(arg)));
    }

    public static void wtf(String tag, Object arg){
        if (!isPrint())
            return;
        Log.wtf(tag, createLog(String.valueOf(arg)));
    }
}
