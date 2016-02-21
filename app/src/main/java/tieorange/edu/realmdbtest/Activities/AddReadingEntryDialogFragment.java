package tieorange.edu.realmdbtest.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import tieorange.edu.realmdbtest.R;

/**
 * Created by tieorange on 21/02/16.
 */
public class AddReadingEntryDialogFragment extends DialogFragment {
    public static final String BUNDLE_PAGES_COUNT = "pagesCount";
    private View mView;
    private NumberPicker mUiCurrentPage;

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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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

        builder.setView(mView)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO: add the reading entry to Realm
                        addReadingEntry();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddReadingEntryDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    private void addReadingEntry() {
        // Return input from NumberPicker to activity through the implemented listener
        AddReadingEntryDialogListener listener = (AddReadingEntryDialogListener) getActivity();
        listener.onFinishEntryDialog(mUiCurrentPage.getValue());
    }
}
