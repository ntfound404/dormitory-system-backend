package org.example.mapper;


import com.mysql.cj.protocol.x.Notice;
import org.apache.ibatis.annotations.*;
import org.example.pojo.Notices;
import org.example.pojo.NoticesQueryParam;

import java.util.List;

@Mapper
public interface NoticesMapper {

    @Select("select * from notices")
    List<Notices> list();

    @Insert("insert into notices(title,content,author,created_time) values (#{title},#{content},#{author},now())")
    void add(Notices notices);


    @Update("update notices set title=#{title},content=#{content},author=#{author},created_time =now() where id = #{id}")
    void update(Notices notices);

    @Delete("delete from notices where id = #{id}")
    void deleteById(Integer id);

    @Delete("<script>"
            + "DELETE FROM notices WHERE id IN "
            + "<foreach collection='ids' item='id' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    void deleteBatch(@Param("ids") List<Integer> ids);


    List<Notices> queryByNotices(@Param("param") NoticesQueryParam param);
}
