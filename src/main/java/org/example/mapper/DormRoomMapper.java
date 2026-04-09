package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.DormRoomQueryParam;
import org.example.pojo.DormRooms;

import java.util.List;

@Mapper
public interface DormRoomMapper {

    @Select("select * from dorm_rooms")
    List<DormRooms> list();

    @Insert("insert into dorm_rooms(dorm_number, floor,current_count,capacity,dorm_id,created_time) values" +
            " (#{dormNumber}, #{floor}, #{currentCount}, #{capacity}, #{dormId},now())")
    void add(DormRooms rooms);

    @Delete("delete from dorm_rooms where id = #{id}")
    void deleteById(Integer id);

    @Update("update dorm_rooms set dorm_number = #{dormNumber}, floor = #{floor}, current_count = #{currentCount},"
            + " capacity = #{capacity}, dorm_id = #{dormId},updated_time = now() where id = #{id}")
    void update(DormRooms rooms);

    @Delete("<script>"
            + "DELETE FROM notices WHERE id IN "
            + "<foreach collection='ids' item='id' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    void deleteBatch(List<Integer> ids);

    @Select("SELECT * FROM dorm_rooms WHERE id = #{roomId}")
    List<DormRooms> listByRoomId(Integer dormId);

    @Update("update dorm_rooms set current_count = current_count + 1 where id = #{id}")
    void updateCount(Integer id);

    @Select("select * from dorm_rooms where id = #{id}")
    DormRooms getRoomById(Integer id);

    @Update("update dorm_rooms set current_count = current_count - 1 where id = #{id}")
    void deleteCount(Integer id);

    @Select("select * from dorm_rooms where dorm_id = (select id from dorms where id = #{id})")
    List<DormRooms> listByDormId(Integer dormId);

    List<DormRooms> queryByDormRoom(@Param("param") DormRoomQueryParam param);
}
