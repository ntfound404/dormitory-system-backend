package org.example.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.NoticesMapper;
import org.example.pojo.Notices;
import org.example.pojo.NoticesQueryParam;
import org.example.pojo.PageResult;
import org.example.service.NoticesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoticesSerivceImpl implements NoticesService {
    @Override
    public PageResult<Notices> queryNotices(NoticesQueryParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        Page<Notices> page = (Page<Notices>) noticesMapper.queryByNotices(param);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.getResult());
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        noticesMapper.deleteBatch(ids);
    }

    @Override
    public void deleteById(Integer id) {
        noticesMapper.deleteById(id);
    }

    @Override
    public void update(Notices notices) {
        noticesMapper.update(notices);
    }


    @Override
    public void add(Notices notices) {
        noticesMapper.add(notices);
    }

    @Override
    public List<Notices> list() {
        return noticesMapper.list();
    }

    @Autowired
    private NoticesMapper noticesMapper;

}
