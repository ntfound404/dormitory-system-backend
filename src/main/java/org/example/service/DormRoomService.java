package org.example.service;

import org.example.pojo.DormRoomQueryParam;
import org.example.pojo.DormRooms;
import org.example.pojo.PageResult;
import org.example.pojo.Students;

import java.util.List;

public interface DormRoomService {
    List<DormRooms> list();

    void add(DormRooms rooms);

    void deleteById(Integer id);

    void update(DormRooms rooms);

    void deleteBatch(List<Integer> ids);

    List<DormRooms> listByRoomId(Integer dormId);

    void updateCount(Integer id);

    DormRooms getRoomById(Integer id);

    void deleteCount(Integer id);

    List<Students> getStudentList(Integer id);

    List<DormRooms> listByDormId(Integer dormId);

    PageResult<DormRooms> queryDormRoom(DormRoomQueryParam param);
}
