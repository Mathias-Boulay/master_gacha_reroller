package fr.spse.master_gacha_reroller.custom_views;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import fr.spse.master_gacha_reroller.R;
import fr.spse.master_gacha_reroller.utils.Tools;

public class SearchBar extends LinearLayout {

    private EditText searchText;

    public SearchBar(Context context) {
        super(context);
        initView();
    }

    public SearchBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView(){
        View v = LayoutInflater.from(getContext()).inflate(R.layout.search_bar, this);

        searchText = v.findViewById(R.id.search_bar_edit_text);
        setBackground(getResources().getDrawable(R.drawable.search_bar_background));
        setElevation(15);

    }

    public void addTextWatcher(TextWatcher watcher){
        searchText.addTextChangedListener(watcher);
    }

    public void removeTextWatcher(TextWatcher watcher){
        searchText.removeTextChangedListener(watcher);
    }



}
