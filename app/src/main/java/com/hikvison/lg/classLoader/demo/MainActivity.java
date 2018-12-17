package com.hikvison.lg.classLoader.demo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printClassLoader();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pathName = getExternalFilesDir(null).getPath()+"/app-debug.apk";
                ClassLoader classLoader = new DexClassLoader(pathName+"",null,null,getClassLoader());
                while (classLoader != null) {
                    Log.d("ligang", classLoader.toString());//1
                    classLoader = classLoader.getParent();
                }
                try {
                    Class<?> cls=classLoader.loadClass("com.hikvison.lg.dexdemo.TextTest");
                    Method method=cls.getMethod("getString",null);
                    Object object=cls.getConstructor().newInstance();
                    String str=(String) method.invoke(object,null);
                    mTextView.setText(""+str);
                } catch (Exception e) {
                    e.printStackTrace();
                } }
        });
        mTextView = (TextView) findViewById(R.id.text);
    }

    public void printClassLoader() {
        ClassLoader loader = getClassLoader();
        while (loader != null) {
            Log.d("ligang", loader.toString());//1
            loader = loader.getParent();
        }
    }
}
