#include <jni.h>
#include <string>

// 宏定义
# define XONGFUNC(name)Java_com_jetpack_xhb_##name

//so文件生成路径 ./app/build/intermediates/cmake/debug/obj

extern "C" JNIEXPORT jstring

JNICALL
XONGFUNC(MainActivity_stringFromJNI)(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
