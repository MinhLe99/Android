package com.com.dumv.game2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.com.game2048.R;


public class MainActivity extends AppCompatActivity {
    private GridView gdvGame;
    private RelativeLayout layoutGT;
    private OVuongAdapter oVuongAdapter;
    private View.OnTouchListener listener;
    private float X, Y;
    private TextView txvPoint,txvMAX;
    private int vt1,vt2;
    private ImageView imageView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Data.getInstance().getMau(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        anhXa();
        khoiTao();
        setData();


    }

    private void anhXa() {
        gdvGame = (GridView) findViewById(R.id.gdvGame);
        txvPoint=(TextView)findViewById(R.id.txvPoint);
        txvMAX=(TextView)findViewById(R.id.txvMAX);

    }

    private void khoiTao() {
        Data.getInstance().intt();
        oVuongAdapter = new OVuongAdapter(MainActivity.this, 0, Data.getInstance().getArr());

        listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Data.getInstance().setKhoaback(true);
                switch ((event.getAction())) {
                    case MotionEvent.ACTION_DOWN:
                        X = event.getX();
                        Y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(event.getX() - X) > Math.abs(event.getY() - Y)) {
                            if (event.getX() > X) {
                                Data.getInstance().phai();
                                oVuongAdapter.notifyDataSetChanged();
                            } else {
                                Data.getInstance().trai();
                                oVuongAdapter.notifyDataSetChanged();
                            }
                        } else {

                            if (event.getY() > Y) {

                              Data.getInstance().xuong();
                                oVuongAdapter.notifyDataSetChanged();
                            } else {
                                Data.getInstance().len();
                                oVuongAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }
                txvMAX.setText(""+Data.getInstance().getPoint());
                txvPoint.setText(""+Data.getInstance().getSoDiem(MainActivity.this));
                return true;
            }
        };

    }
    private boolean b=false;
    private void setData() {
        gdvGame.setAdapter(oVuongAdapter);
        gdvGame.setOnTouchListener(listener);
    }


//xử lý khi người chơi bấm thoát
    int i=0;
    boolean k;
    @Override
    public void onBackPressed() {
         k=false;
        new AlertDialog.Builder(this)
                .setTitle("Bạn Có Muốn thoát Game ?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        k=true;
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                     k=false;
                    }
                })
                .show();
   if (k){
       super.onBackPressed();
   }

    }


}
