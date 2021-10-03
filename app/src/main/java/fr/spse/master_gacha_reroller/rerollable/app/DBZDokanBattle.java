package fr.spse.master_gacha_reroller.rerollable.app;

import android.content.Context;

import fr.spse.master_gacha_reroller.R;
import fr.spse.master_gacha_reroller.rerollable.BaseRerollableApp;
import fr.spse.master_gacha_reroller.utils.Command;

public class DBZDokanBattle extends BaseRerollableApp {

    public DBZDokanBattle(Context ctx) {
        super(ctx, "com.bandainamcogames.dbzdokkanww", R.string.com_bandainamcogames_dbzdokkanww);
    }

    @Override
    public boolean reroll() {
        boolean success;
        success = Command.removeFile(getPrivateDataDir(DIR_SHARED_PREFS) + "/Cocos2dxPrefsFile.xml");
        return success;
    }
}
