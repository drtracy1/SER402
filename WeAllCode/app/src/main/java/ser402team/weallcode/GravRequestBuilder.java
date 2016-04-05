package ser402team.weallcode;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Base64;

/**
 * @author: tkeunebr
 * https://github.com/tkeunebr/gravatar-android/blob/master/gravatar-android/src/fr/tkeunebr/gravatar/RequestBuilder.java
 * @update: Kristel Basra
 */

@SuppressWarnings("UnusedDeclaration") // Public API.
public class GravRequestBuilder {
    private static final String GRAVATAR_ENDPOINT = "http://www.gravatar.com/avatar/";
    private static final String GRAVATAR_SECURE_ENDPOINT = "https://secure.gravatar.com/avatar/";
    private final StringBuilder builder;
    private static StringBuilder mBuilder = new StringBuilder();

    GravRequestBuilder(Gravatar gravatar, String email) {
        final String hash = GravUtils.convertEmailToHash(email);
        builder = gravatar.ssl ? new StringBuilder(GRAVATAR_SECURE_ENDPOINT.length() + hash.length() + 1).append
                (GRAVATAR_SECURE_ENDPOINT) :
                new StringBuilder(GRAVATAR_ENDPOINT.length() + hash.length() + 1).append(GRAVATAR_ENDPOINT);
        builder.append(hash);
        if (gravatar.extension) {
            builder.append(".jpg");
        }
        builder.append("?");
    }

    /**
     * Specifies the image dimensions (as the image is always squared) to be retrieved,
     * allowing to save both bandwidth and memory.
     * <p/>
     *
     * @param sizeInPixels must be between {@code Gravatar.MIN_IMAGE_SIZE_PIXEL}
     *                     and {@code Gravatar.MAX_IMAGE_SIZE_PIXEL}.
     */
    public GravRequestBuilder size(int sizeInPixels) {
        if (sizeInPixels >= Gravatar.MIN_IMAGE_SIZE_PIXEL && sizeInPixels <= Gravatar.MAX_IMAGE_SIZE_PIXEL) {
            builder.append("&size=").append(sizeInPixels);
            return this;
        }
        throw new IllegalArgumentException("Requested image size must be between " + Gravatar.MIN_IMAGE_SIZE_PIXEL
                + " and " + Gravatar.MAX_IMAGE_SIZE_PIXEL);
    }

    /**
     * The default image will always be returned.
     */
    public GravRequestBuilder forceDefault() {
        builder.append("&f=y");
        return this;
    }

    /**
     * If the request image couldn't be found, a 404 HTTP error will be returned instead of the default image.
     */
    public GravRequestBuilder force404() {
        builder.append("&d=404");
        return this;
    }

    /**
     * Sets the defaultImage to be one of Gravatar's built-in default images.
     * <p/>
     *
     * @param gravatarDefaultImage must be either {@code Gravatar.DefaultImage.MYSTERY_MAN},
     *                             {@code Gravatar.DefaultImage.IDENTICON},
     *                             {@code Gravatar.DefaultImage.MONSTER},
     *                             {@code Gravatar.DefaultImage.WAVATAR},
     *                             {@code Gravatar.DefaultImage.RETRO} or
     *                             {@code Gravatar.DefaultImage.BLANK}.
     */
    public GravRequestBuilder defaultImage(int gravatarDefaultImage) {
        switch (gravatarDefaultImage) {
            case Gravatar.DefaultImage.MYSTERY_MAN:
                builder.append("&d=mm");
                return this;
            case Gravatar.DefaultImage.IDENTICON:
                builder.append("&d=identicon");
                return this;
            case Gravatar.DefaultImage.MONSTER:
                builder.append("&d=monsterid");
                return this;
            case Gravatar.DefaultImage.WAVATAR:
                builder.append("&d=wavatar");
                return this;
            case Gravatar.DefaultImage.RETRO:
                builder.append("&d=retro");
                return this;
            case Gravatar.DefaultImage.BLANK:
                builder.append("&d=blank");
                return this;
        }
        throw new IllegalArgumentException("The Gravatar default image must be one of the built-in " +
                "default images MYSTERY_MAN, IDENTICON, MONSTER, WAVATAR, RETRO, BLANK or a custom URL image");
    }

    /**
     * Sets the defaultImage to the custom location pointed by the {@link String url}.
     * <p/>
     *
     * @param url the default image url, must a accessible over the Internet and will be automatically encoded.
     */
    public GravRequestBuilder defaultImage(String url) {
        builder.append("&d=").append(GravUtils.encode(url));
        return this;
    }

    /**
     * Specifies the rating policy for Gravatar image.
     * <p/>
     *
     * @param rating must be either {@code Gravatar.Rating.g},
     *               {@code Gravatar.Rating.pg},
     *               {@code Gravatar.Rating.r} or
     *               {@code Gravatar.Rating.x}.
     */
    public GravRequestBuilder rating(int rating) {
        switch (rating) {
            case Gravatar.Rating.g:
                builder.append("&r=g");
                return this;
            case Gravatar.Rating.pg:
                builder.append("&r=pg");
                return this;
            case Gravatar.Rating.r:
                builder.append("&r=r");
                return this;
            case Gravatar.Rating.x:
                builder.append("&r=x");
                return this;
        }
        throw new IllegalArgumentException("The Gravatar default image must be one of the built-in rating policy" +
                "G, PG, R or X");
    }

    /**
     * @return the fully built Gravatar URL.
     */
    public String build() {
        final int size = builder.length() - 1;
        if (builder.charAt(size) == '?') {
            builder.deleteCharAt(size);
        }
        return builder.toString();
    }
}