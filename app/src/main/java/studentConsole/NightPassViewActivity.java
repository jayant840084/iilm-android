package studentConsole;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import constants.OutPassAttributes;
import in.ac.iilm.iilm.R;
import pojo.GuardLogLeavePOJO;
import utils.ToDateTime;

public class NightPassViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_out_pass_view);

        Bundle extras = getIntent().getExtras();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        getSupportActionBar().setTitle(extras.getString(OutPassAttributes.OUT_PASS_TYPE));

        // only generate the qr code if the out pass has been confirmed
        if (extras.get(OutPassAttributes.WARDEN_SIGNED) != null &&
                extras.get(OutPassAttributes.HOD_SIGNED) != null &&
                extras.get(OutPassAttributes.DIRECTOR_SIGNED) != null)
            if (extras.getBoolean(OutPassAttributes.WARDEN_SIGNED) &&
                    extras.getBoolean(OutPassAttributes.HOD_SIGNED) &&
                    extras.getBoolean(OutPassAttributes.DIRECTOR_SIGNED))
                try {
                    ImageView qRCode = findViewById(R.id.iv_QRCode);
                    BitMatrix bitMatrix;

                    Point point = new Point();
                    getWindowManager().getDefaultDisplay().getSize(point);
                    int widthX = point.x / 2,
                            heightY = widthX;

                    GuardLogLeavePOJO guardLogLeavePOJO = new GuardLogLeavePOJO();
                    guardLogLeavePOJO.setId(extras.getString(OutPassAttributes.ID));
                    guardLogLeavePOJO.setName(extras.getString(OutPassAttributes.NAME));

                    bitMatrix = new MultiFormatWriter().encode(
                            new GsonBuilder().create().toJson(guardLogLeavePOJO),
                            BarcodeFormat.QR_CODE,
                            widthX, heightY, null
                    );
                    int width = bitMatrix.getWidth(),
                            height = bitMatrix.getHeight();
                    int[] pixels = new int[height * width];
                    for (int y = 0; y < height; y++) {
                        int offset = y * width;
                        for (int x = 0; x < width; x++) {
                            pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                        }
                    }
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
                    qRCode.setImageBitmap(bitmap);
                    qRCode.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

        ToDateTime dateTimeLeave = new ToDateTime(Long.parseLong(extras.getString(OutPassAttributes.DATE_LEAVE)));
        ToDateTime dateTimeReturn = new ToDateTime(Long.parseLong(extras.getString(OutPassAttributes.DATE_RETURN)));

        TextView dateLeave = findViewById(R.id.tv_approve_date_leave);
        dateLeave.setText(dateTimeLeave.getDate());

        TextView timeLeave = findViewById(R.id.tv_approve_time_leave);
        timeLeave.setText(dateTimeLeave.getTime());

        TextView timeReturn = findViewById(R.id.tv_approve_time_return);
        timeReturn.setText(dateTimeReturn.getTime());

        TextView dateReturn = findViewById(R.id.tv_approve_date_return);
        dateReturn.setText(dateTimeReturn.getDate());

        TextView address = findViewById(R.id.tv_approve_address);
        address.setText(extras.getString(OutPassAttributes.ADDRESS));

        TextView phoneNumber = findViewById(R.id.tv_approve_phone_number);
        phoneNumber.setText(extras.getString(OutPassAttributes.PHONE_NUMBER));

        TextView reason = findViewById(R.id.tv_approve_reason);
        reason.setText(extras.getString(OutPassAttributes.REASON));

        TextView directorApproved = findViewById(R.id.tv_approve_director);
        if (extras.get(OutPassAttributes.DIRECTOR_SIGNED) != null) {
            if (extras.getBoolean(OutPassAttributes.DIRECTOR_SIGNED)) {
                setAllowed(directorApproved);
            } else {
                setDenied(directorApproved);
            }
        } else {
            setWaiting(directorApproved);
        }

        TextView hodApproved = findViewById(R.id.tv_approve_hod);
        if (extras.get(OutPassAttributes.HOD_SIGNED) != null) {
            if (extras.getBoolean(OutPassAttributes.HOD_SIGNED)) {
                setAllowed(hodApproved);
            } else {
                setDenied(hodApproved);
            }
        } else {
            setWaiting(hodApproved);
        }

        TextView wardenApproved = findViewById(R.id.tv_approve_warden);
        if (extras.get(OutPassAttributes.WARDEN_SIGNED) != null) {
            if (extras.getBoolean(OutPassAttributes.WARDEN_SIGNED)) {
                setAllowed(wardenApproved);
            } else {
                setDenied(wardenApproved);
            }
        } else {
            setWaiting(wardenApproved);
        }
    }

    private void setAllowed(TextView textView) {
        textView.setText(getString(R.string.allowed));
        textView.setTextColor(ContextCompat.getColor(
                this,
                R.color.allow));
    }

    private void setDenied(TextView textView) {
        textView.setText(getString(R.string.denied));
        textView.setTextColor(ContextCompat.getColor(
                this,
                R.color.deny));
    }

    private void setWaiting(TextView textView) {
        textView.setText(getString(R.string.waiting));
        textView.setTextColor(ContextCompat.getColor(
                this,
                R.color.waiting));
    }
}