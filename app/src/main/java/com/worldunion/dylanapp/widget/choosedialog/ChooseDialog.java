package com.worldunion.dylanapp.widget.choosedialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.worldunion.dylanapp.R;


/**
 * Created by cai on 2017-10-19. <br/>
 */
public class ChooseDialog extends Dialog {

    public interface OnEventListener {
        void onCheck(int index);
    }

    private String[] items;
    private int currItemIndex;
    private int visibleItems;
    private boolean cyclic;
    private OnEventListener listener;

    private ChooseDialog(Context context) {
        super(context, R.style.CommonDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        // 设置布局
        window.setContentView(R.layout.single_wheel_view_picker);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                         LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.dialog_anim);

        final WheelView wheelView = (WheelView)window.findViewById(R.id.wheel_view);
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(getContext(), items);
        wheelView.setViewAdapter(adapter);
        wheelView.setCyclic(cyclic);
        wheelView.setCurrentItem(currItemIndex);
        wheelView.setVisibleItems(visibleItems);
        wheelView.setShadowColor(0xcfffffff,0xcfffffff, 0xcfffffff);
        wheelView.setShadowItemCount(1);

        // 设置监听
        TextView ok = (TextView)window.findViewById(R.id.set);
        TextView cancel = (TextView)window.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int selectedItem = wheelView.getCurrentItem();
                    listener.onCheck(selectedItem);
                }
                cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        LinearLayout cancelLayout = (LinearLayout)window.findViewById(R.id.view_none);
        cancelLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                cancel();
                return false;
            }
        });
    }

    private void setItems(String[] items) {
        this.items = items;
    }

    private void setCurrItemIndex(int itemIndex) {
        this.currItemIndex = itemIndex;
    }

    private void setVisibleItems(int visibleItems) {
        this.visibleItems = visibleItems;
    }

    private void setCyclic(boolean cyclic) {
        this.cyclic = cyclic;
    }

    private void setOnEventListener(OnEventListener listener) {
        this.listener = listener;
    }

    public static class Builder {
        private Context context;
        private String[] items;
        private int currItemIndex;
        private int visibleItems;
        private boolean cyclic;
        private OnEventListener listener;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setItems(String[] items) {
            this.items = items;
            return this;
        }

        public Builder setCurrItemIndex(int itemIndex) {
            this.currItemIndex = itemIndex;
            return this;
        }

        public Builder setVisibleItems(int visibleItems) {
            this.visibleItems = visibleItems;
            return this;
        }

        public Builder setCyclic(boolean cyclic) {
            this.cyclic = cyclic;
            return this;
        }

        public Builder setOnEventListener(OnEventListener listener) {
            this.listener = listener;
            return this;
        }


        public ChooseDialog create() {
            ChooseDialog dialog = new ChooseDialog(context);
            dialog.setItems(items);
            dialog.setCurrItemIndex(currItemIndex);
            dialog.setVisibleItems(visibleItems);
            dialog.setOnEventListener(listener);
            return dialog;
        }
    }
}
