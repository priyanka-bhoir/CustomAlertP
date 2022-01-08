// By Priyanka Bhoir 14th Dec 2021

package com.priyanka.alertsp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CusomAlertsP extends Dialog implements View.OnClickListener {

    public static final int NORMAL_TYPE = 0;
    public static final int ERROR_TYPE = 1;
    public static final int SUCCESS_TYPE = 2;
    public static final int WARNING_TYPE = 3;
    public static final int CUSTOM_IMAGE_TYPE = 4;
    public static final int PROGRESS_TYPE = 5;
    public static final int TYPE_QUESTION = 6;
    public static final int TYPE_INFO = 7;
    public static final int TYPE_LOADING = 8;

    private View mDialogView;
    private final AnimationSet mModalInAnim;
    private final AnimationSet mModalOutAnim;
    private final Animation mOverlayOutAnim;
    private AnimationSet mErrorXInAnim;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private String mTitleText;
    private String mContentText;
    private String mProgresstext;
    private boolean mShowCancel;
    private boolean mShowConfirm;
    private boolean mShowNeutral;
    private boolean mShowContent;
    private boolean mShowTitle;
    private String mCancelText;
    private String mConfirmText;
    private String mNeutralText;
    private int mAlertType;
    private FrameLayout mErrorFrame;
    private FrameLayout mSuccessFrame;
    private FrameLayout mProgressFrame;
//    private SuccessTickView mSuccessTick;
    private ImageView mErrorX;
    private View mSuccessLeftMask;
    private View mSuccessRightMask;
    private Drawable mCustomImgDrawable;
    private ImageView mCustomImage;
    private Button mConfirmButton;
    private Button mCancelButton;
    private Button mNeutralButton;

    private ProgressHelper mProgressHelper;
    private FrameLayout mWarningFrame;
    private onCusomAlertsPClickListener mCancelClickListener;
    private onCusomAlertsPClickListener mConfirmClickListener;
    private onCusomAlertsPClickListener mNeutralClickListner;
    private boolean mCloseFromCancel;

    private int mConfirmBTNTextColor;
    private int mCancelBTNTextColor;
    private int mNeutralBTNTextColor;
    private ProgressWheel progressWheel;
    private TextView progressTextView;
    private FrameLayout alertFrame;
    private FrameLayout progressFrame;
    private ProgressBar progressBar;

    String TAG = "CusomAlertsP";

    CircleImageView civProfilePic;
    ImageView iconicsTextView;
    private boolean isCancelable;


    public static interface onCusomAlertsPClickListener {
        public void onClick (CusomAlertsP cusomAlertsP);
    }


    public CusomAlertsP(Context context) {
        this(context, NORMAL_TYPE);
    }

    public CusomAlertsP(Context context, int alertType) {
        super(context, R.style.RoundedCornersDialog);
        setCanceledOnTouchOutside(false);

        mProgressHelper = new ProgressHelper(context);


        mAlertType = alertType;
        Animation mErrorInAnim = OptAnimationLoader.loadAnimation(context, R.anim.error_frame_in);
        mErrorXInAnim = (AnimationSet)OptAnimationLoader.loadAnimation(context, R.anim.error_x_in);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            List<Animation> childAnims = mErrorXInAnim.getAnimations();
            int idx = 0;
            for (;idx < childAnims.size();idx++) {
                if (childAnims.get(idx) instanceof AlphaAnimation) {
                    break;
                }
            }
            if (idx < childAnims.size()) {
                childAnims.remove(idx);
            }
        }

        Animation mSuccessBowAnim = OptAnimationLoader.loadAnimation(context, R.anim.success_bow_roate);
        AnimationSet mSuccessLayoutAnimSet = (AnimationSet) OptAnimationLoader.loadAnimation(context, R.anim.success_mask_layout);
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(context, R.anim.modal_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(context, R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCloseFromCancel) {
                            CusomAlertsP.super.cancel();
                        } else {
                            CusomAlertsP.super.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // dialog overlay fade out
        mOverlayOutAnim =   new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = getWindow().getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_alert_view);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        civProfilePic = findViewById(R.id.civProfilePic);
        iconicsTextView = findViewById(R.id.i);
        mTitleTextView = findViewById(R.id.headertv);
        mContentTextView = findViewById(R.id.subHeadertv);
        mCancelButton = findViewById(R.id.cnlbtn);
        mConfirmButton = findViewById(R.id.okBtn);
        mNeutralButton = findViewById(R.id.neutralBtn);

        alertFrame = findViewById(R.id.frameAlert);
        progressFrame = findViewById(R.id.progressFrame);
//        mProgressHelper.setProgressWheel((ProgressWheel) findViewById(R.id.progressSpin));
        progressBar = findViewById(R.id.progressSpin);
        progressTextView = findViewById(R.id.progresstext);

        mConfirmButton.setOnClickListener(this);
        mNeutralButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        CircleImageView circleImageView = findViewById(R.id.civProfilePic1);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_zoom_out);
        circleImageView.setAnimation(animation);
        Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_zoom_in);
        circleImageView.setAnimation(animation1);

        setTitleText(mTitleText);
        setContentText(mContentText);
        setCancelButtonText(mCancelText);
        setConfirmButtonText(mConfirmText);
        setNeutralButtonText(mNeutralText);
        setSpinnerText(mProgresstext);
        changeAlertType(mAlertType, true);
        setIsCancelable(isCancelable);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeAlertType(int alertType, boolean fromCreate) {
        mAlertType = alertType;
        // call after created views
        if (mDialogView != null) {
            switch (mAlertType) {
                case ERROR_TYPE:
                    //cross icon of red color
                    progressFrame.setVisibility(View.GONE);
                    alertFrame.setVisibility(View.VISIBLE);

                    int color_error = Color.parseColor("#D50000");
                    iconicsTextView.setColorFilter(color_error);
                    fillBtnColor(color_error);
                    civProfilePic.getBackground().setTint(Color.parseColor("#D50000"));
                    iconicsTextView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_times_solid));

                    break;
                case SUCCESS_TYPE:
                    progressFrame.setVisibility(View.GONE);
                    alertFrame.setVisibility(View.VISIBLE);

                    int color_sucess = Color.parseColor("#388E3C");
                    iconicsTextView.setColorFilter(color_sucess);
                    fillBtnColor(color_sucess);
                    civProfilePic.getBackground().setTint(color_sucess);
                    iconicsTextView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_check_solid));

