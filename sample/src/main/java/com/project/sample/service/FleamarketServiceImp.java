package com.project.sample.service;

import com.project.sample.common.AESImp;
import com.project.sample.common.FileService;
import com.project.sample.dao.FleamarketDao;
import com.project.sample.dao.MemberDao;
import com.project.sample.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FleamarketServiceImp implements FleamarketService {

    private final FleamarketDao fleamarketDao;
    private final MemberDao memberDao;
    private FileService fileservice;
    private final AESImp aes;
    @Autowired
    public FleamarketServiceImp(FleamarketDao fleamarketDao, FileService fileservice, MemberDao memberDao, AESImp aes) {
        this.fleamarketDao = fleamarketDao;
        this.fileservice=fileservice;
        this.memberDao=memberDao;
        this.aes = aes;
    }

    //게시글 등록 -> 해당 게시글 번호 들고 복귀
    @Override
    public int reg_FleaMarket(FleamarketDto fleamarketDto,String method) {

        if(method.equals("insert")){
            fleamarketDao.reg_FleaMarket(fleamarketDto);
            return fleamarketDao.FleaMarket_get_fno_max();
        //method = update 일경우
        }else{
            //게시글 업데이트
            System.out.println("수정ser");
            fleamarketDao.upt_FleaMarket(fleamarketDto);
            return fleamarketDto.getFno();
        }
    }
    // 이미지 등록+ 파일 업로드
    @Override
    public int reg_FleaMarket_files(List<MultipartFile> files,int fno,String method) {

        //업데이트 일경우 기존 파일 삭제 진행
        if(method.equals("update")){
            //파일 리스트 부터 뽑아옴
            del_Fleamarket_Files(fno);
            //DB파일 리스트 삭제
            fleamarketDao.del_FleaMarket_files(fno);
        }

        //파일 업로드 처리
        FleamarketDto fleamarketDto = new FleamarketDto();

        fleamarketDto.setFno(fno);

        for(MultipartFile file:files){
            String origin_file_name = file.getOriginalFilename().replace(" ","");
            fleamarketDto.setOrigin_file_name(origin_file_name);
            //여기서 파일 업로드 되면서 UUID파일이름뽑아옴
            fleamarketDto.setUuid_file_name(fileservice.Insprofileimg(file));
            fleamarketDao.reg_FleaMarket_file(fleamarketDto);
        }
        return 0;
    }

    //메인페이지에 보여질 게시글 리스트+썸네일 사진 하나씩
    @Override
    public FleaMarketDto2 get_FleaMarket_List(FleamarketDto fleamarketDto) {
        System.out.println("get_FleaMarket_List 시작");

        FleaMarketDto2 fleaMarketDto2 = new FleaMarketDto2();

        //페이징용 글 갯수 등 정의
        int onePageCnt = 6; //한 페이지 보여질 갯수
        int currentPage = fleamarketDto.getCurrentPage(); //현재 페이지
        int totCnt = fleamarketDao.get_FleaMarket_totCnt(fleamarketDto); //게시글 전체 갯수
        int totPage = (int)Math.ceil((double)totCnt/onePageCnt); //총 페이지 갯수
        int st_rownum = (currentPage-1) * onePageCnt +1; //시작 rounum
        int en_rownum = currentPage * onePageCnt; //끝 rownum

        //검색 내용 및 ROWNUM에 맞는 게시글 들고와서 담기
        fleamarketDto.setSt_rownum(st_rownum);
        fleamarketDto.setEn_rownum(en_rownum);
        fleaMarketDto2.setFleamarketDtoList(fleamarketDao.get_FleaMarket_List(fleamarketDto));

//        System.out.println("현재페이지:"+currentPage);
//        System.out.println("검색단어:"+fleamarketDto.getTitle());
//        System.out.println("현재페이지:"+st_rownum);
//        System.out.println("현재페이지:"+en_rownum);
//        System.out.println("검색글 갯수:"+fleaMarketDto2.getFleamarketDtoList().size());

        //Front에서 사용할 페이징정보
        fleaMarketDto2.setCurrentPage(currentPage);
        fleaMarketDto2.setTotPage(totPage);
        fleaMarketDto2.setOnePageCnt(onePageCnt);

        //게시글 리스트 담기
        return fleaMarketDto2;
    }

    //게시글 상세조회
    @Override
    public FleamarketDto get_FleaMarket(int fno) {
        return fleamarketDao.get_FleaMarket(fno);
    }

    //상세조회의 이미지 파일들
    @Override
    public List<Fleamarket_Files> get_FleaMarket_files(int fno) {
        return fleamarketDao.get_FleaMarket_files(fno);
    }

    //게시글삭제
    @Override
    public int del_FleaMarket(int fno) {
        //아래 파일 삭제 메서드 진행
        del_Fleamarket_Files(fno);
        // 신청글 삭제
        fleamarketDao.del_Application_FM(fno);
        //게시글삭제
        return fleamarketDao.del_FleaMarket(fno);
    }

    //신청하기
    @Override
    public int application_FM(int fno, String userno) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("fno",fno);
        map.put("userno",userno);

        return fleamarketDao.application_FM(map);
    }

    //동일신청자 중복 확인
    @Override
    public int Check_Application(int fno, String userno) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("fno",fno);
        map.put("userno",userno);


        return fleamarketDao.Check_Application(map);
    }

    //특정 게시글에 대한 신청자 목록 확인
    @Override
    public Map<String,Object> get_application_FM(Application_FM applicationFm) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String , Object> map = new HashMap<>();

        //각 상태에 대한 할당
        String[] states = {"대기","승인","거절"};
        String[] states_mapname = {"wait","approval","reject"};
        
        //데이터 가져오기
        for( int i = 0; i  <states.length; i++ ){

            // 대기/승인/거절 각 돌면서 List들고오기
            applicationFm.setState(states[i]);
            List<Application_FM> applicationFmList = fleamarketDao.get_application_FM(applicationFm);

            //복호화처리(유저정보List -> 복호화할 Field선정 -> 복호화 ->setter호출)
            for( Application_FM a : applicationFmList ){

                // 필드명을 가져옴  맨앞글자 대문자+맨앞글자제외문자 => Name,Eamil
                Field[] fields = a.getClass().getDeclaredFields();
                for ( Field field : fields ) {
                    //private혹은 projected 필드에 접근할 수 있도록 허용
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    //복호화가 필요없는 컬럼이 아닐경우
                    if( fieldName.equals("name") || fieldName.equals("address") || fieldName.equals("phoneNumber") ||fieldName.equals("email")){
                        //set/get을 위해 field이름의 앞글자를 대문자로 전환
                        String methodName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                        // 현재 필드의 get+methodName으로 getter호출
                        Method getter = a.getClass().getMethod("get" + methodName);
                        // 현재 필드의 setter 호출
                        Method setter = a.getClass().getMethod("set" + methodName, String.class);
                        // getter로 호출하여 현재 문자열 필드의 암호화값을 얻음
                        String EncValue = (String) getter.invoke(a);
                        //복호화
                        String  Originvalue = aes.decrypt(EncValue);
                        //평문세팅
                        setter.invoke(a,Originvalue);
                    }
                }

            }

            //개인정보 일부 가리기
            for( Application_FM a : applicationFmList ){
                //이름 별처리
                a.setName(aes.ProtectName(a.getName()));
               //이메일 별처리
                a.setEmail(aes.ProtectEmail(a.getEmail()));
               //폰번 별처리
                a.setPhoneNumber(aes.ProtectPhoneNumber(a.getPhoneNumber()));
               //주소 구까지 처리
                a.setAddress(aes.Protectaddress(a.getAddress()));
            }

            //할당
            //states_mapname = {"wait","approval","reject"}
            map.put( states_mapname[i] + "_appliication_FM" , applicationFmList );
        }

        return map;
    }

    //승인/대기/거부시 업데이트
    @Override
    public int upt_application_FM( Application_FM applicationFm ) {

        //System.out.println("상태 수정될 게시글번호:"+applicationFm.getFno());
        //System.out.println("상태 수정될 상태값:"+applicationFm.getState());
        //System.out.println("상태 수정될 유저번호:"+applicationFm.getUserno());

        return fleamarketDao.upt_application_FM( applicationFm );
    }

    //상태 업데이트 이후, 해당 게시글의 승인갯수 업데이트
    @Override
    public int upt_apl_FM_ACount( Application_FM applicationFm ) {

        //현재 승인처리된 갯수불러오기
        int count = fleamarketDao.get_Application_count( applicationFm.getFno() );
        applicationFm.setCount(count);
        //해당 게시글의 승인 갯수 업데이트
        fleamarketDao.upt_apl_FM_Count(applicationFm);
        return count;
    }

    //본인이 작성한 플리마켓 게시글
    @Override
    public FleaMarketDto2 get_My_FleaMarket( FleamarketDto fleamarketDto ) {
        System.out.println("get_FleaMarket_List 실행");
        FleaMarketDto2 fleaMarketDto2 = new FleaMarketDto2();

        int onePageCnt = 10; //한 페이지 보여질 갯수
        int currentPage = fleamarketDto.getCurrentPage(); //현재 페이지
        int totCnt = fleamarketDao.get_My_FleaMarket_Count( fleamarketDto ); //게시글 전체 갯수
        int totPage = (int)Math.ceil( (double)totCnt/onePageCnt ); //총 페이지 갯수
        int st_rownum = (currentPage-1) * onePageCnt +1; //시작 rounum
        int en_rownum = currentPage * onePageCnt; //끝 rownum

        // System.out.println("현재페이지:"+currentPage);
        //System.out.println("검색단어:"+fleamarketDto.getTitle());
        //System.out.println("시작 ROWNUM:"+st_rownum);
        //System.out.println("끝 ROWNUM:"+en_rownum);

        //검색전 ROWNUM 세팅
        fleamarketDto.setSt_rownum(st_rownum);
        fleamarketDto.setEn_rownum(en_rownum);
        fleaMarketDto2.setFleamarketDtoList( fleamarketDao.get_My_FleaMarket(fleamarketDto) );

        fleaMarketDto2.setCurrentPage(currentPage);
        fleaMarketDto2.setTotPage(totPage);
        fleaMarketDto2.setOnePageCnt(onePageCnt);

        return fleaMarketDto2;
    }

    //본인이 신청한 플리마켓 글 목록 확인
    @Override
    public FleaMarketDto2 get_My_Application(FleamarketDto fleamarketDto){
        System.out.println( "get_FleaMarket_List 시작" );

        FleaMarketDto2 fleaMarketDto2 = new FleaMarketDto2();

        //페이징 처리구간
        int onePageCnt = 10; //한 페이지 보여질 갯수
        int currentPage = fleamarketDto.getCurrentPage(); //현재 페이지
        int totCnt = fleamarketDao.get_My_Application_Count(fleamarketDto); //게시글 전체 갯수
        int totPage = (int)Math.ceil( (double)totCnt/onePageCnt ); //총 페이지 갯수
        int st_rownum = (currentPage-1) * onePageCnt +1; //시작 rounum
        int en_rownum = currentPage * onePageCnt; //끝 rownum

        //System.out.println("Application:현재페이지:"+currentPage);
        //System.out.println("Application 전체페이지"+totCnt);
        //System.out.println("현재페이지:"+st_rownum);
        //System.out.println("현재페이지:"+en_rownum);


        //검색 내용 및 ROWNUM에 맞는 게시글 들고와서 담기
        fleamarketDto.setSt_rownum(st_rownum);
        fleamarketDto.setEn_rownum(en_rownum);
        fleaMarketDto2.setFleamarketDtoList(fleamarketDao.get_My_Application(fleamarketDto));


        //Front에서 사용할 페이징숫자들
        fleaMarketDto2.setCurrentPage(currentPage);
        fleaMarketDto2.setTotPage(totPage);
        fleaMarketDto2.setOnePageCnt(onePageCnt);

        return fleaMarketDto2;
    }

//=======================================================================================
    //파일 삭제처리 공통 메서드
    public void del_Fleamarket_Files(int fno){
        //파일 리스트 부터 뽑아옴
        List<Fleamarket_Files> fleamarket_files = fleamarketDao.get_FleaMarket_files(fno);
        //파일 삭제
        for(Fleamarket_Files file:fleamarket_files){
            fileservice.DeleteFile(file.getUuid_file_name());
        }

        fleamarketDao.del_FleaMarket_files(fno);
    }

}
