package com.example.pharmacy_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPVerification extends AppCompatActivity {

    private EditText otpET1, otpET2, otpET3, otpET4, otpET5, otpET6;
    private TextView resendBtn;

    private boolean resendEnabled = false;

    private int resendTime = 60;

    private Button btn_verify;
    private int selectedETPosition = 0;

    private String phone;
    FirebaseAuth mAuth;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        otpET1 = findViewById(R.id.otpET1);
        otpET2 = findViewById(R.id.otpET2);
        otpET3 = findViewById(R.id.otpET3);
        otpET4 = findViewById(R.id.otpET4);
        otpET5 = findViewById(R.id.otpET5);
        otpET6 = findViewById(R.id.otpET6);

        mAuth = FirebaseAuth.getInstance();

        resendBtn = findViewById(R.id.resendBtn);

        btn_verify = findViewById(R.id.btn_verify);
        final TextView otpMobile = findViewById(R.id.otpMobile);

        phone = "+91" + getIntent().getStringExtra("phone").replace(" ", "");

        otpMobile.setText(phone);

        otpET1.addTextChangedListener(textWatcher);
        otpET2.addTextChangedListener(textWatcher);
        otpET3.addTextChangedListener(textWatcher);
        otpET4.addTextChangedListener(textWatcher);
        otpET5.addTextChangedListener(textWatcher);
        otpET6.addTextChangedListener(textWatcher);

        showKeyboard(otpET1);
        sendVerificationCode();
        startCountDownTimer();

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(resendEnabled){
                    //handle resend
                    sendVerificationCode();
                }

            }
        });

        btn_verify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String generateOtp = otpET1.getText().toString() + otpET2.getText().toString() + otpET3.getText().toString() + otpET4.getText().toString() + otpET5.getText().toString() + otpET6.getText().toString();

                if (TextUtils.isEmpty(generateOtp)){
                    Toast.makeText(OTPVerification.this,
                            "Enter OTP", Toast.LENGTH_SHORT).show();
                } else if (generateOtp.length() != 6) {
                    Toast.makeText(OTPVerification.this,
                            "Enter valid OTP", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, generateOtp);
                    signInWithPhoneAuthCredential(credential);
                }
            };
        });
    }


    private void sendVerificationCode() {
        startCountDownTimer();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }
                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                Toast.makeText(OTPVerification.this,
                                        "Failed!", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                OTPVerification.this.id = id;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(OTPVerification.this, MainActivity.class));
                            finish();
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(OTPVerification.this, "Verification failed! Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showKeyboard(EditText otpET){

        otpET.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer(){
        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime*1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend Code (" + (millisUntilFinished / 1000) + ")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(R.color.primary));
            }
        }.start();
    }
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length() > 0){
                if(selectedETPosition == 0){
                    otpET1.clearFocus();
                    selectedETPosition = 1;
                    showKeyboard(otpET2);
                }else if(selectedETPosition == 1){
                    otpET2.clearFocus();
                    selectedETPosition = 2;
                    showKeyboard(otpET3);
                }else if(selectedETPosition == 2){
                    otpET3.clearFocus();
                    selectedETPosition = 3;
                    showKeyboard(otpET4);
                }else if(selectedETPosition == 3){
                    otpET4.clearFocus();
                    selectedETPosition = 4;
                    showKeyboard(otpET5);
                }else if(selectedETPosition == 4){
                    otpET5.clearFocus();
                    selectedETPosition = 5;
                    showKeyboard(otpET6);
                }else{
                    otpET6.clearFocus();
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_DEL){
            if(selectedETPosition == 5){
                selectedETPosition = 4;
                showKeyboard(otpET5);
            }
            else if(selectedETPosition == 4){
                selectedETPosition = 3;
                showKeyboard(otpET4);
            }else if(selectedETPosition == 3){
                selectedETPosition = 2;
                showKeyboard(otpET3);
            }
            else if(selectedETPosition == 2){
                selectedETPosition = 1;
                showKeyboard(otpET2);
            }
            else if(selectedETPosition == 1){
                selectedETPosition = 0;
                showKeyboard(otpET1);
            }
            return true;
        }
        else{
            return super.onKeyUp(keyCode, event);
        }
    }
}