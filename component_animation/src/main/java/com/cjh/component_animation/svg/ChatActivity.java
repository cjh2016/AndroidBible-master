package com.cjh.component_animation.svg;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.cjh.component_animation.R;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.id_rv_content)
    RecyclerView mIdRvContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a12_swipe_refresh);
        ButterKnife.bind(this);

        mIdRvContent.setAdapter(new ChatRvAdapter(BeanFactory.getMsgBeans(60)));
        mIdRvContent.setLayoutManager(new LinearLayoutManager(this));

    }

}
