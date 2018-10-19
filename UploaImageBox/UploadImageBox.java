package com.boraq.sparaat.customviews;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.boraq.sparaat.R;
import com.boraq.sparaat.app.SparaatApp;
import com.boraq.sparaat.http.ConnectionHandler;
import com.boraq.sparaat.http.HttpResponse;
import com.boraq.sparaat.models.FileResponse;

import java.util.ArrayList;

public class UploadImageBox extends LinearLayout {


    ImageView ivAddedImage;
    Context context;
    ViewGroup containerPhotos;
    String imageUrlOrPath;
    String hashedFileName;
    View imgUploadProgressbar;
    ImageButton btnStatus;
    ArrayList<String> uploadedImageList;
    boolean removed = false;
    boolean flagUploading = false;
    private String uploadEndPoint;

    public UploadImageBox(Context context, ViewGroup parent, String uploadEndPoint) {
        super(context);
        containerPhotos = parent;
        this.uploadEndPoint = uploadEndPoint;
        initView(context);
    }

    public UploadImageBox(Context context) {
        super(context);
        initView(context);
    }

    public UploadImageBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public UploadImageBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public UploadImageBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public String getUploadEndPoint() {
        return uploadEndPoint;
    }

    public void setUploadEndPoint(String uploadEndPoint) {
        this.uploadEndPoint = uploadEndPoint;
    }

    public String getUploadedFileName() {
        return hashedFileName;
    }

    public void initView(Context ctx) {

        context = ctx;

        View layout = inflate(getContext(), R.layout.car_added_img_box, this);

        imgUploadProgressbar = layout.findViewById(R.id.img_upload_progressbar);
        btnStatus = layout.findViewById(R.id.btn_status);
        setFocusable(true);
        setClickable(true);

    }

    public void set(String filePath, boolean upload, ArrayList<String> uploadedImages) {

        uploadedImageList = uploadedImages;
        ivAddedImage = this.findViewById(R.id.iv_added_image);
        ivAddedImage.setBackgroundResource(R.color.blue_grey_200);

        if (upload) {
            // Display from file and upload
            imgUploadProgressbar.setVisibility(View.VISIBLE);
            ((SparaatApp) context.getApplicationContext()).displayImage("file://" + filePath, ivAddedImage);
            uploadFile(filePath);
        } else {
            // Display from URL
            ((SparaatApp) context.getApplicationContext()).displayImage(filePath, ivAddedImage);
        }
    }


    /**
     * Upload Car photos  and set {uploadedImages}  list with the uploaded images
     *
     * @param imgOriginalPath
     */
    private void uploadFile(final String imgOriginalPath) {
        if (imgOriginalPath != null) {
            String endpoint = getUploadEndPoint();
            flagUploading = true;
            ConnectionHandler conn = new ConnectionHandler(context);
            conn.upload(endpoint, imgOriginalPath, FileResponse.class, new HttpResponse() {
                @Override
                public void onSuccess(Object parsedObject) {
                    if (!removed) {
                        flagUploading = false;
                        final FileResponse fileResponse = (FileResponse) parsedObject;
                        if (fileResponse != null && fileResponse.filename != null) {
                            uploadedImageList.add(fileResponse.filename);
                            imgUploadProgressbar.setVisibility(View.GONE);
                            hashedFileName = fileResponse.filename;

                        }
                        flagUploading = false;
                        btnStatus.setImageResource(R.drawable.ic_done_24);
                    }
                }

                @Override
                public void onNoConnection() {
                    imgUploadProgressbar.setVisibility(View.GONE);
                    btnStatus.setImageResource(R.drawable.ic_refresh_24);
                    flagUploading = false;
                }

                @Override
                public void onForbidden() {
                    imgUploadProgressbar.setVisibility(View.GONE);
                    btnStatus.setImageResource(R.drawable.ic_refresh_24);
                    flagUploading = false;
                }

                @Override
                public void onError(String response) {
                    imgUploadProgressbar.setVisibility(View.GONE);
                    btnStatus.setImageResource(R.drawable.ic_refresh_24);
                    flagUploading = false;
                }
            });
        }

    }


    public boolean isStillUploading() {
        return flagUploading;
    }


}
