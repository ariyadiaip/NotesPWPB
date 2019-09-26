package id.sch.bdg.smkn4.pwpb.notespwpb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.UserViewHolder> {
    Context context;
    OnUserClickListener listener;
    List<Note> listNoteInfo;

    public RecyclerviewAdapter(Context context, List<Note> listNoteInfo, OnUserClickListener listener) {
        this.context = context;
        this.listNoteInfo = listNoteInfo;
        this.listener = listener;
    }

    public interface OnUserClickListener{
        void onUserClick(Note currentNote, String action);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        final Note currentNote = listNoteInfo.get(position);
        holder.tanggal.setText(currentNote.getTanggal());
        holder.judul.setText(currentNote.getJudul());
        holder.deskripsi.setText(currentNote.getDeskripsi());
        holder.group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice(currentNote);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNoteInfo.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal;
        TextView judul;
        TextView deskripsi;
        RelativeLayout group;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggal);
            judul = itemView.findViewById(R.id.judul);
            deskripsi = itemView.findViewById(R.id.deskripsi);
            group = itemView.findViewById(R.id.group);
        }
    }

    public void choice(final Note note) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Pilihan");
        CharSequence[] items = {"Edit", "Delete"};
        alertDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0 :
                        updateData(note);
                        break;
                    case 1 :
                        deleteData(note);
                        break;
                }
            }
        });
        alertDialog.show();
    }

    private void updateData(Note currentNote) {
        listener.onUserClick(currentNote,"Edit");
    }

    private void deleteData(Note currentNote) {
        listener.onUserClick(currentNote,"Delete");
    }
}