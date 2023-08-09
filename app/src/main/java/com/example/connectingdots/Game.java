package com.example.connectingdots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Game extends SurfaceView implements SurfaceHolder.Callback {


    private float red1X, red1Y, red2X, red2Y, blue1X, blue1Y, blue2X, blue2Y, green1X, green1Y, green2X, green2Y;
    private Random random;

    float touchX,touchY;
    private boolean gameOver = false,collide_red1=false,collide_red2=false,collide_blue1=false,collide_blue2=false,collide_green1=false,collide_green2=false;
    private boolean check_red=false,check_blue=false,check_green=false;
    private List<PointF> pathPoints;

    private Paint paint;
    private Path path;


    float centerX, centerY;




    private Paint pLine;
    private Path touchPath;

    private int score, red_count, green_count, blue_count;

    public MainThread thread;

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }


    public Game(Context context) {
        super(context);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        getHolder().addCallback(this);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();

        red1X = 800;
        red1Y = 800;
        red2X = 700;
        red2Y = 1300;

        green1X = 400;
        green1Y = 1200;
        green2X = 500;
        green2Y = 1400;

        blue1X = 500;
        blue1Y = 1550;
        blue2X = 350;
        blue2Y = 950;

        pathPoints = new ArrayList<>();






    }



    public void update() {



        //check if line collides with the dots
        if(checkred1( red1X, red1Y, 30, touchX,touchY))
        {
            collide_red1=true;
        }

        if(checkred2( red2X, red2Y, 30, touchX,touchY))
        {

            collide_red2=true;
        }

        if(checkblue1( blue1X, blue1Y, 30, touchX,touchY))
        {

            collide_blue1=true;
        }

        if(checkblue2( blue2X, blue2Y, 30, touchX,touchY))
        {

            collide_blue2=true;
        }

        if(checkgreen1( green1X, green1Y, 30, touchX,touchY))
        {

            collide_green1=true;
        }

        if(checkgreen2( green2X, green2Y, 30, touchX,touchY))
        {

            collide_green2=true;
        }

        if(collide_red1==true && red_count==0)
        {

            red_count+=1;
            collide_red1=false;
            score+=1;

        }
        if(collide_red2==true && red_count==1)
        {

            red_count+=1;
            collide_red2=false;
            score+=1;


        }
        if(collide_blue1==true && blue_count==0)
        {

            blue_count+=1;
            collide_blue1=false;
            score+=1;

        }
        if(collide_blue2==true && blue_count==1)
        {

            blue_count+=1;
            collide_blue2=false;
            score+=1;

        }

        if(collide_green1==true && green_count==0)
        {

            green_count+=1;
            collide_green1=false;
            score+=1;

        }

        if(collide_green2==true && green_count==1)
        {

            green_count+=1;
            collide_green2=false;
            score+=1;

        }



        if (score==6)
        {
            gameOver=true;
        }



    }


    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);



        //Creating circular space

        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;
        Paint region = new Paint();
        region.setColor(Color.rgb(255, 255, 255));
        canvas.drawCircle(centerX, centerY, 500, region);


        //Creating red dots
        Paint red1 = new Paint();
        red1.setColor(Color.rgb(250, 0, 0));
        canvas.drawCircle(red1X, red1Y, 30, red1);

        Paint red2 = new Paint();
        red2.setColor(Color.rgb(250, 0, 0));
        canvas.drawCircle(red2X, red2Y, 30, red2);


        //Creating green dots
        Paint green1 = new Paint();
        green1.setColor(Color.rgb(0, 250, 0));
        canvas.drawCircle(green1X, green1Y, 30, green1);

        Paint green2 = new Paint();
        green2.setColor(Color.rgb(0, 250, 0));
        canvas.drawCircle(green2X, green2Y, 30, green2);


        //Creating blue dots
        Paint blue1 = new Paint();
        blue1.setColor(Color.rgb(0, 0, 250));
        canvas.drawCircle(blue1X, blue1Y, 30, blue1);

        Paint blue2 = new Paint();
        blue2.setColor(Color.rgb(0, 0, 250));
        canvas.drawCircle(blue2X, blue2Y, 30, blue2);

        canvas.drawPath(path, paint);

        if(gameOver==true)
        {
            Paint gameOverPaint = new Paint();
            gameOverPaint.setColor(Color.BLACK);
            gameOverPaint.setTextSize(100); // Adjust the text size as needed
            gameOverPaint.setTextAlign(Paint.Align.CENTER);

            String gameOverText = "Score:"+score;
            float textX = getWidth() / 2f;
            float textY = getHeight() / 2f;

            canvas.drawText(gameOverText, textX, textY, gameOverPaint);
        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);
                pathPoints.add(new PointF(touchX, touchY));

                break;
            case MotionEvent.ACTION_MOVE:

                path.lineTo(touchX, touchY);
                pathPoints.add(new PointF(touchX, touchY));
                checkred1( red1X, red1Y, 30, touchX,touchY);
                checkred2( red2X, red2Y, 30, touchX,touchY);
                checkblue1( blue1X, blue1Y, 30, touchX,touchY);
                checkblue2( blue2X, blue2Y, 30, touchX,touchY);
                checkgreen1( green1X, green1Y, 30, touchX,touchY);
                checkgreen2( green2X, green2Y, 30, touchX,touchY);
                break;

            case MotionEvent.ACTION_UP:
                // No action needed
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }







    public boolean checkred1(float red1X, float red1Y, float circleRadius, float touchX,float touchY)
    {
     float distance = (float) Math.sqrt(Math.pow(red1X - touchX, 2) + Math.pow(red1Y - touchY, 2));
        if (distance <= 30) {
        return true;

        }
        else {
            return false;
        }

    }

    public boolean checkred2(float red1X, float red1Y, float circleRadius, float touchX,float touchY)
    {
        float distance = (float) Math.sqrt(Math.pow(red1X - touchX, 2) + Math.pow(red1Y - touchY, 2));
        if (distance <= 30) {
            return true;

        }
        else {
            return false;
        }

    }


    public boolean checkblue1(float red1X, float red1Y, float circleRadius, float touchX,float touchY)
    {
        float distance = (float) Math.sqrt(Math.pow(red1X - touchX, 2) + Math.pow(red1Y - touchY, 2));
        if (distance <= 30) {
            return true;

        }
        else {
            return false;
        }

    }


    public boolean checkblue2(float red1X, float red1Y, float circleRadius, float touchX,float touchY)
    {
        float distance = (float) Math.sqrt(Math.pow(red1X - touchX, 2) + Math.pow(red1Y - touchY, 2));
        if (distance <= 30) {
            return true;

        }
        else {
            return false;
        }

    }

    public boolean checkgreen1(float red1X, float red1Y, float circleRadius, float touchX,float touchY)
    {
        float distance = (float) Math.sqrt(Math.pow(red1X - touchX, 2) + Math.pow(red1Y - touchY, 2));
        if (distance <= 30) {
            return true;

        }
        else {
            return false;
        }


    }

    public boolean checkgreen2(float red1X, float red1Y, float circleRadius, float touchX,float touchY) {
        float distance = (float) Math.sqrt(Math.pow(red1X - touchX, 2) + Math.pow(red1Y - touchY, 2));
        if (distance <= 30) {
            return true;

        } else {
            return false;
        }


    }}


