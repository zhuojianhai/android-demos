# android-demos
Error:No toolchains found in the NDK toolchains folder for ABI with prefix: mips64el-linux-android
    原因：
        This version of the NDK is incompatible with the Android Gradle plugin
           version 3.0 or older. If you see an error like
           `No toolchains found in the NDK toolchains folder for ABI with prefix: mips64el-linux-android`,
           update your project file to [use plugin version 3.1 or newer]. You will also
           need to upgrade to Android Studio 3.1 or newer.

        也就是说新版本的NDK与3.0及以前旧版的Android Gradle plugin插件不兼容.
        我本机的studio是3.0.1的所以出现这个问题
    解决方案：
        dependencies {
            classpath 'com.android.tools.build:gradle:3.1.0'

            // NOTE: Do not place your application dependencies here; they belong
            // in the individual module build.gradle files
        }
        ---------------------
        作者：vocanicy
        来源：CSDN
        原文：https://blog.csdn.net/vocanicy/article/details/83004626
