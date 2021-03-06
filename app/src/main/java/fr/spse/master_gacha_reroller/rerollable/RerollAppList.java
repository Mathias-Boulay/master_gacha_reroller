package fr.spse.master_gacha_reroller.rerollable;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fr.spse.master_gacha_reroller.rerollable.app.DBZDokanBattle;
import fr.spse.master_gacha_reroller.rerollable.app.DBZDokanBattleJP;
import fr.spse.master_gacha_reroller.rerollable.app.GenshinImpact;
import fr.spse.master_gacha_reroller.rerollable.app.Sao_MD;

/**
 * Data class used for storing rerollable apps
 */
final public class RerollAppList {
    /** Remove instantiation */
    private RerollAppList(){}
    
    public static final List<BaseRerollableApp> rerollableAppList = new ArrayList<>();
    
    public static void initList(Context ctx){
        rerollableAppList.add(new Sao_MD(ctx));
        rerollableAppList.add(new DBZDokanBattle(ctx));
        rerollableAppList.add(new DBZDokanBattleJP(ctx));
        rerollableAppList.add(new GenshinImpact(ctx));
    }
}
