package com.staresmiles.amracodes.amitproject.control;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.staresmiles.amracodes.amitproject.R;
import com.staresmiles.amracodes.amitproject.control.models.User;
import com.staresmiles.amracodes.amitproject.sqlite.DBHelper;

/**
 * Created by amra on 5/2/2018.
 */

public class Controller {

    private static DBHelper dbHelper;
    private User loggedUser;


    private static Controller instance;

    public static Controller getInstance(Context context) {
        if (instance == null) {
            instance = new Controller();
            dbHelper = new DBHelper(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
        }
        return instance;
    }

    private Controller() {
    }

    public boolean register(User user) {
        dbHelper.insertUser(user.getName(), user.getMobile(),
                user.getEmail(), user.getUserName()
                , user.getPassword(), user.getCity());

        return true;
    }

    public User login(String userName, String password) {
        Cursor cursor = dbHelper.getUser(userName, password);
        User user = null;
        while (cursor.moveToNext()) {
            user = new User(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6));


        }

        return user;
    }


    public static void adjustDialogWidth(Context context, Dialog dialog) {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int dialogWidth = (int) (screenWidth * .9f);
        dialog.getWindow().setLayout(dialogWidth, dialog.getWindow().getAttributes().height);
    }

//    public void showLogOutDialog(final Activity activity, final boolean closeActivity) {
//
//        final Dialog innerAlertDialog = new Dialog(activity);
//        innerAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        innerAlertDialog.setContentView(R.layout.custom_dialog_layout);
//        adjustDialogWidth(activity, innerAlertDialog);
//        TextView title = (TextView) innerAlertDialog
//                .findViewById(R.id.custom_dialog_title_id);
//        title.setText(R.string.app_name);
//        TextView messageTXT = (TextView) innerAlertDialog
//                .findViewById(R.id.custom_dialog_message_id);
//        messageTXT.setText(activity.getString(R.string.logout_message));
//        innerAlertDialog.setCancelable(false);
//        Button ok_btn = (Button) innerAlertDialog
//                .findViewById(R.id.custom_dialog_okBtn_id);
//        ok_btn.setText(R.string.logout);
//
//        ok_btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//
//                if (closeActivity) {
//                    activity.finish();
//                }
//            }
//
//        });
//
//        Button cancel_btn = (Button) innerAlertDialog
//                .findViewById(R.id.custom_dialog_cancelBtn_id);
//        cancel_btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                innerAlertDialog.dismiss();
//
//
//            }
//        });
//
//        ok_btn.setAllCaps(true);
//        cancel_btn.setAllCaps(true);
//
//        innerAlertDialog.show();
//
//    }


    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
