package com.worldunion.dylanapp.unit.pulltorefresh.adapter;

import android.content.Context;
import android.widget.TextView;

import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.adapter.GeneralAdapter;

import java.util.List;

/**
 * @author Dylan
 * @time 2017/3/28 16:39
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class PtrAdapter extends GeneralAdapter<String> {

    /**
     * 构造函数
     *
     * @param ctx  上下文
     * @param data 默认列表数据，可为null
     */
    public PtrAdapter(Context ctx, List<String> data) {
        super(ctx, R.layout.item_lv_ptr, data);
    }

    @Override
    public void convert(ViewHolder holder, String item, int position) {
        TextView tvText = (TextView) holder.getView(R.id.tvText);
        tvText.setText(item);
    }
}
