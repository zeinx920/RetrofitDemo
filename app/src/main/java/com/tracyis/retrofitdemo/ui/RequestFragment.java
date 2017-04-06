package com.tracyis.retrofitdemo.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tracyis.retrofitdemo.R;
import com.tracyis.retrofitdemo.network.MyRetrofit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestFragment extends Fragment {

    private static final String TAG = "RequestFragment";

    @InjectView(R.id.tv_content)
    TextView mTvContent;

    public RequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick({R.id.btn_request,
            R.id.btn_request_private,
            R.id.btn_login,
            R.id.btn_logout,})
    public void onClick(View view) {
        switch (view.getId()) {

            // 请求公共数据
            case R.id.btn_request:
                Toast.makeText(getActivity(), "开始请求!", Toast.LENGTH_SHORT).show();
                requestPublicData();
                break;

            // 请求私有数据
            case R.id.btn_request_private:
                Toast.makeText(getActivity(), "开始请求!", Toast.LENGTH_SHORT).show();
                requestPrivateData();
                break;

            // 登录
            case R.id.btn_login:
                login();
                break;

            // 注销
            case R.id.btn_logout:
                logout();
                break;

        }
    }


    private void logout() {
        Call<Object> userInfo = MyRetrofit.getInstance().getApi().logout();
        userInfo.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                mTvContent.setText(response.body() + "");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mTvContent.setText(t.getMessage());
            }
        });
    }

    private void login() {
        Call<Object> userInfo = MyRetrofit.getInstance().getApi().login();
        userInfo.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                mTvContent.setText(response.body() + "");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mTvContent.setText(t.getMessage());
            }
        });

    }

    private void requestPublicData() {
        Call<Object> userInfo = MyRetrofit.getInstance().getApi().getPublicData();
        userInfo.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                mTvContent.setText(response.body() + "");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mTvContent.setText(t.getMessage());
            }
        });
    }

    private void requestPrivateData() {
        Call<Object> userInfo = MyRetrofit.getInstance().getApi().getPrivateData();
        userInfo.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                mTvContent.setText(response.body() + "");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mTvContent.setText(t.getMessage());
            }
        });
    }
}
