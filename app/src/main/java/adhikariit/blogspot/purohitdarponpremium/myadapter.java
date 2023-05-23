package adhikariit.blogspot.purohitdarponpremium;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myviewholdr> {


    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholdr holder, int position, @NonNull model model) {

        holder.fileName.setText(model.getFilename());


        holder.fileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.fileIcon.getContext(), PdfViewer.class);
                intent.putExtra("filename", model.getFilename());
                intent.putExtra("fileurl", model.getFileurl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.fileIcon.getContext().startActivity(intent);
            }
        });

        holder.fileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.fileName.getContext(), PdfViewer.class);
                intent.putExtra("filename", model.getFilename());
                intent.putExtra("fileurl", model.getFileurl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.fileName.getContext().startActivity(intent);


            }
        });


    }

    @NonNull
    @Override
    public myviewholdr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_disign, parent, false);
        return new myviewholdr(view);
    }

    public class myviewholdr extends RecyclerView.ViewHolder {
        ImageView fileIcon;
        TextView fileName;

        public myviewholdr(@NonNull View itemView) {
            super(itemView);

            fileIcon = itemView.findViewById(R.id.fileIconId);


            fileName = itemView.findViewById(R.id.fileNametextViewId);

        }
    }
}
