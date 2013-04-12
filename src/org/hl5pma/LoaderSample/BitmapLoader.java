package org.hl5pma.LoaderSample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BitmapLoader extends AsyncTaskLoader<Bitmap> {

    private static final String TAG = "BitmapLoader";

    private Bitmap mBitmap;
    private String mUrl;

    public BitmapLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public Bitmap loadInBackground() {
        return loadImageFromNetwork(mUrl);
    }

    @Override
    public void deliverResult(Bitmap data) {
        if (isReset()) {
            // 자원해제 [예 cursor.close()]
            return;
        }
        Bitmap oldBitmap = mBitmap;
        mBitmap = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (oldBitmap != null && oldBitmap != data) {
            // 이전 데이터 자원 해제 [예 cursor.close()]
        }
    }

    @Override
    protected void onStartLoading() {
        if (mBitmap != null) {
            deliverResult(mBitmap);
        }

        if (takeContentChanged() || mBitmap == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(Bitmap data) {
        // 자원해제 [예 cursor.close()]
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        // 자원해제 [예 cursor.close()]
    }

    private Bitmap loadImageFromNetwork(String url) {
        Bitmap result = null;
        try {
            InputStream is = new URL(mUrl).openConnection().getInputStream();
            result = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
        }
        return result;
    }

}
