package com.gerontechies.semonaid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.gerontechies.semonaid.Models.Budget.JobContentItem;
import com.gerontechies.semonaid.R;

import java.util.List;

import static com.gerontechies.semonaid.Activities.Income.Skills.SkillsQuizActivity.selectedCertfificationsList;
import static com.gerontechies.semonaid.Activities.Income.Skills.SkillsQuizActivity.selectedSkillsList;
/*Adapter for the skills and certifications list*/
public class SkillListAdapter extends RecyclerView.Adapter<SkillListAdapter.MyViewHolder> {

    Context mContext;
    List<JobContentItem> jobContentItem;
    public SkillListAdapter(Activity mContext, List<JobContentItem> jobContentItem) {
        this.mContext = mContext;
        this.jobContentItem = jobContentItem;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox skill_check;

        public MyViewHolder(View itemView) {
            super(itemView);

            skill_check = (CheckBox) itemView.findViewById(R.id.skill_check);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skills_input_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final JobContentItem skill = jobContentItem.get(position);

       holder.skill_check.setText("" +  skill.name);
       if(skill.type.equals("skills")){
           if(selectedSkillsList.contains(skill.name)){
               holder.skill_check.setChecked(true);
           }
       }

        if(skill.type.equals("certification")){
            if(selectedCertfificationsList.contains(skill.name)){
                holder.skill_check.setChecked(true);
            }
        }

        holder.skill_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(skill.type.equals("skills")){
                    if (isChecked) {
                        selectedSkillsList.add(skill.name);
                        Log.d("ERR", skill.name);
                    }else{
                        selectedSkillsList.remove(skill.name);

                    }
                } else if(skill.type.equals("certification")) {
                    if (isChecked) {
                        selectedCertfificationsList.add(skill.name);
                    }else{
                        selectedCertfificationsList.remove(skill.name);

                    }
                }


            }
        });
    }





    @Override
    public int getItemCount () {
        return jobContentItem.size();
    }
}
