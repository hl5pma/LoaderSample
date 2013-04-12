package org.hl5pma.LoaderSample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class BitmapLoaderActivity extends FragmentActivity {

    private static final String TAG = "BitmapLoaderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            BitmapLoaderFragment f = new BitmapLoaderFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, f).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static class BitmapLoaderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Bitmap> {

        private static final String TAG = "BitmapLoaderFragment";

        private static final int LOADER_ID_BITMAP = 0;

        private EditText mUrlEdit;
        private ImageView mImage;

        private Bitmap mBitmap;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_bitmap_loader, container, false);
            mUrlEdit = (EditText) v.findViewById(R.id.url_edit);
            mImage = (ImageView) v.findViewById(R.id.image);

            v.findViewById(R.id.load_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLoaderManager().restartLoader(LOADER_ID_BITMAP, null, BitmapLoaderFragment.this);
                }
            });
            v.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLoaderManager().destroyLoader(LOADER_ID_BITMAP);
                }
            });

            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Loader loader = getLoaderManager().getLoader(LOADER_ID_BITMAP);
            if (loader != null && loader.isStarted()) {
                getLoaderManager().initLoader(LOADER_ID_BITMAP, null, this);
            }
        }

        @Override
        public Loader<Bitmap> onCreateLoader(int id, Bundle bundle) {
            switch (id) {
                case LOADER_ID_BITMAP:
                    return new BitmapLoader(getActivity(), mUrlEdit.getText().toString());
                default:
                    return null;
            }
        }

        @Override
        public void onLoadFinished(Loader<Bitmap> loader, Bitmap bitmap) {
            if (loader.getId() == LOADER_ID_BITMAP) {
                if (bitmap == null) {
                    Toast.makeText(getActivity(), "이미지 불러오기 실패", Toast.LENGTH_SHORT).show();
                    return;
                }
                mImage.setImageBitmap(bitmap);
            }
        }

        @Override
        public void onLoaderReset(Loader<Bitmap> loader) {
            mImage.setImageBitmap(null);
        }

    }
}
