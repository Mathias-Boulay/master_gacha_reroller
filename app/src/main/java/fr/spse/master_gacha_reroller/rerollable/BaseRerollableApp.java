package fr.spse.master_gacha_reroller.rerollable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.hoko.blur.HokoBlur;

import fr.spse.master_gacha_reroller.R;
import fr.spse.master_gacha_reroller.custom_views.rerollableAppItem;
import fr.spse.master_gacha_reroller.utils.ImageUtils;

/**
 * Generic class for rerollable apps.
 * Subclasses need to implement the reroll function and can extend the preReroll and postReroll
 * behavior to match their needs.
 */
public abstract class BaseRerollableApp implements Rerollable, SortedListAdapter.ViewModel {

    private rerollableAppItem holderView = null;

    private final String packageName;
    private final int defaultAppName;
    private ApplicationInfo applicationInfo = null;
    private Bitmap artworkBitmap = null;

    public BaseRerollableApp(Context ctx, String packageName, int defaultAppName){
        this.packageName = packageName;
        this.defaultAppName = defaultAppName;
        getApplicationInfo(ctx);
    }

    private void getApplicationInfo(Context ctx){
        try {
            applicationInfo = ctx.getPackageManager().getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {e.printStackTrace();}
    }

    public void setupData(rerollableAppItem holderView){
        this.holderView = holderView;

        setupArtwork();
    }

    /** Get the artwork, blur it and set it in an async manner */
    private void setupArtwork(){
        if(artworkBitmap != null || applicationInfo == null) return;
        new Thread(() -> {
            artworkBitmap =  ImageUtils.drawableToBitmap(
                    holderView.getContext().getPackageManager().getApplicationIcon(applicationInfo), 4);

            HokoBlur.with(holderView.getContext())
                    .sampleFactor(1)
                    .radius(3)
                    .forceCopy(false)
                    .processor()
                    .blur(artworkBitmap);

            new Handler(Looper.getMainLooper()).post(() -> holderView.updateBlurredBackground());
        }).start();

    }

    /** Tells if we have an appInfo, meaning "is the app is installed on the device ?" */
    public boolean isAppInfoAvailable(){
        return applicationInfo != null;
    }

    /** Wrapper for app name */
    final public String getAppName(){
        if(holderView == null) return "";
        if(applicationInfo == null) return holderView.getContext().getString(defaultAppName);
        return holderView.getContext().getPackageManager().getApplicationLabel(applicationInfo).toString();
    }
    /** Wrapper for app package name */
    final public String getAppPackageName(){
        return packageName;
    }
    /** Wrapper for app base.apk directory */
    final public String getAPKDirectory(){
        if(applicationInfo == null) return "";
        return applicationInfo.sourceDir;
    }
    /** Wrapper for private data directory */
    @SuppressLint("SdCardPath")
    final public String getPrivateDataDir(){
        if(applicationInfo == null) return "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return applicationInfo.deviceProtectedDataDir;
        }else{ //TODO FIX IMPROPER ASS SUPPORT
            return "/data/data/" + packageName;
        }
    }
    /** Wrapper for app "public" data dir */
    final public String getPublicDataDir(){
        if(applicationInfo == null) return "";
        return applicationInfo.dataDir;
    }
    /** Wrapper for artwork bitmap */
    final public Bitmap getArtworkBitmap(){
        return artworkBitmap;
    }


    /** Comparator for the sorted app list*/
    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        if(!(model instanceof BaseRerollableApp)) return false;
        return ((BaseRerollableApp)model).getAppPackageName().equals(getAppPackageName());
    }
    /** Comparator for the sorted app list*/
    @Override
    public <T> boolean isContentTheSameAs(@NonNull T model) {
        return isSameModelAs(model);
    }


    /** Default implementation that can be called by subClasses */
    @Override
    public void onRerollSuccess() {
        Toast.makeText(holderView.getContext(), holderView.getResources().getString(R.string.default_success_toast)
                .replace("%s", getAppName()), Toast.LENGTH_SHORT).show();
    }

    /** Default implementation that can be called by subClasses */
    @Override
    public void onRerollFail() {
        Toast.makeText(holderView.getContext(), holderView.getResources().getString(R.string.default_failed_toast)
                .replace("%s", getAppName()), Toast.LENGTH_LONG).show();
    }
}
