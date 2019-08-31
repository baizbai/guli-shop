package com.ljj.guli.shop.user.mapper;


import com.ljj.guli.shop.bean.UmsMember;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<UmsMember>{

    List<UmsMember> selectAllUser();

}
