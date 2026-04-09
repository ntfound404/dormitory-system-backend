package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Dorms;
import org.example.pojo.DormsQueryParam;

import java.util.List;

@Mapper
public interface DormMapper {
    @Insert("insert into dorms(building,gender, created_time, updated_time) VALUES" +
            " ( #{building}, #{gender}, now(), now())")
    void add(Dorms dorms);

    @Update("UPDATE dorms SET building = #{building},gender = #{gender}, updated_time = now() WHERE id = #{id}")
    void update(Dorms dorms);

    @Delete("DELETE FROM dorms WHERE id = #{id}")
    void deleteById(Integer id);

    @Select("SELECT * FROM dorms")
    List<Dorms> list();

    @Delete("<script>"
            + "DELETE FROM dorms WHERE id IN "
            + "<foreach collection='ids' item='id' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    void deleteBatch(List<Integer> ids);

    @Select("SELECT * FROM dorms WHERE id = #{dormId}")
    List<Dorms> listByDormId(Integer dormId);

    List<Dorms> queryDorms(@Param("param") DormsQueryParam param);
}
