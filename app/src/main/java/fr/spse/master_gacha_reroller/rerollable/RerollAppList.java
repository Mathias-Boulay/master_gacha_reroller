package fr.spse.master_gacha_reroller.rerollable;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fr.spse.master_gacha_reroller.R;

/**
 * Data class used for storing rerollable apps
 */
public class RerollAppList {
    /** Remove instantiation */
    private RerollAppList(){}
    
    public static final List<BaseRerollableApp> rerollableAppList = new ArrayList<>();
    
    public static void initList(Context ctx){
        rerollableAppList.add(new dummyRerollableApp( ctx, "com.nexon.konosuba", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "com.dsemu.drastic", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "skyline.emu", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "com.oasisfeng.greenify", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "org.telegram.messenger", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "org.schabi.newpipe", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "org.chromium.webapk.aOf39d6_v2", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "com.google.ar.core", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "us.zoom.videomeetings", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "nextapp.fx", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "com.nexon.hny", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "com.nexon.konosuba", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "com.nexon.,ghycx", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "com.nexon.fzeqgqe", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "com.nexon.u;ihk", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "com.nexon.fqyuku", R.string.com_nexon_konosuba));
        rerollableAppList.add(new dummyRerollableApp( ctx, "com.nexon.feF", R.string.com_nexon_konosuba));
    }
}
