package it.artform.web;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public DownloadImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    public Bitmap doInBackground(String... urls) {
        String imgUris = urls[0];
        Bitmap bmp = null;
        try {
            InputStream in = new  java.net.URL(imgUris).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }

    public void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
