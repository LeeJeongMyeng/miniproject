package com.project.sample.dao;


import com.project.sample.dto.FleamarketDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FleamarketDao {
    public int reg_FleaMarket(FleamarketDto fleamarketDto);
    public int FleaMarket_get_fno_max();
    public void reg_FleaMarket_file(FleamarketDto fleamarketDto);

}
