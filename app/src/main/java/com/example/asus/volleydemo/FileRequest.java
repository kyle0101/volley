package com.example.asus.volleydemo;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * Created by asus on 2016/12/19.
 * 自定义的Volley请求文件网络请求
 */
public class FileRequest extends Request<String> {


    private Response.Listener<String> mListener;
    private FileOutputStream mOutput;
    private BufferedOutputStream mBufferedOutput;
    private String mContentType;
    private String mFileName;

    public FileRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorlistener) {
        super(method, url, errorlistener);
        mListener = listener;
    }

    public FileRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorlistener) {
        this(Method.POST, url, listener, errorlistener);


    }

    public String getContentType() {
        return mContentType;
    }

    public String getFileName() {
        return mFileName;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {

        byte[] data = response.data;
        Map<String, String> header = response.headers;
        mContentType = header.get("Content-Type");
        String a = header.get("Content-Disposition");
        if (a != null && a.length() >= 23) {
            String s = header.get("Content-Disposition").substring(22);
            mFileName = s.substring(0, s.length() - 1);
        } else {
            mFileName = null;
        }
        String parsed;
        try {
            //  parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            parsed = ByteToStringUtils.bytesToHexString(data);

        } catch (Exception e) {
            parsed = ByteToStringUtils.bytesToHexString(data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));

    }

    @Override
    protected void deliverResponse(String response) {
        Log.i("set", " deliverResponse" + response.toString());
        mListener.onResponse(response);
    }
}
