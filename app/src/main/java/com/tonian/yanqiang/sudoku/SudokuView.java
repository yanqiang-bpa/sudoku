package com.tonian.yanqiang.sudoku;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import static com.tonian.yanqiang.sudoku.R.color.material_blue_grey_800;


public class SudokuView extends View {


    private int ninthPanelWidth=0;
    private int ninthPanelHight=0;
    private Game game=null;
    private int[] matrix;
    private int[] matrix_flag;
    private AlertDialog dialog=null;

    public SudokuView(Context context,String mode) {
        super(context);
        game=new Game(mode);
        //matrix = game.fromStringToMatrix();
        matrix=game.puzzleMatrix;
        matrix_flag=game.matrix_flag;


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();


        int x_axis= ((int) x/ninthPanelWidth);
        int y_axis= ((int) y/ninthPanelHight);

        final int index=(y_axis)*9+x_axis;



//        Toast.makeText(this.getContext(), matrix[index]+"",
//                Toast.LENGTH_SHORT).show();

        if(matrix[index]!=0&&matrix_flag[index]==0) return false;

        // already filled number
        int filled_num_in_cell=matrix[index];
        int x_filled[]=new int[9];
        int y_filled[]=new int[9];
        int mini_square[]=new int[9];

        StringBuffer sb=new StringBuffer();

        for(int r=0;r<9;r++){
            int index_r=(y_axis)*9+r;
            x_filled[r]=matrix[index_r];
        }


        for(int c=0;c<9;c++){
            int index_c=c*9+x_axis;
            y_filled[c]=matrix[index_c];
        }



        //check filled number in small square
        int x_zone_axis= ((int) x/ninthPanelWidth)/3;
        int y_zone_axis= ((int) y/ninthPanelHight)/3;

        for(int inner_r=0;inner_r<3;inner_r++){
            for(int inner_c=0;inner_c<3;inner_c++){
                if(((y_zone_axis*3+inner_c)!=y_axis)&&(x_zone_axis*3+inner_r!=x_axis))
                mini_square[inner_r*3+inner_c]=matrix[(y_zone_axis*3+inner_c)*9+x_zone_axis*3+inner_r];

            }
        }




        //total used numbers from x_filled, y_filled, mini_square
        final int used[]=new int[27];
        int u=0;
        for(int xf=0;xf<9;xf++){
            if(x_filled[xf]!=0) {
                used[u] = x_filled[xf];
                sb.append(used[u]);
                u++;
            }
        }
        for(int yf=0;yf<9;yf++){
            if(y_filled[yf]!=0){
                used[u]=y_filled[yf];
                sb.append(used[u]);
                u++;
            }
        }

        for(int mf=0;mf<9;mf++){
            if(mini_square[mf]!=0){
                used[u]=mini_square[mf];
                sb.append(used[u]);
                u++;
            }
        }

//        Toast.makeText(this.getContext(), sb.toString(),
//                Toast.LENGTH_LONG).show();



        LayoutInflater layoutInflater=LayoutInflater.from(this.getContext());
        View layoutView=layoutInflater.inflate(R.layout.select_dialog,null);

        AlertDialog.Builder builder=new AlertDialog.Builder(this.getContext());
        builder.setView(layoutView);
        dialog=builder.create();
        dialog.show();

        Button bt1=(Button)layoutView.findViewById(R.id.button1);
        Button bt2=(Button)layoutView.findViewById(R.id.button2);
        Button bt3=(Button)layoutView.findViewById(R.id.button3);
        Button bt4=(Button)layoutView.findViewById(R.id.button4);
        Button bt5=(Button)layoutView.findViewById(R.id.button5);
        Button bt6=(Button)layoutView.findViewById(R.id.button6);
        Button bt7=(Button)layoutView.findViewById(R.id.button7);
        Button bt8=(Button)layoutView.findViewById(R.id.button8);
        Button bt9=(Button)layoutView.findViewById(R.id.button9);
        Button btn[]=new Button[9];
        btn[0]=bt1;
        btn[1]=bt2;
        btn[2]=bt3;
        btn[3]=bt4;
        btn[4]=bt5;
        btn[5]=bt6;
        btn[6]=bt7;
        btn[7]=bt8;
        btn[8]=bt9;

        for(int b=0;b<9;b++) {

            btn[b].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button bt=(Button)v;
                    String selectNum=bt.getText().toString();

                    int number=Integer.parseInt(selectNum);

                    int flag=0;
                    for(int f=0;f<27;f++){
                        if(number==used[f])
                            flag=1;
                    }
                    if(flag==0) {
                        matrix[index] = number;
                        invalidate();
                    }
                    else if(flag==1) ;
//                        Toast.makeText(SudokuView.this.getContext(), "this number is used.",
//                                Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
        }




        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        ninthPanelWidth=getWidth()/9;
        ninthPanelHight=getHeight()/9;

        Paint BGPaint=new Paint();
        BGPaint.setColor(0xffe6f0ff);

        Paint darkPain=new Paint();
        darkPain.setColor(Color.DKGRAY);
        darkPain.setStyle(Paint.Style.STROKE);

        Paint lightPain=new Paint();
        lightPain.setColor(Color.LTGRAY);

        canvas.drawRect(0,0,getWidth(),getHeight(),BGPaint);


        Paint numPain=new Paint();
        numPain.setTextSize(ninthPanelHight*0.75f);
        numPain.setColor(Color.BLACK);
        numPain.setStyle(Paint.Style.STROKE);
        numPain.setTextAlign(Paint.Align.CENTER);

        //player's paint
        Paint playerNumPain=new Paint();
        playerNumPain.setTextSize(ninthPanelHight*0.75f);
        playerNumPain.setColor(Color.GREEN);
        playerNumPain.setStyle(Paint.Style.STROKE);
        playerNumPain.setTextAlign(Paint.Align.CENTER);




        for(int i=0;i<8;i++){
            if((i+1)%3==0) {
                canvas.drawLine(0, (i + 1) * ninthPanelHight, 9 * ninthPanelWidth, (i + 1) * ninthPanelHight, darkPain);
                canvas.drawLine((i + 1) * ninthPanelWidth, 0, (i + 1) * ninthPanelWidth, 9 * ninthPanelHight, darkPain);
            }
            else {
                canvas.drawLine(0, (i + 1) * ninthPanelHight, 9 * ninthPanelWidth, (i + 1) * ninthPanelHight, lightPain);
                canvas.drawLine((i + 1) * ninthPanelWidth, 0, (i + 1) * ninthPanelWidth, 9 * ninthPanelHight, lightPain);

            }
        }



        for(int j=0;j<9; j++){
            for(int i=0;i<9;i++){
                int index=(j)*9+i;
                int x=matrix[index];
                if(x!=0&&matrix_flag[index]==0)
                    canvas.drawText(x+"",i*ninthPanelWidth+ninthPanelWidth/2,j*ninthPanelHight+ninthPanelHight*0.8f,numPain);
                else if(x!=0&&matrix_flag[index]==1)
                    canvas.drawText(x+"",i*ninthPanelWidth+ninthPanelWidth/2,j*ninthPanelHight+ninthPanelHight*0.8f,playerNumPain);
            }
        }


//        canvas.drawText(matrix[12]+"",3*ninthPanelWidth+ninthPanelWidth/2,2*ninthPanelHight+ninthPanelHight*0.8f,numPain);
        super.onDraw(canvas);
    }
}
