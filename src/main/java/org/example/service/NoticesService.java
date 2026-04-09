package org.example.service;


import org.example.pojo.Notices;
import org.example.pojo.NoticesQueryParam;
import org.example.pojo.PageResult;

import java.util.List;

public interface NoticesService {
    List<Notices> list();

    void add(Notices notices);

    void update(Notices notices);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    PageResult<Notices> queryNotices(NoticesQueryParam param);
}
