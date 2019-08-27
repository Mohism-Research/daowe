package edu.buu.daowe.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.buu.daowe.R;
import edu.buu.daowe.defui.SelectableRoundedImageView;

/**
 * Created by elimy on 2016-12-26.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CardViewHolder> {
    private Context context;
    Map piliangdata;
    private ArrayList<CardData> datalist;
    View view;

    public RecyclerViewAdapter() {


    }



    /*
     * 带参构造函数，传入上下文和需要绑定的数据集合
     * */
    public RecyclerViewAdapter(Context cx, ArrayList datalist) {
        this.context = cx;
        this.datalist = datalist;
    }


    /*
     *创建ViewHolder，持有布局映射
     * */
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //通过布局加载器获取到CardView布局view
        view = LayoutInflater.from(context).inflate(R.layout.fragment_cardview_content, parent, false);
        //通过获取到的布局view实例化一个自己实现的CardViewHolder
        CardViewHolder cardViewHolder = new CardViewHolder(view);
        //返回一个已绑定布局的viewHolder，避免重复findViewById()


        return cardViewHolder;
    }


    /*
     *onBindViewHolder方法做药作用就是将数据集绑定到布局view，以及添加一些事件点击监听
     * */
    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.CardViewHolder holder, final int position) {

        final int pos = position;

        //将view中的view和数据集绑定
        //  holder.cardImage.setImageResource(datalist.get(position).ge);


        holder.tvcoursedate.setText(datalist.get(position).getDate());
        holder.tvcoursestatus.setText(datalist.get(position).getCoursestatus() + "");
        if (datalist.get(position).getCoursestatus().equals("进行中")) {
            holder.cardView.setBackgroundColor(Color.argb(12, 50, 160, 0));

            holder.cardImage.setImageResource(R.mipmap.form_checkbox_checked);

        } else if (datalist.get(position).getCoursestatus().equals("已结束")) {
            holder.cardView.setBackgroundColor(Color.argb(12, 160, 50, 0));
            holder.cardImage.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.cardImage.setImageResource(R.mipmap.icon_error);
        } else {
            holder.cardView.setBackgroundColor(Color.argb(12, 160, 160, 0));
            holder.cardImage.setImageResource(R.mipmap.icon_wait);
        }

        holder.tvcoursenum.setText(datalist.get(position).getCoursenum());
        holder.tvcoursename.setText(datalist.get(position).getCoursename());
        holder.tvcoursetime.setText(datalist.get(position).getCoursetime());
        holder.tvcourseposition.setText(datalist.get(position).getCourseposition());
        holder.tvcourseteacher.setText(datalist.get(position).getTeachername());

        //给整个cardview添加点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, position);
                //  Toast.makeText(context, "You click card" + pos, Toast.LENGTH_SHORT).show();
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (holder.piliang.isChecked() == true) {
                    holder.piliang.setVisibility(View.INVISIBLE);
                    holder.piliang.setChecked(false);
                    piliangdata.put(position + "", "false");
                } else {
                    holder.piliang.setVisibility(View.VISIBLE);
                    holder.piliang.setChecked(true);
                    piliangdata.put(position + "", "true");
                }


                return true;
            }
        });


//        //设置不喜欢的按钮点击监听事件
//        holder.disLikeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "你为啥不喜欢我呢？", Toast.LENGTH_SHORT).show();
//            }
//        });
//        //设置喜欢的按钮点击监听事件
//        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "我就知道你喜欢我！", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public Map getPiliangdata() {
        return piliangdata;
    }

    public void setPiliangdata(Map piliangdata) {
        this.piliangdata = piliangdata;
    }

    @SuppressLint("RestrictedApi")
    private void showPopupMenu(View view, final int positioninner) {


        // 这里的view代表popupMenu需要依附的view
        PopupMenu popupMenu = new PopupMenu(context, view);

        // 获取布局文件
        popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
        try {
            Field field = popupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper helper = (MenuPopupHelper) field.get(popupMenu);

            helper.setForceShowIcon(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        popupMenu.show();
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 控件每一个item的点击事件
                Toast.makeText(context, "第" + positioninner + "个", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // 控件消失时的事件
            }
        });

    }

    /*
     * 返回recyclerview数据项的个数
     * */
    @Override
    public int getItemCount() {
        return datalist.size();
    }


    /*
     * 自定义实现viewholder类
     * */
    class CardViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        CheckBox piliang;
        SelectableRoundedImageView cardImage;
        TextView tvcoursenum, tvcoursedate, tvcoursetime, tvcourseposition, tvcourseteacher, tvcoursename, tvcoursestatus;
        //  Button likeBtn, disLikeBtn;


        public CardViewHolder(View itemView) {
            super(itemView);
            piliangdata = new HashMap();
            piliang = itemView.findViewById(R.id.checkpiliang);
            cardView = itemView.findViewById(R.id.cv1);
            cardImage = itemView.findViewById(R.id.img_card_status);
            tvcoursedate = itemView.findViewById(R.id.tv_card_date);
            tvcoursestatus = itemView.findViewById(R.id.tv_card_coursestatus);
            tvcoursenum = itemView.findViewById(R.id.tv_card_course_num);
            tvcoursename = itemView.findViewById(R.id.tv_card_course_title);
            tvcourseposition = itemView.findViewById(R.id.tv_card_course_position);
            tvcourseteacher = itemView.findViewById(R.id.tv_card_course_teacher);
            tvcoursetime = itemView.findViewById(R.id.tv_card_course_time);
        }
    }
}