package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.DormRoomMapper;
import org.example.mapper.StudentMapper;
import org.example.pojo.DormRoomQueryParam;
import org.example.pojo.DormRooms;
import org.example.pojo.PageResult;
import org.example.pojo.Students;
import org.example.service.DormRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class DormRoomServiceImpl implements DormRoomService {
    @Override
    public PageResult<DormRooms> queryDormRoom(DormRoomQueryParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        Page<DormRooms> page = (Page<DormRooms>) dormRoomMapper.queryByDormRoom(param);
        return new PageResult<>(
                page.getTotal(),
                page.getPages(),
                page.getResult()
        );
    }

    @Override
    public List<DormRooms> listByDormId(Integer dormId) {
        return dormRoomMapper.listByDormId(dormId);
    }

    @Autowired
    private StudentMapper studentMapper;
    @Override
    public List<Students> getStudentList(Integer id) {
        return studentMapper.getStudentList(id);  // 调用 Mapper 获取学生列表
    }

    @Override
    public void deleteCount(Integer id) {
        dormRoomMapper.deleteCount(id);
    }

    @Override
    public DormRooms getRoomById(Integer id) {
        DormRooms room = dormRoomMapper.getRoomById(id);
        return room;
    }

    @Override
    public void updateCount(Integer id) {
        dormRoomMapper.updateCount(id);
    }

    @Override
    public List<DormRooms> listByRoomId(Integer dormId) {
        return dormRoomMapper.listByRoomId(dormId);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        dormRoomMapper.deleteBatch(ids);
    }

    @Override
    public void update(DormRooms rooms) {
        dormRoomMapper.update(rooms);
    }

    @Override
    public void deleteById(Integer id) {
        dormRoomMapper.deleteById(id);
    }

    @Override
    public void add(DormRooms rooms) {
        dormRoomMapper.add(rooms);
    }

    @Override
    public List<DormRooms> list() {
        return dormRoomMapper.list();
    }

    @Autowired
    private DormRoomMapper dormRoomMapper;
}
