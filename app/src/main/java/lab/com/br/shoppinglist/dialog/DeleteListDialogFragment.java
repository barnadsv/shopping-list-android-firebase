package lab.com.br.shoppinglist.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import lab.com.br.shoppinglist.R;
import lab.com.br.shoppinglist.services.ShoppingListService;

/**
 * Created by LEONARDO on 23/07/2017.
 */

public class DeleteListDialogFragment extends BaseDialog implements View.OnClickListener{

    public static final String EXTRA_SHOPPING_LIST_ID = "EXTRA_SHOPPING_LIST_ID";
    public static final String EXTRA_BOOLEAN = "EXTRA_BOOLEAN";

    private String mShoppingListId;
    private boolean mIsLongClicked;

    public static DeleteListDialogFragment newInstance(String shoppingListId, boolean isLongClicked) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_SHOPPING_LIST_ID, shoppingListId);
        arguments.putBoolean(EXTRA_BOOLEAN, isLongClicked);

        DeleteListDialogFragment dialogFragment = new DeleteListDialogFragment();
        dialogFragment.setArguments(arguments);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShoppingListId = getArguments().getString(EXTRA_SHOPPING_LIST_ID);
        mIsLongClicked = getArguments().getBoolean(EXTRA_BOOLEAN);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_delete_list, null))
                .setPositiveButton("Confirm", null)
                .setNegativeButton("Cancel", null)
                .setTitle("Delete Shopping List?")
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        if (mIsLongClicked) {
            dismiss();
            bus.post(new ShoppingListService.DeleteSoppingListRequest(userEmail, mShoppingListId));
        }
    }
}
