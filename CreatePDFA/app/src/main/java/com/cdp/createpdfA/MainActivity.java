package com.cdp.createpdfA;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    //second configuration
    //jajaj
    //Tercera configuracion
    Button button;
    //mas modificacion

    Bitmap bmp, scaledBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.keeui);
        scaledBitmap = Bitmap.createScaledBitmap(bmp,200,200,false);
        TextView titulo1;
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();


        titulo1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                         if (titulo1.getVisibility() == View.VISIBLE){
                                             titulo1.setVisibility(View.GONE);
                                         }else{
                                             titulo1.setVisibility(View.VISIBLE);
                                         }


                                       }
                                   }
        );

    }

    private void createPDF(){
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                PdfDocument document = new PdfDocument();

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1499, 5000, 1).create();

                // start a page
                PdfDocument.Page page = document.startPage(pageInfo);


                // draw something on the page
                LayoutInflater inflater = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    inflater = (LayoutInflater)
                            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                }
                View content = inflater.inflate(R.layout.pdf_report, null);

                int measureWidth = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getWidth(), View.MeasureSpec.EXACTLY);
                int measuredHeight = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getHeight(), View.MeasureSpec.EXACTLY);

                content.measure(measureWidth, measuredHeight);
                content.layout(0, 0, page.getCanvas().getWidth(), page.getCanvas().getHeight());

//                tvName = (TextView)content.findViewById(R.id.tvName);
//                tvDate = (TextView)content.findViewById(R.id.tvDate);
//                tvAge = (TextView)content.findViewById(R.id.tvAge);
//                tvGender = (TextView)content.findViewById(R.id.tvGender);
//                tvPhone = (TextView)content.findViewById(R.id.tvPhone);
//                lvList = (ListView)content.findViewById(R.id.lvList);
//                lvList.setAdapter(adapter);
//                Utils.setListViewHeight(lvList, CreatePDFDemo.this);
//
//                tvName.setText(name);
//                tvAge.setText(age + "Y");
//                tvGender.setText(gender);
//                tvPhone.setText(phone);

                content.draw(page.getCanvas());

                // finish the page
                document.finishPage(page);
                // add more pages
                // write the document content
                File file = new File(Environment.getExternalStorageDirectory(),"/KeeUIReport.pdf");
                try {
                    document.writeTo(new FileOutputStream(file));
                    Toast.makeText(MainActivity.this, "Report", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                document.close();
            }
        });
    }
}