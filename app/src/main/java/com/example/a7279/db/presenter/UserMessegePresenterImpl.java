package com.example.a7279.db.presenter;

import com.example.a7279.db.callback.OnChangetAvatarListener;
import com.example.a7279.db.callback.OnGetMessegeSuccessListener;
import com.example.a7279.db.model.UserMessegeModel;
import com.example.a7279.db.model.UserMessegeModelImpl;
import com.example.a7279.db.view.UserMessegeActivityView;

/**
 * Created by a7279 on 2018/3/10.
 */

public class UserMessegePresenterImpl implements UserMessegePresenter,OnGetMessegeSuccessListener,OnChangetAvatarListener {
    private UserMessegeModel model;
    private UserMessegeActivityView view;

    public UserMessegePresenterImpl(UserMessegeActivityView userMessegeActivityView){
        this.view=userMessegeActivityView;
        this.model=new UserMessegeModelImpl();

    }

    @Override
    public void getUermessege() {
        model.getUserMessege(this);
    }

    @Override
    public void onDestroy() {
        this.view=null;
    }

    @Override
    public void changeAvatar(String avatar,String token) {
        model.changeAvatar(avatar,token,this);
    }

    @Override
    public void onSuccessGet(int id, String name, String avatar, String token,String avatar_id) {
        view.getSuccess(id,name,avatar,token,avatar_id);
    }


    @Override
    public void onNetworkError() {
        view.show_Networkerror();
    }

    @Override
    public void onSuccess() {
        view.changeAvatarSuccess();
    }
}
