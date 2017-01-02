package com.kaltura.magikapp.magikapp.asset_page;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.connect.backend.magikapp.data.Configuration;
import com.kaltura.magikapp.R;

/**
 * Created by vladir on 30/11/2016.
 */

public class RoundButton extends BaseRoundButton implements SecondaryViewsInitiator.SecondaryView {

    protected RoundButtonCommonImage mMode;
    protected ImageView mImageView;


    enum RoundButtonCommonImage {
        Like(0),
        Favorites(1),
        Comment(2),
        Share(3),
        Play(4),
        Download(5),
        MoreOptions(6);

        int id;

        RoundButtonCommonImage(int id) {
            this.id = id;
        }

        static RoundButtonCommonImage fromId(int id) {
            for (RoundButtonCommonImage f : values()) {
                if (f.id == id) return f;
            }
            throw new IllegalArgumentException();
        }
    }

    public RoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundButton(Context context) {
        super(context);
        init(context, null);
    }

    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected int getButtonLayout() {
        return R.layout.round_button_layout;
    }

    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        mImageView = (ImageView) mRoot.findViewById(R.id.round_button_image);

        if (!isInEditMode()) {
            if (attrs != null) {
                TypedArray appearance = context.obtainStyledAttributes(attrs, R.styleable.HelenRoundButton);
                int modeInt = appearance.getInt(R.styleable.HelenRoundButton_button_mode, -1);

                if (modeInt != (-1)) {
                    mMode = RoundButtonCommonImage.fromId(modeInt);
                    setImages(populateCommonImages(mMode));
                } else {
                    Drawable imageNotPressedRef = appearance.getDrawable(R.styleable.HelenRoundButton_image_not_pressed);
                    Drawable imagePressedRef = appearance.getDrawable(R.styleable.HelenRoundButton_image_pressed);
                    mImageView.setImageDrawable(RoundButtonUtils.getStateDrawableForImageImages(new Drawable[]{imagePressedRef, imageNotPressedRef}));
                }

                appearance.recycle();
            } else {
                setImages(populateCommonImages(mMode));
            }
        }
    }

    private Drawable[] populateCommonImages(RoundButtonCommonImage mode) {
        Drawable notPressed = null;
        Drawable pressed = null;
        switch (mode){
            case Like:
                if (mThemeType.equals(Configuration.ThemeType.Food)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_face, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_face, null);
                } else if (mThemeType.equals(Configuration.ThemeType.Festival)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_facer, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_facer, null);
                }
                break;
            case Comment:
                if (mThemeType.equals(Configuration.ThemeType.Food)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_insta, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_insta, null);
                } else if (mThemeType.equals(Configuration.ThemeType.Festival)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_instar, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_instar, null);
                }
                break;
            case Favorites:
                if (mThemeType.equals(Configuration.ThemeType.Food)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_pintrest, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_pintrest, null);
                } else if (mThemeType.equals(Configuration.ThemeType.Festival)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pintrestr, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pintrestr, null);
                }
                break;
            case Share:
                if (mThemeType.equals(Configuration.ThemeType.Food)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_mail, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_mail, null);
                } else if (mThemeType.equals(Configuration.ThemeType.Festival)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_mailr, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_mailr, null);
                }
                break;
            case Play:
                if (mThemeType.equals(Configuration.ThemeType.Food)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.vid_badge_copy, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.vid_badge_copy, null);
                } else if (mThemeType.equals(Configuration.ThemeType.Festival)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.drawable.vid_badge_black, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.drawable.vid_badge_black, null);
                }
                break;
            case Download:
                if (mThemeType.equals(Configuration.ThemeType.Food)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_twitter, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_twitter, null);
                } else if (mThemeType.equals(Configuration.ThemeType.Festival)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_twitterr, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_twitterr, null);
                }
                break;
            case MoreOptions:
                if (mThemeType.equals(Configuration.ThemeType.Food)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_twitter, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_twitter, null);
                } else if (mThemeType.equals(Configuration.ThemeType.Festival)) {
                    notPressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_twitterr, null);
                    pressed = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_twitterr, null);
                }
                break;
        }

        pressed.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        notPressed.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        return new Drawable[]{pressed, notPressed};

    }

    /**
     * images {pressed, not pressed}
     ø     * @param images
     */
    private void setImages(Drawable[] images){
        Drawable pressed = images[0];
        Drawable notPressed = images[1];

        if (isSelected()){
            if (pressed != null) {
                mImageView.setImageDrawable(pressed);
            }
        } else {
            if (notPressed != null) {
                mImageView.setImageDrawable(notPressed);
            }
        }
    }


    @Override
    public void setText(String string){

    }

    @Override
    protected LayoutParams getSmallLinearLayout() {
        return new LayoutParams(getDimension(R.dimen.round_round_button_small_width),
                getDimension(R.dimen.round_round_button_small_height));
    }

    @Override
    protected LayoutParams getMediumLinearLayout() {
        return new LayoutParams(getDimension(R.dimen.round_round_button_medium_width),
                getDimension(R.dimen.round_round_button_medium_height));
    }

    @Override
    protected LayoutParams getLargeLinearLayout() {
        return new LayoutParams(getDimension(R.dimen.round_round_button_large_width),
                getDimension(R.dimen.round_round_button_large_height));
    }

}
