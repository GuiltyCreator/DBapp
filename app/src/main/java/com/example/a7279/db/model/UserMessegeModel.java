package com.example.a7279.db.model;

import com.example.a7279.db.callback.OnChangetAvatarListener;
import com.example.a7279.db.callback.OnGetMessegeSuccessListener;

/**
 * Created by a7279 on 2018/3/10.
 */

public interface UserMessegeModel {
    void getUserMessege(OnGetMessegeSuccessListener listener);
    void changeAvatar(String avatar,String token,OnChangetAvatarListener listener);
}
