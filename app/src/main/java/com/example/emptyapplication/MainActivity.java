package com.example.emptyapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.api.services.sheets.v4.model.ValueRange;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private List<Cadet> cadetList = new ArrayList<>();
    private TypeOfWork typeOfWork = TypeOfWork.Duty;


    @SuppressLint({"SetTextI18n", "WrongThread"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.MyLayout);

        File file = getFile();

        CreateFile.createFile(file);

        Thread newThread = new Thread(() -> {

            ValueRange valueRangeNames = null;
            ValueRange valueRangeValues = null;

            try {
                valueRangeNames = GetValues.getValues("1YNn3-82eCVd26cpu7sV47C5jO60ht5_mF2E1TBAqtT8", "A2:A9", new FileInputStream(file));
                valueRangeValues = GetValues.getValues("1YNn3-82eCVd26cpu7sV47C5jO60ht5_mF2E1TBAqtT8", "B2:E9", new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(valueRangeNames.getValues());
            System.out.println(valueRangeValues.getValues());
            System.out.println(valueRangeNames.getValues().size());

            for (int i = 0; i < valueRangeNames.getValues().size(); i++) {
                String name = String.valueOf(valueRangeNames.getValues().get(i));
                name = name.substring(1,name.length() - 1);

                String Duty = String.valueOf(valueRangeValues.getValues().get(i).get(0));
                String Work = String.valueOf(valueRangeValues.getValues().get(i).get(1));
                String Clear = String.valueOf(valueRangeValues.getValues().get(i).get(2));
                String Reales = String.valueOf(valueRangeValues.getValues().get(i).get(3));


                cadetList.add(new Cadet(name, Integer.parseInt(Duty),Integer.parseInt(Work),
                        Integer.parseInt(Clear), Integer.parseInt(Reales)));
            }


        });

        newThread.start();

        do {
        fillCadets(cadetList, typeOfWork);
        }   while (cadetList.size() == 0);

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

           fillTableInGoogle(file);

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
                    buttonDo.setVisibility(View.VISIBLE);

                    fillCadets(cadetList, TypeOfWork.Duty);
                    typeOfWork = TypeOfWork.Duty;
                }

                else if(Objects.equals(tab.getText(), "Роботи")) {
                    buttonDo.setVisibility(View.VISIBLE);

                    fillCadets(cadetList, TypeOfWork.Work);
                    typeOfWork = TypeOfWork.Work;
                }

                else if(tab.getText().equals("Прибирання")) {
                    buttonDo.setVisibility(View.VISIBLE);

                    fillCadets(cadetList, TypeOfWork.Clean);
                    typeOfWork = TypeOfWork.Clean;
                }
                else {
                    fillCadets(cadetList, TypeOfWork.Reales);
                    typeOfWork = TypeOfWork.Reales;

                    buttonDo.setVisibility(View.INVISIBLE);
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

            fillTableInGoogle(getFile());
        });

        no.setOnClickListener(view12 -> {
            visited.add(cadet);

            noAction();

            yesNoAction(newCadetList, visited);
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fillTableInGoogle(File file){

        Thread thread = new Thread(() ->{

            cadetList.sort(Comparator.comparing(Cadet::getSecondName));

            List<List<Object>> list = new ArrayList<>();

            for (int i = 0; i < cadetList.size(); i++) {
                Cadet cadet = cadetList.get(i);
                System.out.println(cadet);
                List<Object> objects = new ArrayList<>();
                objects.add(cadet.getDutyCount());
                objects.add(cadet.getWorksCount());
                objects.add(cadet.getCleanCount());
                objects.add(cadet.getRealesCount());

                list.add(objects);
            }

            try {
                UpdateValues.updateValues("1YNn3-82eCVd26cpu7sV47C5jO60ht5_mF2E1TBAqtT8", "B2:E9","RAW", list, new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        thread.start();

    }

    public File getFile(){
        return new File(getApplicationContext().getFilesDir(), "text.json");
    }

}
