package tieorange.edu.realmdbtest.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import tieorange.edu.realmdbtest.Activities.ChartsActivity;
import tieorange.edu.realmdbtest.Helpers.POJOHelper;
import tieorange.edu.realmdbtest.Helpers.RealmHelper;
import tieorange.edu.realmdbtest.R;

/**
 * Created by tieorange on 21/02/16.
 */
public class AddReadingEntryDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String BUNDLE_PAGES_COUNT = "pagesCount";
    private View mView;

    private Button mUiBtnOtherDate;
    private NumberPicker mUiCurrentPage;
    private ChartsActivity mActivity;
    private Realm mRealm;
    private TextView mUiTvDate;
    private Date mSelectedDate = new Date();

    public AddReadingEntryDialogFragment() {
        // Empty constructor is required for DialogFragment
    }


    private void showDatePickerDialog() {
        // Get current date data
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getActivity(), this, year, month, day);

        // Set limit to last reading entry date
        final Date dateLastReadingEntry = RealmHelper.getLastReadingEntry(mRealm).getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateLastReadingEntry);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTime().getTime()); // TODO: minimal date

        datePickerDialog.show();
    }

    // Defines the listener with a method passing back data result
    public interface AddReadingEntryDialogListener {
        void onFinishEntryDialog(int currentPage, Date date);
    }

    public static AddReadingEntryDialogFragment newInstance(int pagesCount) {
        AddReadingEntryDialogFragment dialogFragment = new AddReadingEntryDialogFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_PAGES_COUNT, pagesCount);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onStop() {
        super.onStop();
        mRealm.close();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mActivity = (ChartsActivity) getActivity();
        mRealm = Realm.getDefaultInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.what_s_current_page);

        // Let's get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        mView = inflater.inflate(R.layout.dialog_add_reading_entry, null);
        mUiTvDate = (TextView) mView.findViewById(R.id.dialog_entry_date);

        mUiBtnOtherDate = (Button) mView.findViewById(R.id.dialog_entry_other_date);
        setupNumberPicker();

        builder.setView(mView)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO: add the reading entry to Realm
                        addReadingEntry(mUiCurrentPage.getValue());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddReadingEntryDialogFragment.this.getDialog().cancel();
                    }
                });

        mUiBtnOtherDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mView, "otherDateClicked", Snackbar.LENGTH_SHORT).show();
//                mActivity.showDatePickerDialog();
                showDatePickerDialog();
            }
        });

        return builder.create();
    }

    private void setupNumberPicker() {
        // setup NumberPicker
        mUiCurrentPage = (NumberPicker) mView.findViewById(R.id.dialog_entry_current_page);
        int pagesCount = getArguments().getInt(BUNDLE_PAGES_COUNT);
        mUiCurrentPage.setMaxValue(pagesCount);
        int lastCurrentPage = 0;
        try {
            lastCurrentPage = RealmHelper.getLastReadingEntry(mRealm).getCurrentPage(); // get last current page user entered
        } catch (Exception ex) {
        }
        mUiCurrentPage.setValue(lastCurrentPage);
        mUiCurrentPage.setMinValue(lastCurrentPage); // Can't read back
    }

    private void addReadingEntry(int page) {
        // Return input from NumberPicker to activity through the implemented listener
        AddReadingEntryDialogListener listener = (AddReadingEntryDialogListener) getActivity();

        // TODO: add validation ( value has to be > lastCurrentPage )

        listener.onFinishEntryDialog(page, mSelectedDate);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        String dateFormattedString = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
        mSelectedDate = POJOHelper.getDateFromDatePicker(view);
        String dateFormattedString = POJOHelper.getDateDayMonthYearString(mSelectedDate);

        mUiTvDate.setText(dateFormattedString);
    }
}
