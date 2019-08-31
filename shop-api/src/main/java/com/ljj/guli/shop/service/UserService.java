package com.ljj.guli.shop.service;


import com.ljj.guli.shop.bean.UmsMember;
import com.ljj.guli.shop.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
