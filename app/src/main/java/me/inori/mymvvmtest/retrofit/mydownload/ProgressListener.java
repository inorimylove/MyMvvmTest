package me.inori.mymvvmtest.retrofit.mydownload;

/**
 * Created by hjx on 2018/1/15.
 */

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
