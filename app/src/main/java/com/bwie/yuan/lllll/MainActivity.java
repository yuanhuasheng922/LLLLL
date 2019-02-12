package com.bwie.yuan.lllll;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity  {
    @BindView(R.id.b1)
    Button b1;
    @BindView(R.id.b2)
    Button b2;
    @BindView(R.id.b3)
    Button b3;
    @BindView(R.id.b4)
    Button b4;
    @BindView(R.id.b5)
    Button b5;
    @BindView(R.id.b6)
    Button b6;
   @BindView(R.id.image_icon)
   SimpleDraweeView image_icon;
    private String path = "http://www.zhaoapi.cn/images/quarter/ad1.png";
    private String pathgif = "http://www.zhaoapi.cn/images/girl.gif";
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.b1, R.id.b2, R.id.b3, R.id.b4,R.id.b5,R.id.b6})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //圆形
            case R.id.b1:
                RoundingParams roundingParams=RoundingParams.asCircle();
                GenericDraweeHierarchy build=GenericDraweeHierarchyBuilder.newInstance(this.getResources()).setRoundingParams(roundingParams).build();
                image_icon.setHierarchy(build);
                uri = Uri.parse(path);
                image_icon.setImageURI(uri);
                break;
            case R.id.b2:
                //圆角
                RoundingParams roundingParams1=RoundingParams.fromCornersRadius(50f);
                GenericDraweeHierarchy build1=GenericDraweeHierarchyBuilder.newInstance(this.getResources()).setRoundingParams(roundingParams1).build();
                image_icon.setHierarchy(build1);
                Uri uri1=Uri.parse(path);
                image_icon.setImageURI(uri1);
                break;
            case R.id.b3:
                //动图
                Uri uri3 = Uri.parse(pathgif);
                AbstractDraweeController build2 = Fresco.newDraweeControllerBuilder()
                        .setUri(uri3).setAutoPlayAnimations(true)
                        .setOldController(image_icon.getController())
                        .build();
                image_icon.setController(build2);
                break;
            case R.id.b4:
                //渐进式
                Uri uri2=Uri.parse(path);
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri2)
                        .setProgressiveRenderingEnabled(true)
                        .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(image_icon.getController())
                        .build();
                image_icon.setController(controller);
                break;
            case R.id.b5:
                //比例
                Uri uri4 = Uri.parse(path);
                image_icon.setAspectRatio(1.2f);
                ImageRequest request1 = ImageRequestBuilder.newBuilderWithSource(uri4).build();
                PipelineDraweeController pipelineDraweeController = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().setOldController(image_icon.getController()).setImageRequest(request1).build();
                image_icon.setController(pipelineDraweeController);
                break;
            case R.id.b6:
                //磁盘
                DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                        .setBaseDirectoryName("images")
                        .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                        .build();

                ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                //将DiskCacheConfig设置给ImagePipelineConfig
                        .setMainDiskCacheConfig(diskCacheConfig)
                        .build();
                Fresco.initialize(this,config);

                break;
        }
    }

}
