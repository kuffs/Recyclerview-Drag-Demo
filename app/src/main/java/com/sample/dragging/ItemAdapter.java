package com.sample.dragging;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;

import java.util.List;

public class ItemAdapter
        extends RecyclerView.Adapter<ItemAdapter.VH>
        implements DraggableItemAdapter<ItemAdapter.VH> {

    private List<ListItem> _items;
    private DatabaseHelper _helper;

    public ItemAdapter(List<ListItem> items, DatabaseHelper helper) {
        setHasStableIds(true);
        _items = items;
        _helper=helper;
    }


    //region [ ViewHolder ]

    public static class VH extends AbstractDraggableItemViewHolder {
        public FrameLayout mContainer;
        public View mDragHandle;
        public TextView mTextView;

        public VH(View v) {
            super(v);
            mContainer = v.findViewById(R.id.container);
            mDragHandle = v.findViewById(R.id.drag_handle);
            mTextView = v.findViewById(R.id.txtDescription);
        }
    }

    //endregion

    //region [ Adapter ]

    private interface Draggable extends DraggableItemConstants {
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_layout, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final ListItem item = _items.get(position);

        holder.mTextView.setText(item.getDescription());


        final int dragState = holder.getDragStateFlags();

        if (((dragState & Draggable.STATE_FLAG_IS_UPDATED) != 0)) {
            int bgResId;

            if ((dragState & Draggable.STATE_FLAG_IS_ACTIVE) != 0) {
                bgResId = R.drawable.bg_item_dragging_active_state;

                // need to clear drawable state here to get correct appearance of the dragging item.
                DrawableUtils.clearState(holder.mContainer.getForeground());
            } else if ((dragState & Draggable.STATE_FLAG_DRAGGING) != 0) {
                bgResId = R.drawable.bg_item_dragging_state;
            } else {
                bgResId = R.drawable.bg_item_normal_state;
            }

            holder.mContainer.setBackgroundResource(bgResId);
        }
    }

    @Override
    public int getItemCount() {
        return _items.size();
    }

//endregion

    //region [ Drag Support ]

    @Override
    public long getItemId(int position) {
        return _items.get(position).getId();
    }

    @Override
    public boolean onCheckCanStartDrag(VH holder, int position, int x, int y) {

        View itemView = holder.itemView;
        View dragHandle = holder.mDragHandle;

        int handleWidth = dragHandle.getWidth();
        int handleHeight = dragHandle.getHeight();
        int handleLeft = dragHandle.getLeft();
        int handleTop = dragHandle.getTop();

        return (x >= handleLeft) && (x < handleLeft + handleWidth) &&
                (y >= handleTop) && (y < handleTop + handleHeight);

    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(VH holder, int position) {
        return null;
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }

        ListItem item = _items.remove(fromPosition);
        _items.add(toPosition, item);

        notifyItemChanged(toPosition);

        resetSortIndexes();
    }

    private void resetSortIndexes() {
        int index = 1;
        for (ListItem s : _items) {
            s.setSort(index);
            s.save(_helper);
            index++;
        }
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        return false;
    }

    @Override
    public void onItemDragStarted(int position) {

    }

    @Override
    public void onItemDragFinished(int fromPosition, int toPosition, boolean result) {

    }

    //endregion



}