//                    iconicsTextView.setText(getContext().getString(R.string.fo5_check));
                    break;
                case WARNING_TYPE:
                    progressFrame.setVisibility(View.GONE);
                    alertFrame.setVisibility(View.VISIBLE);

                    int color_warning = Color.parseColor("#F09125");
                    iconicsTextView.setColorFilter(color_warning);
                   fillBtnColor(color_warning);
                    civProfilePic.getBackground().setTint(color_warning);
//                    iconicsTextView.setText(getContext().getString(R.string.fo5_exclamation));
                    iconicsTextView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_exclamation_solid));

                    break;
                case TYPE_QUESTION:
                    progressFrame.setVisibility(View.GONE);
                    alertFrame.setVisibility(View.VISIBLE);

                    iconicsTextView.setColorFilter(Color.parseColor("#353A3E"));
                    if (mNeutralBTNTextColor ==0 ) {
                        mNeutralButton.setTextColor(Color.parseColor("#353A3E"));
                    }
                    if (mCancelBTNTextColor == 0) {
                        mCancelButton.setTextColor(Color.parseColor("#D50000"));
                    }
                    if (mConfirmBTNTextColor == 0) {
                        mConfirmButton.setTextColor(Color.parseColor("#388E3C"));
                    }
                    civProfilePic.getBackground().setTint(Color.parseColor("#353A3E"));
//                    iconicsTextView.setText(getContext().getString(R.string.fo5_question));
                    iconicsTextView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_question_solid));

                    break;
                case TYPE_INFO:
                    progressFrame.setVisibility(View.GONE);
                    alertFrame.setVisibility(View.VISIBLE);
                    int color_info = Color.parseColor("#3F51B5");
                    iconicsTextView.setColorFilter(color_info);
                    fillBtnColor(color_info);
                    civProfilePic.getBackground().setTint(Color.parseColor("#3F51B5"));
//                    iconicsTextView.setText(getContext().getString(R.string.fo5_info));
                    iconicsTextView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_info_solid));

                    break;
                case TYPE_LOADING:
                    alertFrame.setVisibility(View.GONE);
                    progressFrame.setVisibility(View.VISIBLE);
