package org.catroid.catrobat.newui.recycleviewlist.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.ActionModeListener;
import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.ListItem;
import org.catroid.catrobat.newui.recycleviewlist.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.recycleviewlist.adapter.RecyclerViewAdapterDelegate;
import org.catroid.catrobat.newui.utils.Utils;

import java.util.List;

public abstract class BaseRecyclerListFragment<T> extends Fragment implements RecyclerViewAdapterDelegate<T> {

    public static final String TAG = BaseRecyclerListFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    public final String NAME = "";
    protected ActionMode mActionMode;
    protected RecyclerView mRecyclerView;
    protected MenuItem mEditButton;
    protected RecyclerViewAdapter<T> mRecyclerViewAdapter;
    protected ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);

            mEditButton = menu.findItem(R.id.btnEdit);

            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.btnEdit:
                    //ActionModeListener.renameItem(mRecyclerViewAdapter.getSelectedItems().get(0));
                    mRecyclerViewAdapter.clearSelection();
                    return true;
                case R.id.btnCopy:
                    //ActionModeListener.copyItems(mRecyclerViewAdapter.getSelectedItems());
                    mRecyclerViewAdapter.clearSelection();
                    return true;
                case R.id.btnDelete:
                    //ActionModeListener.deleteItems(mRecyclerViewAdapter.getSelectedItems());
                    mRecyclerViewAdapter.clearSelection();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mRecyclerViewAdapter.clearSelection();
            mActionMode = null;
        }
    };

    public static Fragment newInstance(int sectionNumber) {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_view, container, false);

        List<ListItem> items = Utils.getItemList();
        mRecyclerViewAdapter = createAdapter();
        mRecyclerViewAdapter.setDelegate(this);

        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        return mRecyclerView;
    }

    public abstract RecyclerViewAdapter<T> createAdapter();

    @Override
    public void onSelectionChanged(RecyclerViewAdapter<T> adapter) {
        List<T> selectedItems = adapter.getSelectedItems();

        if (selectedItems.isEmpty()) {
            if (mActionMode != null) {
                mActionMode.finish();
            }
        } else {
            if (mActionMode == null) {
                mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(mActionModeCallback);
            }

            boolean editButtonVisibility = selectedItems.size() <= 1;
            setEditButtonVisibility(editButtonVisibility);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        mEditButton = (MenuItem) v.findViewById(R.id.btnEdit);
    }

    private void setEditButtonVisibility(boolean visible) {

        if (mEditButton != null && mEditButton.isVisible() != visible) {
            mEditButton.setVisible(visible);
            getActivity().invalidateOptionsMenu();
        }
    }

    public void clearSelection() {
        mRecyclerViewAdapter.clearSelection();
    }
}
