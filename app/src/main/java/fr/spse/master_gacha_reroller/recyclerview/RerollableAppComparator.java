package fr.spse.master_gacha_reroller.recyclerview;

import java.util.Comparator;

import fr.spse.master_gacha_reroller.rerollable.BaseRerollableApp;

/**
 * Comparator class used to rank apps
 * The comparator checks for two things:
 * 1 - If the app is available (meaning installed on the user's device)
 * 2 - The app package name.
 * Apps availability is more important to the user than what exists
 */
public class RerollableAppComparator implements Comparator<BaseRerollableApp> {
    @Override
    public int compare(BaseRerollableApp baseRerollableApp, BaseRerollableApp t1) {
        Boolean isFirstAvailable = baseRerollableApp.isAppInfoAvailable();
        Boolean isSecondAvailable = t1.isAppInfoAvailable();


        return 9999*(isSecondAvailable.compareTo(isFirstAvailable))
                + baseRerollableApp.getAppPackageName().compareTo(t1.getAppPackageName());
    }
}
