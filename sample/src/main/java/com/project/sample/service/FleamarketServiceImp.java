package com.project.sample.service;

import com.project.sample.dao.FleamarketDao;
import com.project.sample.dto.FleamarketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FleamarketServiceImp implements FleamarketService {

    private final FleamarketDao dao;
    private FileService fileservice;
    @Autowired
    public FleamarketServiceImp(FleamarketDao dao,FileService fileservice) {
        this.dao = dao;
        this.fileservice=fileservice;
    }

    @Override
    public int reg_FleaMarket(FleamarketDto fleamarketDto) {


        return dao.reg_FleaMarket(fleamarketDto);
    }

    @Override
    public int reg_FleaMarket_files(List<MultipartFile> files) {
        //파일 업로드 처리
        int fno = dao.FleaMarket_get_fno_max();
        System.out.println(fno);
        FleamarketDto fleamarketDto = new FleamarketDto();

        fleamarketDto.setFno(fno);


        for(MultipartFile file:files){
            fleamarketDto.setOrigin_file_name(file.getOriginalFilename());
            //여기서 파일 업로드 되면서 UUID파일이름뽑아옴
            fleamarketDto.setUuid_file_name(fileservice.Insprofileimg(file));
            dao.reg_FleaMarket_file(fleamarketDto);
        }
        return 0;
    }


}
