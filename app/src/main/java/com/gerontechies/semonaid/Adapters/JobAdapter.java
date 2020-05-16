package com.gerontechies.semonaid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gerontechies.semonaid.Activities.Income.Skills.SkillDetailItemActivity;
import com.gerontechies.semonaid.Models.Budget.JobItem;
import com.gerontechies.semonaid.R;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder> {

    Context mContext;
    List<JobItem> jobItems;



    public JobAdapter(Activity mContext, List<JobItem> jobItems) {
        this.mContext = mContext;
        this.jobItems = jobItems;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView job_title;
       Button job_btn;
       CardView job;

        public MyViewHolder(View itemView) {
            super(itemView);

            job_title = (TextView) itemView.findViewById(R.id.job_title_txt);
            job_btn = (Button) itemView.findViewById(R.id.job_btn);
            job = (CardView) itemView.findViewById(R.id.job_card);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final JobItem job = jobItems.get(position);
        final int jobId = job.id;
        //need to get the skills and certification to check if the current job @pos has those
        //if it has those items, only then display;
        holder.job_title.setText("" +  job.name);
        holder.job_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, SkillDetailItemActivity.class);
                intent.putExtra("id", String.valueOf(jobId));
                intent.putExtra("job_name", String.valueOf(job.name));
                intent.putExtra("job_skills", String.valueOf(job.skills));
                intent.putExtra("job_desc", String.valueOf(job.desc));
                intent.putExtra("job_certification", String.valueOf(job.certifications));

                mContext.startActivity(intent);
            }
        });
//            if(!selectedSkillsList.isEmpty()){
//                try {
//                    JSONArray skills = new JSONArray(job.getSkills());
//
//                    for (int j = 0; j < skills.length(); j++) {
//
//                        JSONObject object1 = skills.getJSONObject(j);
//                        String skillName = object1.getString("name");
//                        Log.d("ERR", "------" + skillName);
//
//                        if(selectedSkillsList.contains(skillName)){
//
//                            Log.d("ERR", "It matched" + job.name);
//
//                        } else{
//                          //  Log.d("ERR", "no match" + job.name);
//                            holder.job.setVisibility(View.GONE);
//                        }
//
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            } else{
//                holder.job_title.setText("" +  job.name);
//                holder.job_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Intent intent = new Intent(mContext, SkillDetailItemActivity.class);
//                        intent.putExtra("id", String.valueOf(jobId));
//                        intent.putExtra("job_name", String.valueOf(job.name));
//                        intent.putExtra("job_skills", String.valueOf(job.skills));
//                        intent.putExtra("job_desc", String.valueOf(job.desc));
//                        intent.putExtra("job_certification", String.valueOf(job.certifications));
//
//                        mContext.startActivity(intent);
//                    }
//                });
//            }

    }


    @Override
    public int getItemCount () {
        return jobItems.size();
    }
}
