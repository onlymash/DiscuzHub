package com.kidozh.discuzhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kidozh.discuzhub.R;
import com.kidozh.discuzhub.entities.ForumInfo;
import com.kidozh.discuzhub.entities.bbsInformation;
import com.kidozh.discuzhub.entities.forumUserBriefInfo;
import com.kidozh.discuzhub.results.BBSIndexResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kidozh.discuzhub.utilities.networkUtils.getPreferredClient;

public class bbsPortalCategoryAdapter extends RecyclerView.Adapter<bbsPortalCategoryAdapter.bbsShowPortalViewHolder> {
    private final static String TAG = bbsPortalCategoryAdapter.class.getSimpleName();
    Context mContext;
    List<BBSIndexResult.ForumCategory> forumCategoryList;
    public String jsonString;
    bbsInformation bbsInfo;
    forumUserBriefInfo curUser;
    List<ForumInfo> allForumInfo;

    bbsPortalCategoryAdapter(Context context){
        this.mContext = context;
    }

    public bbsPortalCategoryAdapter(Context context, String jsonString, bbsInformation bbsInformation, forumUserBriefInfo userBriefInfo){
        this.mContext = context;
        this.jsonString = jsonString;
        this.bbsInfo = bbsInformation;
        this.curUser = userBriefInfo;
    }

    public void setForumCategoryList(List<BBSIndexResult.ForumCategory> forumCategoryList, List<ForumInfo> allForumInfo) {
        this.forumCategoryList = forumCategoryList;
        this.allForumInfo = allForumInfo;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public bbsShowPortalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_bbs_category;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new bbsShowPortalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bbsShowPortalViewHolder holder, int position) {
        BBSIndexResult.ForumCategory category = forumCategoryList.get(position);
        holder.mPortalCatagoryName.setText(category.name);
        if(category.forumIdList.size()>=4){
            holder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        }
        else {
            holder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        }

        bbsPortalCategoryForumAdapter adapter = new bbsPortalCategoryForumAdapter(mContext,jsonString,bbsInfo,curUser);
        holder.mRecyclerView.setAdapter(adapter);
        List<ForumInfo> forumInfoListInTheCategory = category.getForumListInTheCategory(allForumInfo);
        adapter.setForumInfoList(forumInfoListInTheCategory);



    }

    @Override
    public int getItemCount() {
        if(forumCategoryList == null){
            return 0;
        }
        else {
            return forumCategoryList.size();
        }

    }

    public class bbsShowPortalViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.portal_catagory_name)
        TextView mPortalCatagoryName;
        @BindView(R.id.portal_catagory_recyclerview)
        RecyclerView mRecyclerView;
        @BindView(R.id.portal_category_icon)
        ImageView mPortalCategoryIcon;

        public bbsShowPortalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
