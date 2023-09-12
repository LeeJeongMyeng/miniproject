package com.project.sample.service;

import com.project.sample.dto.Application_FM;
import com.project.sample.dto.FleaMarketDto2;
import com.project.sample.dto.FleamarketDto;
import com.project.sample.dto.Fleamarket_Files;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface FleamarketService {

    public int reg_FleaMarket(FleamarketDto fleamarketDto,String method);
    public int reg_FleaMarket_files(List<MultipartFile> files,int fno,String method);
    public FleaMarketDto2 get_FleaMarket_List(FleamarketDto fleamarketDto);
    public FleamarketDto get_FleaMarket(int fno);
    public List<Fleamarket_Files> get_FleaMarket_files(int fno);
    public int del_FleaMarket(int fno);
    public int application_FM(int fno,String userno);
    public int Check_Application(int fno, String userno);
    public Map<String,Object> get_application_FM(Application_FM applicationFm) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    public int upt_application_FM(Application_FM applicationFm);
    public int upt_apl_FM_ACount(Application_FM applicationFm);
    public FleaMarketDto2 get_My_FleaMarket(FleamarketDto fleamarketDto);
    public FleaMarketDto2 get_My_Application(FleamarketDto fleamarketDto);
}
