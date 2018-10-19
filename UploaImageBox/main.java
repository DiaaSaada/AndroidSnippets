

public abstract class CarFormFragment extends Fragment implements ImagePickerCallback {

     
    protected ArrayList<UploadImageBox> mUploadImageBoxes = new ArrayList<>();

     


    @Override
    public void onImagesChosen(List<ChosenImage> images) {

       // Log.v("onImagesChosen", "onImagesChosen ");

        if (uploadedImages == null) {
            uploadedImages = new ArrayList<String>();
        }
        if (uploadedImages.size() + images.size() >= 4) {
            mView.findViewById(R.id.btn_add_photos).setVisibility(View.GONE);
        }

        if (images.size() > 0) {
            for (ChosenImage img : images) {
                if (containerCarPhotos.getChildCount() == 4) {
                    toast(R.string.only_4_images);
                    return;
                }

               // Log.w(this.toString(), "SELECT PHOTO " + img.getOriginalPath());
                File ff = new File(img.getOriginalPath());
                if (ff.exists()) {
                    UploadImageBox imageBox = new UploadImageBox(mainActivity(), containerCarPhotos, AppRoutes.ENDPOINT_UPLOAD_CAR);
                    mUploadImageBoxes.add(imageBox);
                    imageBox.set(img.getOriginalPath(), true, uploadedImages);
                    imageBox.setFocusable(true);
                    imageBox.setClickable(true);
                    imageBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UploadImageBox box = (UploadImageBox) v;
                            containerCarPhotos.removeView(v);

                            if (uploadedImages.size() - 1 < 4) {
                                mView.findViewById(R.id.btn_add_photos).setVisibility(View.VISIBLE);
                            }

                            if (box.getUploadedFileName() != null) {
                                uploadedImages.remove(box.getUploadedFileName());
                            }


                        }
                    });
                    containerCarPhotos.addView(imageBox);

                }
            }
        }


    }


    void onImageClick(final View layout, final String filename) {


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerCarPhotos.removeView(layout);

                if (uploadedImages.size() - 1 < 4) {
                    mView.findViewById(R.id.btn_add_photos).setVisibility(View.VISIBLE);
                }

                if (filename != null) {
                    uploadedImages.remove(filename);
                }

            }
        });

    }


    @Override
    public void onError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }


    

}
