package fr.spse.master_gacha_reroller.rerollable.app;

import android.content.Context;

import fr.spse.master_gacha_reroller.R;
import fr.spse.master_gacha_reroller.rerollable.BaseRerollableApp;
import fr.spse.master_gacha_reroller.utils.Command;

public class GenshinImpact extends BaseRerollableApp {
    public GenshinImpact(Context ctx) {
        super(ctx, "com.miHoYo.GenshinImpact", R.string.com_miHoYo_GenshinImpact);
    }

    @Override
    public boolean reroll() {
        boolean success;
        success = Command.removeFolder(getPrivateDataDir(DIR_SHARED_PREFS));
        success &= Command.removeFolder(getPrivateDataDir(DIR_DATABASES));
        success &= Command.removeFolder(getPrivateDataDir() + "/no_backup");
        success &= Command.removeFile(getPrivateDataDir(DIR_FILES) + "/PersistedInstallation* ");

        return success;
    }
}
