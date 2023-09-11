package com.project.sample.dao;

import com.project.sample.dto.Notic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticDao {
    public int get_Notic_Count(Notic notic);
    public List<Notic> get_Notic_List(Notic notic);
}
