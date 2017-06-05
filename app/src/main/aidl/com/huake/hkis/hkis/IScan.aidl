// IScan.aidl
package com.huake.hkis.hkis;

// Declare any non-default types here with import statements

interface IScan {
    //初始化扫描头
    void initEngine();
    //扫描
    String scan();
    //关闭扫描
    void close();
}
