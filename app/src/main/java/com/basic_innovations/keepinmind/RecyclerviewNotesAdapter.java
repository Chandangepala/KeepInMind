package com.basic_innovations.keepinmind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerviewNotesAdapter extends RecyclerView.Adapter<RecyclerviewNotesAdapter.ViewHoder> {

    Context context;
    ArrayList<NoteData> arrNoteData;
    Animation appearBox;

    public RecyclerviewNotesAdapter(Context context, ArrayList<NoteData> arrNoteData) {
        this.context = context;
        this.arrNoteData = arrNoteData;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(context).inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoder holder, final int position) {
        holder.txtTitle.setText(arrNoteData.get(position).title);
        holder.txtNote.setText(arrNoteData.get(position).Note);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              arrNoteData.remove(position);
              notifyDataSetChanged();
              Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        //setting animation on each recyclerView Item.
        appearBox = AnimationUtils.loadAnimation(context,R.anim.appear_box);
        holder.relLay.startAnimation(appearBox);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linLay_edit.setVisibility(View.VISIBLE);
                holder.linLay_edit.setAnimation(appearBox);
                holder.edtEditItemData.setText(arrNoteData.get(position).Note);
            }
        });
        holder.btnEditDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting new edited data to note
                String EditedNote = holder.edtEditItemData.getText().toString();
                holder.txtNote.setText(EditedNote);

                //setting edited note and title data within notedata element...
                NoteData noteData = new NoteData();
                noteData.title = arrNoteData.get(position).title;
                noteData.Note = EditedNote;
                arrNoteData.set(position, noteData);
                notifyItemChanged(position);

                holder.linLay_edit.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrNoteData.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView imgDelete, imgEdit;
        TextView txtTitle, txtNote;
        RelativeLayout relLay;
        LinearLayout linLay_edit;
        Button btnEditDone;
        EditText edtEditItemData;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.img_delete);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtNote = itemView.findViewById(R.id.txt_note);
            relLay = itemView.findViewById(R.id.relLay);
            imgEdit = itemView.findViewById(R.id.edit_item_data);
            linLay_edit = itemView.findViewById(R.id.linlay_item_edit);
            edtEditItemData = itemView.findViewById(R.id.edt_edit_item);
            btnEditDone = itemView.findViewById(R.id.btn_edit_done);
        }
    }
}
