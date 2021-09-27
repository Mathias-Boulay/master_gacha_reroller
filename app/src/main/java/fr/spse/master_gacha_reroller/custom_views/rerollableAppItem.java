package fr.spse.master_gacha_reroller.custom_views;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.makeramen.roundedimageview.RoundedImageView;

import fr.spse.master_gacha_reroller.rerollable.BaseRerollableApp;
import fr.spse.master_gacha_reroller.R;
import fr.spse.master_gacha_reroller.utils.Tools;


public class rerollableAppItem extends ConstraintLayout{

    private BaseRerollableApp rerollableApp;

    private TextView appTitleTextView;
    private TextView appPackageTextView;
    private RoundedImageView backgroundArtImageView;
    private ImageButton appSettingButton;
    private ImageButton appRerollButton;

    public rerollableAppItem(Context context) {
        super(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(
                Tools.dpToPx(getContext(), 20), //Left
                Tools.dpToPx(getContext(), 12), //Top
                Tools.dpToPx(getContext(), 20), //Right
                0); //Bottom
        setLayoutParams(params);

        initView();

        appSettingButton.setOnClickListener(view -> { //TODO launch an intent for the app
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + rerollableApp.getAppPackageName() ));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().getApplicationContext().startActivity(intent);
        });

        appRerollButton.setOnClickListener(view -> { //TODO call the reroll framework
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(rerollableApp.getAppName())
                    .setMessage(R.string.reroll_confirmation_dialog_message)
                    .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> performReroll())
                    .setNegativeButton(android.R.string.no, null)
                    .create().show();
        });
    }



    /**
     * Link Views with their Java object.
     */
    private void initView(){
        View v = inflate(this.getContext(), R.layout.reroll_app_item, this );

        appTitleTextView = v.findViewById(R.id.reroll_app_item_title);
        appPackageTextView = v.findViewById(R.id.reroll_app_item_packageName);
        backgroundArtImageView = v.findViewById(R.id.reroll_app_item_artwork);
        appSettingButton = v.findViewById(R.id.reroll_app_item_setting_button);
        appRerollButton = v.findViewById(R.id.reroll_app_item_reroll_button);
    }

    /**
     * Setup the data needed to get displayed.
     * Entry point for the recyclerView system
     */
    public void setData(BaseRerollableApp rerollableApp){
        //TODO implement the package not found exception since someone won't have ALL the games !
        this.rerollableApp = rerollableApp;
        rerollableApp.setupData(this);

        appTitleTextView.setText(rerollableApp.getAppName());
        appPackageTextView.setText(rerollableApp.getAppPackageName());
        updateBlurredBackground(); //Background artwork


        setClickableButtonState(rerollableApp.isAppInfoAvailable());
    }

    /**
     * Set the background from the app icon.
     */
    public void updateBlurredBackground(){
        //Default appearance, since no package
        if(!rerollableApp.isAppInfoAvailable()){
            backgroundArtImageView.setColorFilter(Color.argb(255, 20,20,20));
        }

        Bitmap bitmap = rerollableApp.getArtworkBitmap();
        if(bitmap != null) {
            backgroundArtImageView.setImageBitmap(bitmap);
            backgroundArtImageView.setColorFilter(Color.argb(120, 0, 0, 0));
        }
    }

    /**
     * Change button behavior according to the status
     * @param isClickable Whether buttons should be clickable
     */
    private void setClickableButtonState(boolean isClickable){
        appSettingButton.setClickable(isClickable);
        appRerollButton.setClickable(isClickable);

        int color =  Color.argb(isClickable ? 0 : 200, 142, 142, 142);
        appSettingButton.setColorFilter(color);
        appRerollButton.setColorFilter(color);
    }

    /**
     * Perform the reroll
     */
    private void performReroll(){
       setClickableButtonState(false);
       rerollableApp.onPreReroll();
       new Thread(() -> {
           boolean success = rerollableApp.reroll();
           //Guarantee being in the UI thread
           new Handler(Looper.getMainLooper()).post(() -> {
               setClickableButtonState(true);
               rerollableApp.onPostReroll(success);
           });
       }).start();
    }

}
