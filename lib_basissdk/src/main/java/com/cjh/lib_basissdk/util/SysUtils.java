package com.cjh.lib_basissdk.util;

/**
 * 提供些获取系统信息相关的工具方法
 */
public class SysUtils {
	
	/**
     * JVM的版本
     */
    public static final String JVM_VERSION     = PropertiesUtils.key("java.version");
    /**
     * JVM的编码
     */
    public static final String JVM_ENCODING    = PropertiesUtils.key("file.encoding");
    /**
     * JVM默认的临时目录
     */
    public static final String JVM_TEMPDIR     = PropertiesUtils.key("java.io.tmpdir");
	

    /**
     * 主机架构
     */
    public static String OS_ARCH           = PropertiesUtils.key("os.arch");
    /**
     * 主机类型
     */
    public static String OS_NAME           = PropertiesUtils.key("os.name");
    /**
     * 主机类型版本
     */
    public static String OS_VERSION        = PropertiesUtils.key("os.version");
    
    
    /**
     * 当前用户
     */
    public static String CURRENT_USER      = PropertiesUtils.key("user.name");
    /**
     * 当前用户的家目录
     */
    public static String CURRENT_USER_HOME = PropertiesUtils.key("user.home");
    /**
     * 当用用户的工作目录
     */
    public static String CURRENT_USER_DIR  = PropertiesUtils.key("user.dir");
    public static String FILE_SEPARATOR    = PropertiesUtils.key("file.separator");
    public static String PATH_SEPARATOR    = PropertiesUtils.key("path.separator");
    public static String LINE_SEPARATOR    = PropertiesUtils.key("line.separator");
    
    private static int kb = 1024;
    
    /**
     * 获取JVM内存总量
     *
     */
    public final static long JVMtotalMem() {
        return Runtime.getRuntime().totalMemory() / kb;
    }

    /**
     * 虚拟机空闲内存量
     *
     */
    public final static long JVMfreeMem() {
        return Runtime.getRuntime().freeMemory() / kb;
    }

    /**
     * 虚拟机使用最大内存量
     *
     */
    public final static long JVMmaxMem() {
        return Runtime.getRuntime().maxMemory() / kb;
    }
}
