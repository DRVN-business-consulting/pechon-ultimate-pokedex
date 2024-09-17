package dev.nayeoniii.ultimate_pokedex.dto.request;

public class Image {
    private final int imageResId;

    public Image(int imageResId){
        this.imageResId = imageResId;
    }

    public int getImageResId() {
        return imageResId;
    }
}
