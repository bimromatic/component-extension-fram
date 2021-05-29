package com.bimromatic.component.moudle_reader.act;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bimromatic.component.lib_base.act.BaseActivity;
import com.bimromatic.component.moudle_reader.R;
import com.bimromatic.component.common.router.modules.reader.RouterHub;
import com.bimromatic.component.moudle_reader.databinding.ActivityReaderHomeBinding;

@Route(path = RouterHub.READER_HOMEACTIVITY)
public class ReaderHomeActivity extends BaseActivity<ActivityReaderHomeBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reader_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}