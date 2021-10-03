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

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.hoko.blur.HokoBlur;

import fr.spse.master_gacha_reroller.R;
import fr.spse.master_gacha_reroller.custom_views.rerollableAppItem;
import fr.spse.master_gacha_reroller.utils.Command;
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
        } catch (PackageManager.NameNotFoundException ignored) {}
    }

    final public void setupData(rerollableAppItem holderView){
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
    final public boolean isAppInfoAvailable(){
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


    /** Flags for the usual app directories */
    public static final int DIR_SHARED_PREFS = 0x1;
    public static final int DIR_FILES = 0x2;
    public static final int DIR_CACHE = 0x3;
    public static final int DIR_DATABASES = 0x4;
    public static final int DIR_APP_WEBVIEW = 0x5;

    /** Wrapper for private data directory without any flag*/
    final public String getPrivateDataDir(){
        return getPrivateDataDir(0);
    }

    /** Wrapper for private data directory, with an optional flag for usual app paths*/
    @SuppressLint("SdCardPath")
    final public String getPrivateDataDir(int flag){
        if(applicationInfo == null) return "";

        String appPath = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            appPath = applicationInfo.dataDir;
        }else{ //TODO FIX IMPROPER ASS SUPPORT
            appPath = "/data/data/" + packageName;
        }

        /* If a flag is specified, add it to the path */
        if(flag == DIR_SHARED_PREFS) appPath += "/shared_prefs";
        if(flag == DIR_FILES) appPath += "/files";
        if(flag == DIR_CACHE) appPath += "/cache";
        if(flag == DIR_DATABASES) appPath += "/databases";
        if(flag == DIR_APP_WEBVIEW) appPath += "app_webview";

        return appPath;
    }
    /** Wrapper for app "public" data dir */
    final public String getPublicDataDir(){return getPublicDataDir(0);}

    final public String getPublicDataDir(int flag){
        if(applicationInfo == null) return "";
        String appPath = "/storage/emulated/0/Android/data/" + getAppPackageName();

        if(flag == DIR_FILES) appPath += "/files";
        if(flag == DIR_CACHE) appPath += "/cache";

        return appPath;
    }
    /** Wrapper for artwork bitmap */
    final public Bitmap getArtworkBitmap(){
        return artworkBitmap;
    }


    /** Comparator for the sorted app list*/
    @Override
    final public <T> boolean isSameModelAs(@NonNull T model) {
        if(!(model instanceof BaseRerollableApp)) return false;
        return ((BaseRerollableApp)model).getAppPackageName().equals(getAppPackageName());
    }
    /** Comparator for the sorted app list*/
    @Override
    final public <T> boolean isContentTheSameAs(@NonNull T model) {
        return isSameModelAs(model);
    }

    /** Kill the application to avoid unwanted file tampering */
    @CallSuper
    @Override
    public void onPreReroll(){
        Command.killApp(getAppPackageName());
    }

    /** Auto call the fail or success callback by default */
    @CallSuper
    @Override
    public void onPostReroll(boolean success) {
        if(success) onRerollSuccess();
        else onRerollFail();
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
