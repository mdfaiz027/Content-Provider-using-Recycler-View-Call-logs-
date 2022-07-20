package com.example.practicecontentproviderusingrecyclerview2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CallLogsAdapter extends RecyclerView.Adapter<CallLogsAdapter.CallHolder> {

    Context context;
    List<CallLogsModel> callLogsModelList = new ArrayList<>();

    public CallLogsAdapter(Context context, List<CallLogsModel> callLogsModelList) {
        this.context = context;
        this.callLogsModelList = callLogsModelList;
    }

    @NonNull
    @Override
    public CallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_custom_layout,parent, false);
        return new CallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallHolder holder, int position) {

        holder.name.setText(callLogsModelList.get(position).getName());
        holder.number.setText(callLogsModelList.get(position).getNumber());

        //setting date and time format
        String dateFormat = callLogsModelList.get(position).getDate();
        long dateFormatting = Long.parseLong(dateFormat);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy" + "  |  " + "HH:mm:ss");
        String dateAndTime = format.format(dateFormatting);
        holder.date.setText(""+dateAndTime);

        //setting calltype
        int callType = callLogsModelList.get(position).getType();

        if(callType==1){
            holder.type.setImageResource(R.drawable.incoming);
        }
        else if(callType==2){
            holder.type.setImageResource(R.drawable.outgoing);
        }
        else if(callType==3){
            holder.type.setImageResource(R.drawable.missed);
        }
        else if(callType==4){
            holder.type.setImageResource(R.drawable.voice);
        }
        else if(callType==5){
            holder.type.setImageResource(R.drawable.rejected);
        }
        else if(callType==6){
            holder.type.setImageResource(R.drawable.blocked);
        }
        else if(callType==7){
            holder.type.setImageResource(R.drawable.ic_launcher_background);
        }

        //setting duration

        String callDuration = callLogsModelList.get(position).duration;
        int seconds = Integer.parseInt(callDuration);

        int p1 = seconds % 60;
        int p2 = seconds / 60;
        int p3 = p2 % 60;
        p2 = p2 / 60;

        //holder.duration.setText(p2 + " hrs : " + p3 + " mins: " + p1 + " secs"); //for hrs use p2

        if(p3>1){
            holder.duration.setText(p3 + " mins " + p1 + " secs");
        }
        else if(p3 == 1){
            holder.duration.setText(p3 + " mins " + p1 + " secs");
        }
        else if(p3 == 0 && p1 == 0){
            holder.duration.setText(p1 + " secs");
        }
        else if(p3 == 0){
            holder.duration.setText(p1 + " secs");
        }
        else{
            holder.duration.setText(p3 + " mins " + p1 + " secs");
        }
    }

    @Override
    public int getItemCount() {
        return callLogsModelList.size();
    }

    public class CallHolder extends RecyclerView.ViewHolder {

        TextView name, number, date, duration;
        ImageView type;

        public CallHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            date = itemView.findViewById(R.id.date);
            type = itemView.findViewById(R.id.type);
            duration = itemView.findViewById(R.id.duration);

        }
    }
}
