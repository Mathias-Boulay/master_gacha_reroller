package fr.spse.master_gacha_reroller.rerollable.app;

import android.content.Context;

import fr.spse.master_gacha_reroller.R;
import fr.spse.master_gacha_reroller.rerollable.BaseRerollableApp;
import fr.spse.master_gacha_reroller.utils.Command;

public class DBZDokanBattleJP extends BaseRerollableApp {

    public DBZDokanBattleJP(Context ctx) {
        super(ctx, "com.bandainamcogames.dbzdokkan", R.string.com_bandainamcogames_dbzdokkan);
    }

    @Override
    public boolean reroll() {
        boolean success;
        success = Command.removeFile(getPrivateDataDir(DIR_SHARED_PREFS) + "/Cocos2dxPrefsFile.xml");
        return success;
    }
}
