package com.hyc.zhihu.helper;

import android.content.Context;
import android.net.Uri;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by hyc on 2015/10/26.
 */
public class FrescoHelper {
    static Context appContext;

    /**
     * 初始化 在Application OnCreate
     *
     * @param context
     */
    public static void initialize(Context context) {
        DiskCacheConfig cacheConfig = DiskCacheConfig.newBuilder(context).setBaseDirectoryPath(context.getApplicationContext().getCacheDir()).setBaseDirectoryName("image_cache").setMaxCacheSize(20 * 1024 * 1024).build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(context).setMainDiskCacheConfig(cacheConfig).build();
        Fresco.initialize(context, imagePipelineConfig);
//        FLogDefaultLoggingDelegate.getInstance().setApplicationTag("ITV Fresco");
//        FLogDefaultLoggingDelegate.getInstance().setMinimumLoggingLevel(Log.VERBOSE);
    }

    public static void loadImage(SimpleDraweeView sdv, String url) {
        loadImage(sdv, url, null);
    }

    public static void loadImage(SimpleDraweeView sdv, Uri uri) {
        loadImage(sdv, uri, null);
    }

    public static void loadImage(SimpleDraweeView sdv, String url, ControllerListener listener) {
        Uri uri = Uri.parse(url);
        loadImage(sdv, uri, listener);
    }

    public static void loadImage(SimpleDraweeView sdv, Uri uri, ControllerListener listener) {
        if (sdv == null) {
            return;
        }
        if (listener != null) {
            DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(uri).setControllerListener(listener).build();
            sdv.setController(controller);
        } else {
            sdv.setImageURI(uri);
        }
    }

    public static void loadImage(SimpleDraweeView sdv, String url, int width, int height) {
        loadImage(sdv, url, width, height, null);
    }

    public static void loadImage(SimpleDraweeView sdv, Uri uri, int width, int height) {
        loadImage(sdv, uri, width, height, null);
    }

    /**
     * @param sdv
     * @param url
     * @param width
     * @param height
     * @param listener 最好使用{@link com.facebook.drawee.controller.BaseControllerListener}
     */
    public static void loadImage(SimpleDraweeView sdv, String url, int width, int height, ControllerListener listener) {
        Uri uri = Uri.parse(url);
        loadImage(sdv, uri, width, height, listener);
    }

    public static void loadImage(SimpleDraweeView sdv, Uri uri, int width, int height, ControllerListener listener) {
        if (sdv == null) {
            return;
        }
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).setResizeOptions(new ResizeOptions(width, height)).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder().setImageRequest(request).setControllerListener(listener).build();
        sdv.setController(controller);
    }

    public static void changeRounding(SimpleDraweeView sdv, float topleft, float topRight, float bottomRight, float bottomLeft) {
        if (sdv != null) {
            GenericDraweeHierarchy hierarchy = sdv.getHierarchy();
            RoundingParams roundingParams = hierarchy.getRoundingParams();
            roundingParams.setCornersRadii(topleft, topRight, bottomRight, bottomLeft);
            hierarchy.setRoundingParams(roundingParams);
        }
    }

    public static void changeRoundingAndHolder(SimpleDraweeView sdv, float topleft, float topRight, float bottomRight, float bottomLeft, int img) {
        if (sdv != null) {
            GenericDraweeHierarchy hierarchy = sdv.getHierarchy();
            RoundingParams roundingParams = hierarchy.getRoundingParams();
            roundingParams.setCornersRadii(topleft, topRight, bottomRight, bottomLeft);
            hierarchy.setRoundingParams(roundingParams);
            hierarchy.setPlaceholderImage(img);
        }
    }
}
