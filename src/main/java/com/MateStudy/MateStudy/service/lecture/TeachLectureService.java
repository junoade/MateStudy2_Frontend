package com.MateStudy.MateStudy.service.lecture;

import com.MateStudy.MateStudy.domain.account.Member;
import com.MateStudy.MateStudy.domain.lecture.Lecture;
import com.MateStudy.MateStudy.domain.lecture.Teaching_Lecture;
import com.MateStudy.MateStudy.repository.MemberRepository;
import com.MateStudy.MateStudy.repository.lecture.LectureRepository;
import com.MateStudy.MateStudy.repository.lecture.TeachLectureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * 강좌 - 교수자 Repository를 활용하여 서비스 제공
 */
@Service
@Slf4j
@AllArgsConstructor
public class TeachLectureService {

    @Autowired
    TeachLectureRepository teachingRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    MemberRepository memberRepository;

    /* 교수자의 학번, 등록하고자 하는 학수번호, 분반 정보 입력시 "학습 중 강좌"에 교수자 등록*/
    @Transactional
    public void setInstructor(String id, String lecCode, long subCode){
        Optional<Lecture> lecture = lectureRepository.getOneLecture(lecCode, subCode);
        Optional<Member> instructor = memberRepository.findByName(id);

        if(lecture.isPresent() && instructor.isPresent()){
            Teaching_Lecture result = Teaching_Lecture.builder()
                    .instId(instructor.get())
                    .lecCode(lecture.get().getLecCode())
                    .subCode(lecture.get().getSubCode())
                    .build();
            teachingRepository.save(result);
        }else{
            log.info("강좌에 교수자 지정 실패");
        }
    }

}
