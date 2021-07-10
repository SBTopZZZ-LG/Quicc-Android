package com.sbtopzzz.quicc.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.SearchUser;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.Activities.UserSearchActivity_Objects.Search;
import com.sbtopzzz.quicc.Activities.UserSearchActivity_Objects.SearchAdapter;
import com.sbtopzzz.quicc.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserSearchActivity extends AppCompatActivity {
    private EditText etSearch;
    private RecyclerView rvSearch;

    private int requestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        Initialize();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int localCode = new Random().nextInt();
                requestCode = localCode;

                CountDownTimer timer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        if (localCode != requestCode)
                            return;

                        Search(etSearch.getText().toString().trim().length() == 0);
                    }
                };

                timer.start();
            }
        });
    }

    private void Search(boolean emptyList) {
        if (!emptyList)
            Funcs.userSearch(CurrentUser.user.getEmailId(), "^" + etSearch.getText().toString(), new Funcs.UserSearchResult() {
                @Override
                public void onSuccess(@NonNull List<SearchUser> users) {
                    Toast.makeText(UserSearchActivity.this, users.size() + " results", Toast.LENGTH_SHORT).show();

                    List<Search> searches = new ArrayList<>();

                    SearchAdapter adapter = new SearchAdapter(searches);
                    rvSearch.setHasFixedSize(true);
                    rvSearch.setLayoutManager(new LinearLayoutManager(UserSearchActivity.this));
                    rvSearch.setAdapter(adapter);

                    for (SearchUser searchUser : users) {
                        searches.add(new Search(searchUser.uid, searchUser.name, searchUser.getEmailId()));
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onWarning(String errorText) {
                    Toast.makeText(UserSearchActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NonNull Throwable t) {
                    Toast.makeText(UserSearchActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        else {
            SearchAdapter adapter = new SearchAdapter(new ArrayList<>());
            rvSearch.setHasFixedSize(true);
            rvSearch.setLayoutManager(new LinearLayoutManager(UserSearchActivity.this));
            rvSearch.setAdapter(adapter);
        }
    }

    private void Initialize() {
        etSearch = findViewById(R.id.etSearch);

        rvSearch = findViewById(R.id.rvSearch);
    }
}