#include <jni.h>
#include <fcntl.h>

int fd = 0;

JNIEXPORT jint JNICALL Java_com_example_jnidriver_JNIDriver_openDriver(JNIEnv * env, jclass class, jstring path) {
	jboolean iscopy;
	const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
	fd = open(path_utf, O_WRONLY);
	(*env)->ReleaseStringUTFChars(env, path, path_utf);

	if (fd < 0) return -1;
	else return 1;
}

JNIEXPORT void JNICALL Java_com_example_jnidriver_JNIDriver_closeDriver(JNIEnv * env, jobject obj) {
	if (fd > 0) close(fd);
}

JNIEXPORT void JNICALL Java_com_example_jnidriver_JNIDriver_setMotor(JNIEnv * env, jobject o, jchar c) {
	int i = (int) c;

	char temp[4] = {1,1,1,1};

	if (i==0)
		write(fd,temp,4);
	else if (i==1) {
		temp[0] = 0;
		write(fd,temp,4);
		usleep(10000);

		temp[0] = 1;
		temp[1] = 0;
		write(fd,temp,4);
		usleep(10000);

		temp[1] = 1;
		temp[2] = 0;
		write(fd,temp,4);
		usleep(10000);

		temp[2] = 1;
		temp[3] = 0;
		write(fd,temp,4);
		usleep(10000);
		temp[3] = 1;
	} else if (i==2) {
		temp[3] = 0;
		write(fd,temp,4);
		usleep(10000);

		temp[3] = 1;
		temp[2] = 0;
		write(fd,temp,4);
		usleep(10000);

		temp[2] = 1;
		temp[1] = 0;
		write(fd,temp,4);
		usleep(10000);

		temp[1] = 1;
		temp[0] = 0;
		write(fd,temp,4);
		usleep(10000);
		temp[0] = 1;
	}
}