//                    mprogressHelper.spin();
                    break;
            }

        }
    }

    private void fillBtnColor(int color_info) {
            if (mNeutralBTNTextColor ==0 ){
                mNeutralButton.setTextColor(color_info);
            }
            if (mConfirmBTNTextColor == 0) {
                mConfirmButton.setTextColor(Color.parseColor("#388E3C"));
            }
            if (mCancelBTNTextColor == 0 ) {
                mCancelButton.setTextColor(Color.parseColor("#D50000"));
            }
    }

    public int getAlerType () {
        return mAlertType;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeAlertType(int alertType) {
        changeAlertType(alertType, false);
    }

    public String getSpinerText(){
        return mProgresstext;
    }

    public CusomAlertsP setSpinnerText(String text){
        mProgresstext = text;
        if (progressTextView != null && mProgresstext != null ){
            progressTextView.setText(mProgresstext);
        }
        return this;
    }

    public String getTitleText () {
        return mTitleText;
    }

    public CusomAlertsP setTitleText (String text) {
        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            mTitleTextView.setText(mTitleText);
        }
        return this;
    }

    public String getContentText () {
        return mContentText;
    }

    public CusomAlertsP setContentText (String text) {
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            showContentText(true);
            mContentTextView.setText(mContentText);
        }
        return this;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public CusomAlertsP setIsCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        setCancelable(isCancelable);
        return this;
    }

    public boolean isShowCancelButton () {
        return mShowCancel;
    }

    public CusomAlertsP showCancelButton (boolean isShow) {
        mShowCancel = isShow;
        if (mCancelButton != null) {
            mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public boolean isShowContentText () {
        return mShowContent;
    }

    public CusomAlertsP showConfirmButton(boolean isShow){
        mShowConfirm = isShow;
        if (mConfirmButton != null){
            mConfirmButton.setVisibility(mShowConfirm ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public CusomAlertsP showContentText (boolean isShow) {
        mShowContent = isShow;
        if (mContentTextView != null) {
            mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public String getCancelText () {
        return mCancelText;
    }

    public CusomAlertsP setCancelButtonText (String text) {
        mCancelText = text;
        if (mCancelButton != null && mCancelText != null) {
            showCancelButton(true);
            mCancelButton.setText(mCancelText);
        }
        return this;
    }

    public String getConfirmText () {
        return mConfirmText;
    }

    public String getNeutralText(){
        return mNeutralText;
    }

    public CusomAlertsP setConfirmButtonText (String text) {
        mConfirmText = text;
        if (mConfirmButton != null && mConfirmText != null) {
            showConfirmButton(true);
            mConfirmButton.setText(mConfirmText);
        }
        return this;
    }

    public int getmConfirmBTNTextColor(){return mConfirmBTNTextColor; }

    public CusomAlertsP setConfirmBTNTextColor(int color){
        mConfirmBTNTextColor = color;
        if (mConfirmButton != null && mConfirmText !=null){
            showConfirmButton(true);
            mConfirmButton.setTextColor(mCancelBTNTextColor);
        }
        return this;
    }

    public int getmNeutralBTNTextColor(){return mNeutralBTNTextColor;}

    public CusomAlertsP setNeutralBTNTextColor(int color){
        mNeutralBTNTextColor = color;
        if (mNeutralButton != null && mNeutralText != null){
            showNeutalButton(true);
            mNeutralButton.setTextColor(color);
        }
        return this;
    }

    public int getmCancelBTNTextColor(){return mCancelBTNTextColor;}

    public CusomAlertsP setCanceleBTNTextColor(int color){
        mCancelBTNTextColor = color;
        if (mCancelButton != null && mNeutralText != null) {
            showCancelButton(true);
            mCancelButton.setTextColor(color);
        }
        return this;
    }

    public CusomAlertsP setNeutralButtonText (String text) {
        mNeutralText = text;
        if (mNeutralButton != null && mNeutralText != null) {
            showNeutalButton(true);
            mNeutralButton.setText(mNeutralText);
        }
        return this;
    }

    private CusomAlertsP showNeutalButton(boolean b) {
        mShowNeutral = b;
        if (mNeutralButton != null){
            mNeutralButton.setVisibility(mShowNeutral ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public CusomAlertsP setCancelClickListener (onCusomAlertsPClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }

    public CusomAlertsP setConfirmClickListener (onCusomAlertsPClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }

    public CusomAlertsP setNeutralClickListner(onCusomAlertsPClickListener listner){
        mNeutralClickListner = listner;
        return this;
    }

    protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
//        playAnimation();
    }

    /**
     * The real Dialog.cancel() will be invoked async-ly after the animation finishes.
     */
    @Override
    public void cancel() {
        dismissWithAnimation(true);
    }

    /**
     * The real Dialog.dismiss() will be invoked async-ly after the animation finishes.
     */
    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    private void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        mConfirmButton.startAnimation(mOverlayOutAnim);
        mDialogView.startAnimation(mModalOutAnim);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cnlbtn) {
            if (mCancelClickListener != null) {
                mCancelClickListener.onClick(CusomAlertsP.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == R.id.okBtn) {
            if (mConfirmClickListener != null) {
                mConfirmClickListener.onClick(CusomAlertsP.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == R.id.neutralBtn){
            if (mNeutralClickListner != null){
                mNeutralClickListner.onClick(CusomAlertsP.this);
            }
            else {
                dismissWithAnimation();
            }
        }
    }

    public ProgressHelper getProgressHelper () {
        return mProgressHelper;
    }

}
