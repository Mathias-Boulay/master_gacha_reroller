package fr.spse.master_gacha_reroller.rerollable;

import android.content.Context;

/**
 * Dummy class used for recycler View tests.
 * DO NOT USE NOR SUBCLASS THIS CLASS !
 */
public class dummyRerollableApp extends BaseRerollableApp {

    public dummyRerollableApp(Context ctx, String packageName, int defaultAppName) {
        super(ctx, packageName, defaultAppName);
    }

    @Override
    public void onPreReroll() {
        //PREPARE REROLL
    }

    @Override
    public boolean reroll() {
        //START REROLL
        return false;
    }

    @Override
    public void onPostReroll(boolean success) {
        super.onPostReroll(success);
        //DO CLEANUP
    }
}
