package com.keyautomation.mybar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoadingDialogFragment extends DialogFragment{

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
            if (value <= 1) dismiss();
            else {
                countdown.setText(String.valueOf(value - 1));
                mHandler.postDelayed(loading_thread, 1000);
            }
        }
    };

    private Handler mHandler;

    public static LoadingDialogFragment getInstance(String title/*, DialogDismissListener dismissListener*/) {
        LoadingDialogFragment vDialog = new LoadingDialogFragment();
        vDialog.title = title;
        //vDialog.dismissListener = dismissListener;
        return vDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("context", String.valueOf(context));
        if (context instanceof DialogDismissListener)
            this.dismissListener = (DialogDismissListener) context;
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
        if (dismissListener != null) {
            Log.d("Arrived", String.valueOf(dismissListener));
            dismissListener.onDialogDismiss();
            LoginActivity.instance.loadOrdersActivity();
        }
    }


}