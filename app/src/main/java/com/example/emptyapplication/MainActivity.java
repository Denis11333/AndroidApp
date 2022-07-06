package com.example.emptyapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ClientProtocolException;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private List<Cadet> cadetList = new ArrayList<>();
    private TypeOfWork typeOfWork = TypeOfWork.Duty;
    char aChar = 'A';
    private String urlClear = "https://sheets.googleapis.com/v4/spreadsheets/1ZASii3WhxszLheph-XroGVJINntqyfixEo2vhcKlIx0/values/Прибирання!A1:Z8?key=AIzaSyCc_vw8RW0JP81CqiS5sjTn2SJhrQyPbtw";
    private String urlDuty = "https://sheets.googleapis.com/v4/spreadsheets/1ZASii3WhxszLheph-XroGVJINntqyfixEo2vhcKlIx0/values/Наряди!A1:Z8?key=AIzaSyCc_vw8RW0JP81CqiS5sjTn2SJhrQyPbtw";
    private String urlWork = "https://sheets.googleapis.com/v4/spreadsheets/1ZASii3WhxszLheph-XroGVJINntqyfixEo2vhcKlIx0/values/Роботи!A1:Z8?key=AIzaSyCc_vw8RW0JP81CqiS5sjTn2SJhrQyPbtw";
    private String urlReales = "https://sheets.googleapis.com/v4/spreadsheets/1ZASii3WhxszLheph-XroGVJINntqyfixEo2vhcKlIx0/values/Звільнення!A1:Z8?key=AIzaSyCc_vw8RW0JP81CqiS5sjTn2SJhrQyPbtw";

    @SuppressLint({"SetTextI18n", "WrongThread"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.MyLayout);



        Thread newThread = new Thread(() -> {
            try {
                JSONObject jsonClear = new JSONObject(IOUtils.toString(new URL(urlClear), StandardCharsets.UTF_8));
                JSONObject jsonDuty = new JSONObject(IOUtils.toString(new URL(urlDuty), StandardCharsets.UTF_8));
                JSONObject jsonWorks = new JSONObject(IOUtils.toString(new URL(urlWork), StandardCharsets.UTF_8));
                JSONObject jsonReales = new JSONObject(IOUtils.toString(new URL(urlReales), StandardCharsets.UTF_8));

                JSONArray jsonArrayClear = jsonClear.getJSONArray("values");
                JSONArray jsonArrayDuty = jsonDuty.getJSONArray("values");
                JSONArray jsonArrayReales = jsonReales.getJSONArray("values");
                JSONArray jsonArrayWorks = jsonWorks.getJSONArray("values");

                for (int i = 0; i < jsonArrayClear.length(); i++) {

                    cadetList.add(new Cadet(jsonArrayClear.getJSONArray(i).getString(0), jsonArrayDuty.getJSONArray(i).length() - 1,
                            jsonArrayWorks.getJSONArray(i).length() - 1, jsonArrayClear.getJSONArray(i).length() - 1,
                            jsonArrayReales.getJSONArray(i).length() - 1)
                    );
                }

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        });

        newThread.start();


        fillCadets(cadetList, typeOfWork);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        Button buttonDo = findViewById(R.id.button);
        Button buttonSave = findViewById(R.id.button4);

        /**
         * On click Зберегти
         */

        buttonSave.setOnClickListener(view -> {

            for (int i = 0; i < cadetList.size(); i++) {
                EditText editText = linearLayout.findViewWithTag(cadetList.get(i).getFullName() + "Count");

                try {
                    cadetList.get(i).setValueOfNeedType(typeOfWork, Integer.parseInt(editText.getText().toString()));
                }catch (Exception e){
                    System.out.println("Incorrect input of Number");
                    editText.setText(String.valueOf(cadetList.get(i).getValueOfNeedType(typeOfWork)));
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE); imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }

        });

        /**
         * On click Назначити
         */

        buttonDo.setOnClickListener(view -> {
            yesNoAction(cadetList, new ArrayList<>());

        });

        /**
         * On change tab action
         */

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(Objects.equals(tab.getText(), "Наряди")) {
                    fillCadets(cadetList, TypeOfWork.Duty);
                    typeOfWork = TypeOfWork.Duty;
                }

                else if(Objects.equals(tab.getText(), "Роботи")) {
                    fillCadets(cadetList, TypeOfWork.Work);
                    typeOfWork = TypeOfWork.Work;
                }

                else if(tab.getText().equals("Прибирання")) {
                    fillCadets(cadetList, TypeOfWork.Clean);
                    typeOfWork = TypeOfWork.Clean;
                }
                else {
                    fillCadets(cadetList, TypeOfWork.Reales);
                    typeOfWork = TypeOfWork.Reales;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                    clearView();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Method which fill info
     */

    public void fillCadets(List<Cadet> cadetList, TypeOfWork typeOfWork){

        for (int i = 0; i <cadetList.size(); i++) {
            TextView textView = new TextView(MainActivity.this);
            textView.setText(cadetList.get(i).getFullName());
            textView.setTextSize(22);

            textView.setTag(cadetList.get(i).getFullName());

            EditText editText = new EditText(MainActivity.this);
            editText.setInputType(InputType.TYPE_CLASS_PHONE);

            editText.setText(String.valueOf(cadetList.get(i).getValueOfNeedType(typeOfWork)));
            editText.setTag(cadetList.get(i).getFullName() + "Count");

            linearLayout.addView(textView, 1);
            linearLayout.addView(editText, 2);
        }

    }

    public void clearView(){

        for (int i = 0; i < cadetList.size(); i++) {
            linearLayout.removeView(linearLayout.findViewWithTag(cadetList.get(i).getFullName()));
            linearLayout.removeView(linearLayout.findViewWithTag(cadetList.get(i).getFullName() + "Count"));
        }

    }

    public void noAction(){

        linearLayout.removeViewAt(cadetList.size() * 2 + 1);
        linearLayout.removeViewAt(cadetList.size() * 2 + 1);
        linearLayout.removeViewAt(cadetList.size() * 2 + 1);

    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void yesNoAction(List<Cadet> newCadetList, List<Cadet> visited) {

        if (cadetList.size() == visited.size()) {
            Toast toast = Toast.makeText(MainActivity.this, "Всі работягі вже пропущені ", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        Collections.sort(newCadetList, Comparator.comparing(x -> x.getValueOfNeedType(typeOfWork)));

        Cadet cadet = newCadetList.stream().filter(x -> !visited.contains(x)).collect(Collectors.toList()).stream().findFirst().get();

        TextView textView = new TextView(MainActivity.this);
        textView.setText(cadet.getFullName() + " був обранний");
        textView.setTextSize(25);

        linearLayout.addView(textView, cadetList.size() * 2 + 1);

        Button yes = new Button(MainActivity.this);
        Button no = new Button(MainActivity.this);

        yes.setText("Підтвердити");
        no.setText("Відхилити");

        linearLayout.addView(yes, cadetList.size() * 2 + 2);
        linearLayout.addView(no, cadetList.size() * 2 + 3);

        yes.setOnClickListener(view1 -> {
            cadet.setValueOfNeedType(typeOfWork, cadet.getValueOfNeedType(typeOfWork) + 1);

            clearView();
            fillCadets(cadetList, typeOfWork);

            noAction();
        });

        no.setOnClickListener(view12 -> {
            visited.add(cadet);

            noAction();

            yesNoAction(newCadetList, visited);
        });

    }



}
