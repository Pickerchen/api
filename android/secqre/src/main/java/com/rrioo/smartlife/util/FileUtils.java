package com.rrioo.smartlife.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileUtils {


    /**
     * 视频缩略图目录
     * @return
     */
    public static String getSnapshotFileDirPath() {
//        File directory = context.getFilesDir();
        File directory = new File(getBaseDir(), "snapshot_temp");
        if (!directory.exists()) {
            boolean mkdir = directory.mkdirs();
        }
        return directory.getAbsolutePath();
    }

    /**
     * 相应did的视频缩略图地址，一个摄像头只保存一张缩略图，用于视频加载时展示
     * @param did
     * @return
     */
    public static String getSnapshotFilePath(String did) {
        File dir = new File(getSnapshotFileDirPath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, did + ".jpg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }

    /**
     * 保存缩略图
     * @param bmp
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public static boolean saveSnapshot(Bitmap bmp, String path) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(path);
        return bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
    }

    /**
     * 根据当前时间生成一个录像文件地址
     * @param did
     * @return
     */
    public static File createRecordFile(String did) {
        File file = new File(getBaseDir(), "record");
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(file, did);
        if (!file.exists())
            file.mkdirs();
        file = new File(file, System.currentTimeMillis() + ".mp4");
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        return file;
    }

    /**
     * 当前app主文件目录地址
     * @return
     */
    private static String getBaseDir() {
        File directory = Environment.getExternalStorageDirectory();
        directory = new File(directory, "sen5/ipcamera");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory.getAbsolutePath();
    }

    /**
     * 根据当前时间生成用户截图文件地址
     * @param did
     * @return
     */
    public static String createScreenShotFile(String did) {
        File file = new File(getBaseDir(), "snapshot");
        if (!file.exists()) file.mkdirs();
        file = new File(file, did);
        if (!file.exists()) file.mkdirs();
        file = new File(file, System.currentTimeMillis() + ".jpg");
        if (!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file.getAbsolutePath();
    }
}
