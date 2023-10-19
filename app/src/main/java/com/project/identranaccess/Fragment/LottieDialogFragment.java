package com.project.identranaccess.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.project.identranaccess.R;


    public class LottieDialogFragment extends Dialog {

        public LottieDialogFragment(@NonNull Context context) {
            super(context);

            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.gravity = Gravity.CENTER;
            getWindow().setAttributes(params);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setTitle(null);
            setCancelable(false);
            setOnCancelListener(null);
            View view = LayoutInflater.from(context).inflate(R.layout.lottie, null);
            setContentView(view);
        }


    }

