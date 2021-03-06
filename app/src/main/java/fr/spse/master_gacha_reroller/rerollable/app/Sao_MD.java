package fr.spse.master_gacha_reroller.rerollable.app;

import android.content.Context;

import fr.spse.master_gacha_reroller.R;
import fr.spse.master_gacha_reroller.rerollable.BaseRerollableApp;
import fr.spse.master_gacha_reroller.utils.Command;

public class Sao_MD extends BaseRerollableApp {
    public Sao_MD(Context ctx) {
        super(ctx, "com.bandainamcoent.saomd", R.string.com_bandainamcoent_saomd);
    }

    @Override
    public boolean reroll() {
        boolean success;
        success = Command.removeFolder(getPrivateDataDir(DIR_SHARED_PREFS));
        success &= Command.removeFolder(getPrivateDataDir(DIR_FILES) + "/memories");
        return success;
    }


}
