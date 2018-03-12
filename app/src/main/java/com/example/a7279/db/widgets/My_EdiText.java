package com.example.a7279.db.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a7279.db.R;
import com.example.a7279.db.base.BaseApp;
import com.example.a7279.db.utils.EditTextClearTools;
import com.example.a7279.db.utils.LogUtil;

/**
 * Created by a7279 on 2018/2/14.
 */

public class My_EdiText extends LinearLayout {
    private EditText e1;
    private ImageView m1;
    private TextView t1;


    public My_EdiText(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(BaseApp.getContext()).inflate(R.layout.input_box_layout,this);
        init();
        TypedArray typedArray = BaseApp.getContext().obtainStyledAttributes(attrs, R.styleable.My_EdiTextView);
        if (typedArray != null) {
            e1.setHint(typedArray.getString(R.styleable.My_EdiTextView_hint));
            e1.setInputType(InputType.TYPE_CLASS_TEXT|typedArray.getInt(R.styleable.My_EdiTextView_InputType,0));
            t1.setText(typedArray.getString(R.styleable.My_EdiTextView_text));
            typedArray.recycle();
        }
    }
    private void init() {
        e1 = (EditText) findViewById(R.id.phonenumber);
        m1 = (ImageView) findViewById(R.id.del_phonenumber);
        t1=findViewById(R.id.textView1);
        // 添加监听器
        EditTextClearTools.addclerListener(e1, m1);
    }

    public String getText()
    {
        //LogUtil.d("TAG",e1.getText().toString());
        return e1.getText().toString();
    }

    public void clear()
    {
        e1.setText("");
    }
}