package org.example.service;

import org.example.pojo.Dorms;
import org.example.pojo.DormsQueryParam;
import org.example.pojo.PageResult;

import java.util.List;

public interface DormService {
    void add(Dorms dorms);

    void update(Dorms dorms);

    void deleteById(Integer id);

    List<Dorms> list();

    void deleteBatch(List<Integer> ids);

    List<Dorms> listByDormId(Integer dormId);

    PageResult<Dorms> queryDorms(DormsQueryParam param);
}
