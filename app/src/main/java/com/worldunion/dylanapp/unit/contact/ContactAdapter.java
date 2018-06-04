package com.worldunion.dylanapp.unit.contact;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.adapter.GeneralAdapter;

import java.util.List;

/**
 * Created by Dylan on 2017/10/10.
 * 联系人adapter
 */
public class ContactAdapter extends GeneralAdapter<ContactBean> {

    /**
     * @param ctx  上下文
     * @param data 默认列表数据，可为null
     */
    public ContactAdapter(Context ctx, List<ContactBean> data) {
        super(ctx, R.layout.item_lv_contact, data);
    }

    @Override
    public void convert(ViewHolder holder, ContactBean item, int position) {
        TextView cityTitle = (TextView) holder.getView(R.id.city_item_title);
        TextView cityName = (TextView) holder.getView(R.id.city_item_name);
        View line = holder.getView(R.id.city_item_grey_line);

        cityName.setText(item.getName());

        String sortLetters = item.getFirstLetter();
        //根据position获取分类的首字母的Char ascii值
        int section = sortLetters.charAt(0);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            cityTitle.setVisibility(View.VISIBLE);
            cityTitle.setText(sortLetters);
        } else {
            cityTitle.setVisibility(View.GONE);
        }

        //最后一个 隐藏横线
        if (position == getLastPositionForSection(section)) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = getData().get(i).getFirstLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其最后一次出现该首字母的位置
     */
    public int getLastPositionForSection(int section) {
        for (int i = getCount() - 1; i >= 0; i--) {
            String sortStr = getData().get(i).getFirstLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

}
