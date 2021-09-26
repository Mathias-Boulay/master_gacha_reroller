package fr.spse.master_gacha_reroller;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.spse.master_gacha_reroller.custom_views.SearchBar;
import fr.spse.master_gacha_reroller.recyclerview.RerollableAppComparator;
import fr.spse.master_gacha_reroller.recyclerview.RerollableAppAdapter;
import fr.spse.master_gacha_reroller.rerollable.BaseRerollableApp;
import fr.spse.master_gacha_reroller.rerollable.RerollAppList;

public class MainActivity extends AppCompatActivity {

    SearchBar searchBar;
    RecyclerView rerollableAppRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        RerollAppList.initList(this); // Needed to provide initial context

        rerollableAppRecyclerView = findViewById(R.id.reroll_activity_recycler_view);
        searchBar = findViewById(R.id.reroll_activity_search_bar);

        //Setup the recycler view, and fill the adapter.
        RerollableAppAdapter adapter = new RerollableAppAdapter(getApplicationContext(), BaseRerollableApp.class, new RerollableAppComparator());
        rerollableAppRecyclerView.setAdapter(adapter);
        rerollableAppRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.edit()
                .add(RerollAppList.rerollableAppList)
                .commit();

        //Listen to changes to make new queries
        searchBar.addTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final List<BaseRerollableApp> filteredModelList = filterRerollableApp(RerollAppList.rerollableAppList, charSequence.toString());
                adapter.edit().replaceAll(filteredModelList).commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Additional button behavior.
    }

    /**
     * Filter apps by (package) name, given a string query
     * @param models list of BaseRerollableApp to filter through
     * @param query The query provided
     * @return A list of filtered apps
     */
    private static List<BaseRerollableApp> filterRerollableApp(List<BaseRerollableApp> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<BaseRerollableApp> filteredModelList = new ArrayList<>();
        for (BaseRerollableApp model : models) {
            final String packageName = model.getAppPackageName().toLowerCase();
            final String appName = model.getAppName().toLowerCase();
            if (packageName.contains(lowerCaseQuery) || appName.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


}