package tieorange.edu.realmdbtest.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import io.realm.Realm;
import tieorange.edu.realmdbtest.Activities.ChartsActivity;
import tieorange.edu.realmdbtest.DataHelpers.RealmHelper;
import tieorange.edu.realmdbtest.R;

/**
 * Created by tieorange on 21/02/16.
 */
public class AddReadingEntryDialogFragment extends DialogFragment {
    public static final String BUNDLE_PAGES_COUNT = "pagesCount";
    private View mView;
    private NumberPicker mUiCurrentPage;
    private ChartsActivity mActivity;
    private Realm mRealm;

    public AddReadingEntryDialogFragment() {
        // Empty constructor is required for DialogFragment
    }

    // Defines the listener with a method passing back data result
    public interface AddReadingEntryDialogListener {
        void onFinishEntryDialog(int currentPage);
    }

    public static AddReadingEntryDialogFragment newInstance(int pagesCount) {
        AddReadingEntryDialogFragment dialogFragment = new AddReadingEntryDialogFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_PAGES_COUNT, pagesCount);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
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

        // setup NumberPicker
        mUiCurrentPage = (NumberPicker) mView.findViewById(R.id.dialog_entry_current_page);
        int pagesCount = getArguments().getInt(BUNDLE_PAGES_COUNT);
        mUiCurrentPage.setMaxValue(pagesCount);
        int lastCurrentPage = 0;
        try {
            lastCurrentPage = RealmHelper.getLastReadingEntry(mRealm).getCurrentPage(); // TODO
        } catch (Exception ex) {

        }
        mUiCurrentPage.setValue(lastCurrentPage);

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

        return builder.create();
    }

    private void addReadingEntry(int value) {
        // Return input from NumberPicker to activity through the implemented listener
        AddReadingEntryDialogListener listener = (AddReadingEntryDialogListener) getActivity();
        listener.onFinishEntryDialog(value);
    }
}
