package hkust.com.bitwise;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import hkust.com.bitwise.models.FoodOrder;

@EActivity(R.layout.activity_order)
public class OrderActivity extends AppCompatActivity {
    @Extra
    FoodOrder selectedOrder;

    @ViewById
    com.github.jiahuanyu.circletimerview.CircleTimerView timer;

    @ViewById
    TextView district;

    @ViewById
    TextView ordersCompleted;

    @ViewById
    Toolbar toolbar;

    CountDownTimer countDownTimer;

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timer.setEnabled(false);
        countDownTimer = new CountDownTimer(20000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("TICK " + (int) ((millisUntilFinished / 20000f) * 3600f));
                timer.setCurrentTime((int) ((millisUntilFinished / 20000f) * 3600f));
                timer.setHintText("Food being prepared...");
            }

            @Override
            public void onFinish() {
                timer.setCurrentTime(0);
                timer.setHintText("Food ready!");
                selectedOrder.setCurrentState(1);
                selectedOrder.save();
            }
        };
        countDownTimer.start();

        district.setText(selectedOrder.getVendor().getName());
        ordersCompleted.setText(selectedOrder.getOrderDate().toString());
    }
}
