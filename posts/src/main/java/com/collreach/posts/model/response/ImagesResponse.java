package com.collreach.posts.model.response;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class ImagesResponse {
    HashSet<ImageResponse> imagesSet;

    public ImagesResponse(HashSet<ImageResponse> imagesSet) {
        this.imagesSet = imagesSet;
    }

    public ImagesResponse(ImageResponse imageResponse) {
        this.imagesSet = new HashSet<>(Collections.singletonList(imageResponse));
    }

    public HashSet<ImageResponse> getImagesSet() {
        return imagesSet;
    }

    public void setImagesSet(HashSet<ImageResponse> imagesSet) {
        this.imagesSet = imagesSet;
    }
}
