package com.dezzzl;

public interface ImageOperations {
    void deleteImage(int imageId);
    void addTag(int imageId, String tagName);

    void removeTag(int imageId, String tagName);
}
