package fr.spse.master_gacha_reroller.recyclerview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

import fr.spse.master_gacha_reroller.rerollable.BaseRerollableApp;
import fr.spse.master_gacha_reroller.custom_views.rerollableAppItem;

public class RerollableAppAdapter extends SortedListAdapter<BaseRerollableApp> {
    public RerollableAppAdapter(@NonNull Context context, @NonNull Class<BaseRerollableApp> itemClass, @NonNull Comparator<BaseRerollableApp> comparator) {
        super(context, itemClass, comparator);
    }


    @NonNull
    @Override
    protected SortedListAdapter.ViewHolder<? extends BaseRerollableApp> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        rerollableAppItem rerollAppItemView = new rerollableAppItem(parent.getContext());

        return new ViewHolder(rerollAppItemView);
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends SortedListAdapter.ViewHolder<BaseRerollableApp> {
        private final rerollableAppItem rerollableAppItem;

        public ViewHolder(rerollableAppItem view) {
            super(view);
            // Define click listener for the ViewHolder's View
            rerollableAppItem = view;
        }
        public rerollableAppItem getRerollableAppItem(){
            return rerollableAppItem;
        }

        @Override
        protected void performBind(@NonNull BaseRerollableApp rerollableApp) {
            rerollableApp.setupData(getRerollableAppItem());
            getRerollableAppItem().setData(rerollableApp);
        }
    }





}
