package com.project.sample.service;

import com.project.sample.dao.NoticDao;
import com.project.sample.dto.FleaMarketDto2;
import com.project.sample.dto.Member;
import com.project.sample.dto.Notic;
import com.project.sample.dto.Notic2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticServiceImp implements NoticService {

    private NoticDao noticDao;
    @Autowired
    public NoticServiceImp(NoticDao noticDao) {
        this.noticDao = noticDao;
    }

    //공지사항 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public Notic2 get_Notic_List(Notic notic) {

        Notic2 notic2 = new Notic2();

        int totCnt = noticDao.get_Notic_Count(notic);
        int currentPage = notic.getCurrentPage(); //현재 페이지
        int onePageCnt = 10; //한 페이지 보여질 갯수
        int totPage = (int)Math.ceil((double)totCnt/onePageCnt);
        int st_rownum = (currentPage-1) * onePageCnt +1; //시작 rounum
        int en_rownum = currentPage * onePageCnt; //끝 rownum

        notic.setSt_rownum(st_rownum);
        notic.setEn_rownum(en_rownum);
        notic2.setNoticList(noticDao.get_Notic_List(notic));
        for(Notic n:notic2.getNoticList()){
            System.out.println(n.getNotice_id());
        }
        notic2.setCurrentPage(currentPage);
        notic2.setTotPage(totPage);
        notic2.setOnePageCnt(onePageCnt);


        return notic2;
    }

    //공지사항 상세 조회
    @Transactional(readOnly = true)
    @Override
    public Map<String,Object> get_Notic(int notice_id) {
        Map<String,Object> map = new HashMap<String,Object>();

        map.put("Notic",noticDao.get_Notic(notice_id));
        map.put("Notic_files",noticDao.get_Notic_files(notice_id));

        return map;
    }
}
