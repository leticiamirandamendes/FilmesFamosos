package com.example.android.filmesfamosos;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.filmesfamosos.data.TaskContract;

public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.TaskViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public CustomCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.favoritos, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        int idIndex = mCursor.getColumnIndex(TaskContract.TaskEntry._ID);
        int idMovie = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_ID);
        int nameIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME);
        int descriptionIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION);

        mCursor.moveToPosition(position);

        final String id = mCursor.getString(idIndex);
        String description = mCursor.getString(idMovie);
        String name = mCursor.getString(nameIndex);
        String sinopse = mCursor.getString(descriptionIndex);

        holder.itemView.setTag(id);
        holder.id.setText(description);
        holder.name.setText(name);
        holder.description.setText(sinopse);


    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        if (mCursor == c) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = c;

        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        TextView description;

        public TaskViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.tv_fav_id);
            name = itemView.findViewById(R.id.tv_fav_name);
            description = itemView.findViewById(R.id.tv_fav_description);
        }
    }
}