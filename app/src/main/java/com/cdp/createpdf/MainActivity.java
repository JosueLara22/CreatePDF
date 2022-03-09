package com.cdp.createpdf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
// ultimas añadidas
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    Button button;
    Bitmap bmp, scaledBitmap;
    int pageWidth = 400;
    int pageHeight = 600;
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

    }
    private void createPDF(Context context){
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
/*                PrintAttributes builder = new PrintAttributes.Builder().build();
                PrintedPdfDocument document = new PrintedPdfDocument(context, builder);
                PdfDocument.Page page = document.startPage(0);
                View content = R.layout.pdf_report.getContentView();*/

                dateObj = new Date();
                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();
                Paint titlePaint = new Paint();
                myPaint.setColor(getResources().getColor(R.color.purple_200));


                PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(612, 792, 1).create();
                PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                Canvas canvas = myPage1.getCanvas();

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setTextSize(40);
                titlePaint.setColor(Color.BLACK);
                canvas.drawText("Keeui Solar report", pageWidth/2, 40, titlePaint);

                myPaint.setColor(Color.BLACK);
                myPaint.setTextSize(20f);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("Call: +55 921-267-03-27", 400, 65, myPaint);
                canvas.drawText("921-345-78-23", 400, 85, myPaint);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                titlePaint.setTextSize(30);
                canvas.drawText("System metrics", pageWidth/2, 115, titlePaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10);
                myPaint.setColor(Color.BLACK);
                canvas.drawText("Customer Name: Patricio Tognola ",20, 140, myPaint);
                canvas.drawText("Contact No. 9212670327", pageWidth-110, 140, myPaint);

                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("Report No. "+"92783992", pageWidth-10,150, myPaint);

                dateFormat = new SimpleDateFormat("dd/MM/yy");
                canvas.drawText("Date: "+dateFormat.format(dateObj),pageWidth-20,160, myPaint);

                dateFormat = new SimpleDateFormat("HH,mm,ss");
                canvas.drawText("Time: "+dateFormat.format(dateObj),pageWidth-20,170, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                canvas.drawRect(20,pageHeight-10,pageWidth-20,180, myPaint);

                //bmp = BitmapFactory.decodeResource(getResources().getDrawable(R.drawable.logo));

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                canvas.drawText("Item",50,200, myPaint);
                canvas.drawText("Description",100, 200, myPaint);
                canvas.drawText("Price", 250,200, myPaint);
                canvas.drawText("Qty",300,200, myPaint);
                canvas.drawText("Total",400,200, myPaint);

                canvas.drawLine(90,190,90,250, myPaint);
                canvas.drawLine(290,190,290,250, myPaint);
                canvas.drawLine(340,190,340,250, myPaint);
                canvas.drawLine(440,190,440,250, myPaint);

                canvas.drawText("1.",50,220, myPaint);
                canvas.drawText("Mono_Canadian Solar_CS3U...",100,220, myPaint);
                canvas.drawText("53.28",300,220, myPaint);
                canvas.drawText("6",350,220, myPaint);
                canvas.drawText("319.68",450,220, myPaint );

                //canvas.drawText("2.",40,1050, myPaint);
                //canvas.drawText("FRONIUS GALVO 1.5-1...",200,1050, myPaint);
                //canvas.drawText("1112.99",700,1050, myPaint);
                //canvas.drawText("1",900,1050, myPaint);
                //canvas.drawText("1,112.99",1050,1050, myPaint );

                //canvas.drawText("3.",40,1150, myPaint);
                //canvas.drawText("6V Deep-Cycle Trojan...",200,1150, myPaint);
                //canvas.drawText("21.488",700,1150, myPaint);
                //canvas.drawText("12",900,1150, myPaint);
                //canvas.drawText("257.856",1050,1150, myPaint );

                //canvas.drawLine(680,1200, pageWidth-20,1200, myPaint);
                //canvas.drawText("Sub total",700,1250, myPaint);
                //canvas.drawText(":",900,1300, myPaint);
                //myPaint.setTextAlign(Paint.Align.RIGHT);
                //canvas.drawText("1,690.526",pageWidth-40,1250, myPaint);

                //myPaint.setTextAlign(Paint.Align.LEFT);
                //canvas.drawText("Tax (12%)",700, 1300, myPaint);
                //canvas.drawText(":",900,1300, myPaint);
                //myPaint.setTextAlign(Paint.Align.RIGHT);
                //canvas.drawText("202.86",pageWidth-40,1300, myPaint);
                //myPaint.setTextAlign(Paint.Align.LEFT);

                //myPaint.setColor(Color.BLACK);
                //myPaint.setTextSize(50f);
                //myPaint.setTextAlign(Paint.Align.LEFT);
                //canvas.drawText("Total",700,1415, myPaint);
                //myPaint.setTextAlign(Paint.Align.RIGHT);
                //canvas.drawText("1,893.39",pageWidth-40,1415, myPaint);





                //canvas.save();
                myPdfDocument.finishPage(myPage1);

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/PrimerPDF3.pdf");

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
}