package com.keyautomation.mybar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LoadingFragment extends DialogFragment {

    public static final String WAIT_DIALOG_TAG = "wait_dialog_tag";
    TextView countdown;

    public interface DialogDismissListener {
        void onDialogDismiss();
    }

    private String title;
    private DialogDismissListener dismissListener;

    Runnable loading_thread = new Runnable() {
        public void run() {
            int value = Integer.parseInt(countdown.getText().toString());
            if(value <= 1)dismiss();
            else{
                countdown.setText( String.valueOf(value - 1));
                mHandler.postDelayed(loading_thread, 1000);
            }
        }
    };

    private Handler mHandler;

    public static LoadingFragment getInstance(String title, DialogDismissListener dismissListener) {
        LoadingFragment vDialog = new LoadingFragment();
        vDialog.title = title;
        vDialog.dismissListener = dismissListener;
        return vDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = View.inflate(getContext(), R.layout.fragment_loading, null);

        countdown = dialogView.findViewById(R.id.fragment_loading_countdown);


        if (title != null)
            ((TextView) dialogView.findViewById(R.id.fragment_loading_title)).setText(title);
        //((TextView) dialogView.findViewById(R.id.fragment_loading_message)).setText(getForcedString(getActivity(), R.string.please_wait));


        mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(loading_thread);

        builder.setView(dialogView);

        return builder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        mHandler.removeCallbacks(loading_thread);
    }


}