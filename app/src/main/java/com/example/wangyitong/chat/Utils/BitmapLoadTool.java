package com.example.wangyitong.chat.Utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by wangyitong on 2016/5/24.
 */
public class BitmapLoadTool {
    private static BitmapLoadTool sTool = new BitmapLoadTool();
    private BitmapLoadTool() {
    }

    public static BitmapLoadTool getInstance() {
        return sTool;
    }

    public void showImageByUrl(ImageView view, String url ) {
        Bitmap bm = BitmapUtils.getBitmapFromLocal(BitmapUtils.getBitmapLocalName(url));
        if (bm != null) {
            view.setImageBitmap(bm);
            return;
        } else {
            new BitmapLoadTask(view).execute(url);
        }
    }

    public Bitmap getImageByUrl(String url ) {
        Bitmap bm = BitmapUtils.getBitmapFromLocal(BitmapUtils.getBitmapLocalName(url));
        if (bm == null)  {
            bm = BitmapUtils.getBitmapFromUrl(url) ;
        }
        return bm;
    }

    class BitmapLoadTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView mImageView;

        public BitmapLoadTask(ImageView view) {
            LogUtils.d("no local file,start loading from net");
            mImageView = view;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return BitmapUtils.getBitmapFromUrl(params[0]);
        }

        @Override
        protected void onPostExecute(final Bitmap bitmap) {
            ThreadUtils.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mImageView.setImageBitmap(bitmap);
                }
            });
        }
    }
}
