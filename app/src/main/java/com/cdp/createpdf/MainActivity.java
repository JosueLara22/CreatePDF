package com.cdp.createpdf;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;


// ultimas añadidas

public class MainActivity extends AppCompatActivity {

    Button button;
    Bitmap bmp, scaledBitmap;
    int pageWidth = 612;
    int pageHeight = 792;
    int margin = 20;
    Date dateObj;
    DateFormat dateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        createPDF(this);
        PieChart mPieChart;
        BarChart mBarChart;
        String[] mMonths =new String []{"E","F","Mr","A","My","Jn","Jl","A","S","O","N","D"};
        float[] msale =new float[]{12,10,(float) 9.1,3,6,12,5,(float)10.68,11,15,14, (float) 11.8};
        int[] mColors =new int[]{Color.GREEN,Color.GRAY,Color.GREEN,Color.GRAY,Color.GREEN,
                Color.GRAY,Color.GREEN,Color.GRAY,Color.GREEN,Color.GRAY,Color.GREEN,Color.GRAY};
        mBarChart =(BarChart)findViewById(R.id.barChart);
        mPieChart =(PieChart) findViewById(R.id.pieChart);
        //createdCharts();
        GraphingUtils.setBarGraphic(mBarChart, msale, mMonths, Color.GREEN, "Series",
                    Color.BLACK, Color.WHITE, 3000, "LABEL");

    }
    private void createPDF(Context context){
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                dateObj = new Date();
                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();
                Paint titlePaint = new Paint();
                myPaint.setColor(getResources().getColor(R.color.purple_200));
                RelativeLayout reportLayoutRL = findViewById(R.id.reportLayout);
                Bitmap bitmap = getBitmapFromView(reportLayoutRL);

                PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(612, 792, 1).create();
                PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                Canvas canvas = myPage1.getCanvas();
                Bitmap bmp, scaledBitmap;
                //bmp = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
                scaledBitmap = Bitmap.createScaledBitmap(bitmap,pageWidth,pageWidth,false);
                //myPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawBitmap(scaledBitmap,0,90,myPaint);
                //canvas.drawBitmap(bitmap,0,0,myPaint);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setTextSize(50);//25 por cada letra 27 por numeros con 50. 50 por cada letra y 55 por cada numero
                titlePaint.setColor(Color.BLACK);
                canvas.drawText("Keeui Solar report", pageWidth/2, 50, titlePaint);//titulo

                myPaint.setColor(Color.BLACK);
                myPaint.setTextSize(10);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("Call: +55 921-267-03-27", pageWidth-margin, 75, myPaint);
                canvas.drawText("921-345-78-23", pageWidth-margin, 85, myPaint);

                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                titlePaint.setTextSize(25);
                canvas.drawText("System metrics", pageWidth/2, 110, titlePaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10);
                myPaint.setColor(Color.BLACK);
                canvas.drawText("Customer Name: Patricio Tognola ",margin, 140, myPaint);
                canvas.drawText("Contact No. 9212670327", margin, 150, myPaint);
                canvas.drawText("Report No. "+"92783992", margin,160, myPaint);

                myPaint.setTextAlign(Paint.Align.RIGHT);

                dateFormat = new SimpleDateFormat("dd/MM/yy");
                canvas.drawText("Date: "+dateFormat.format(dateObj),pageWidth-margin,140, myPaint);

                dateFormat = new SimpleDateFormat("HH,mm,ss");
                canvas.drawText("Time: "+dateFormat.format(dateObj),pageWidth-margin,150, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                canvas.drawRect(margin,pageHeight-margin,pageWidth-margin,180, myPaint);



                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                canvas.drawText("Item",30,200, myPaint);
                canvas.drawText("Description",80, 200, myPaint);
                canvas.drawText("Price", 352,200, myPaint);
                canvas.drawText("Qty",452,200, myPaint);
                canvas.drawText("Total",502,200, myPaint);

                canvas.drawLine(70,180,70,270, myPaint);
                canvas.drawLine(pageWidth-margin-250,180,pageWidth-margin-250,270, myPaint);
                canvas.drawLine(pageWidth-margin-150,180,pageWidth-margin-150,270, myPaint);
                canvas.drawLine(pageWidth-margin-100,180,pageWidth-margin-100,270, myPaint);

                canvas.drawText("1.",30,220, myPaint);
                canvas.drawText("Mono_Canadian Solar_CS3U...",80,220, myPaint);
                canvas.drawText("53.28",352,220, myPaint);
                canvas.drawText("6",452,220, myPaint);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("319.68",pageWidth-(2*margin),220, myPaint );
                myPaint.setTextAlign(Paint.Align.LEFT);

                canvas.drawText("2.",30,235, myPaint);
                canvas.drawText("FRONIUS GALVO 1.5-1...",80,235, myPaint);
                canvas.drawText("1112.99",352,235, myPaint);
                canvas.drawText("1",452,235, myPaint);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("1,112.99",pageWidth-(2*margin),235, myPaint );
                myPaint.setTextAlign(Paint.Align.LEFT);

                canvas.drawText("3.",30,250, myPaint);
                canvas.drawText("6V Deep-Cycle Trojan...",80,250, myPaint);
                canvas.drawText("21.488",352,250, myPaint);
                canvas.drawText("12",452,250, myPaint);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("257.856",pageWidth-(2*margin),250, myPaint );
                myPaint.setTextAlign(Paint.Align.LEFT);


                canvas.drawLine(margin,270, pageWidth-margin,270, myPaint);
                canvas.drawText("Sub total:",452,285, myPaint);
                //canvas.drawText(":",900,1300, myPaint);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("1,690.526",pageWidth-(2*margin),285, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("Tax (12%)",452, 300, myPaint);
                //canvas.drawText(":",900,1300, myPaint);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("202.86",pageWidth-(2*margin),300, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);

                myPaint.setColor(Color.BLACK);
                myPaint.setTextSize(15);
                myPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("Total",452,320, myPaint);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("1,893.39",pageWidth-(2*margin),320, myPaint);





                //canvas.save();
                myPdfDocument.finishPage(myPage1);

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/ReporteKeeeUISolar.pdf");

                try {
                    myPdfDocument.writeTo(new FileOutputStream(file));
                    myPdfDocument.close();
                    Toast.makeText(MainActivity.this, "File created", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "File not created", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private Bitmap getBitmapFromView(View view){
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        view.draw(canvas);
        return returnedBitmap;
    }
}