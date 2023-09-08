package com.project.sample.service;

import com.project.sample.dto.FleaMarketDto2;
import com.project.sample.dto.FleamarketDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FleamarketService {

    public int reg_FleaMarket(FleamarketDto fleamarketDto);
    public int reg_FleaMarket_files(List<MultipartFile> files);
    public FleaMarketDto2 get_FleaMarket_List(FleamarketDto fleamarketDto);
}
