package com.project.sample.mapper;

import com.project.sample.dto.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user3")
    public List<UserDTO> findAll();

    @Update("update user3 set name=#{name},pwd=#{pwd},gender=#{gender} where no = #{no}")
    public int editById(UserDTO userDTO);

    @Update("update user3 set del= ture where no =#{no}")
    public int delete(int no);
    @Insert("insert into user3 (name,email,pwd,gender) value (#{name},#{email},#{pwd},#{gender})")
    public int save(UserDTO userDTO);
}
