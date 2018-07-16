package com.sdattg.vip.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileUtil {
    private String TAG = this.getClass().getSimpleName();
    public boolean isPathAvailable(String path){
        if(!TextUtils.isEmpty(path)){
            if((new File(path)).exists()){
                return true;
            }else{
                Log.w(TAG, "into initSQLite() new File(rootPath)).exists() is false");
                return false;
            }
        }else{
            Log.w(TAG, "into initSQLite() TextUtils.isEmpty(rootPath) is true");
            return false;
        }
    }

    public static String replaceBy_(String name){
        return name.replace("-", "_");
    }

    public static byte[] createChecksum(String filename) {
        try{
            InputStream fis =  new FileInputStream(filename);
            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            int numRead;
            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);
            fis.close();
            return complete.digest();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Log.d("FileUtil", "into FileNotFoundException");
            return "noHash".getBytes();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            Log.d("FileUtil", "into NoSuchAlgorithmException");
            return "noHash".getBytes();
        }catch (IOException e){
            e.printStackTrace();
            Log.d("FileUtil", "into IOException");
            return "noHash".getBytes();
        }
    }

    public static String getMD5Checksum(String filePath) {
        byte[] b = createChecksum(filePath);
        String result = "";
        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        Log.d("FileUtil", "MD5 result:" + result);
        return result;
    }

    public static File[] orderByName(File[] files) {
        //File file = new File(filePath);
        //File[] files = file.listFiles();
        List fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (File file1 : files) {
            //System.out.println(file1.getName());
        }
        return files;
    }

}
