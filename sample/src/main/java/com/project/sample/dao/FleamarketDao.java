package com.project.sample.dao;


import com.project.sample.dto.Application_FM;
import com.project.sample.dto.FleaMarketDto2;
import com.project.sample.dto.FleamarketDto;
import com.project.sample.dto.Fleamarket_Files;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FleamarketDao {
    public int reg_FleaMarket(FleamarketDto fleamarketDto);
    public int FleaMarket_get_fno_max();
    public void reg_FleaMarket_file(FleamarketDto fleamarketDto);
    public List<FleamarketDto> get_FleaMarket_List(FleamarketDto fleamarketDto);
    public int get_FleaMarket_totCnt(FleamarketDto fleamarketDto);
    public FleamarketDto get_FleaMarket(int fno);
    public List<Fleamarket_Files> get_FleaMarket_files(int fno);
    public int del_FleaMarket(int fno);
    public void del_FleaMarket_files(int fno);
    public int upt_FleaMarket(FleamarketDto fleamarketDto);
    public int application_FM(Map<String,Object> map);
    public int Check_Application(Map<String,Object> map);
    public void del_Application_FM(int fno);
    public List<Application_FM> get_application_FM(Application_FM applicationFm);
    public int upt_application_FM(Application_FM applicationFm);
    public int get_Application_count(int fno);
    public int upt_apl_FM_Count(Application_FM applicationFm);
    public int get_My_FleaMarket_Count(FleamarketDto fleamarketDto);
    public List<FleamarketDto> get_My_FleaMarket(FleamarketDto fleamarketDto);
    public int get_My_Application_Count(FleamarketDto fleamarketDto);
    public List<FleamarketDto> get_My_Application(FleamarketDto fleamarketDto);
}

