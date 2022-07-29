package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.vo.AnswerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AnswerMapper {
    @Select("select questionId, question, subject, score, section,level, \"选择题\" as type, 1 as questionType from multi_question " +
            "union select questionId, question, subject, score, section,level, \"判断题\" as type, 2 as questionType  from judge_question " +
            "union select questionId, question, subject, score, section,level, \"填空题\" as type, 3 as questionType from fill_question")
    IPage<AnswerVO> findAll(Page page);
}
