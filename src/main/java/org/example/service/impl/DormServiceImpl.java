package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.DormMapper;
import org.example.pojo.Dorms;
import org.example.pojo.DormsQueryParam;
import org.example.pojo.PageResult;
import org.example.service.DormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormServiceImpl implements DormService {
    @Override
    public PageResult<Dorms> queryDorms(DormsQueryParam param) {
        PageHelper.startPage(param.getPageNum(),param.getPageSize());
        Page<Dorms> page = (Page<Dorms>) dormMapper.queryDorms(param);
        return new PageResult<>(page.getTotal(),
                page.getPages(),
                page.getResult());
    }

    @Override
    public List<Dorms> listByDormId(Integer dormId) {
        return dormMapper.listByDormId(dormId);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        dormMapper.deleteBatch(ids);
    }

    @Override
    public List<Dorms> list() {
        return dormMapper.list();
    }

    @Override
    public void deleteById(Integer id) {
        dormMapper.deleteById(id);
    }

    @Override
    public void update(Dorms dorms) {
        dormMapper.update(dorms);
    }

    @Override
    public void add(Dorms dorms) {
        dormMapper.add(dorms);
    }

    @Autowired
    private DormMapper dormMapper;
}
