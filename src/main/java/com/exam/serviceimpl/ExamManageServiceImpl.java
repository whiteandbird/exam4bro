package com.exam.serviceimpl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ExamManage;
import com.exam.entity.FillQuestion;
import com.exam.entity.JudgeQuestion;
import com.exam.entity.MultiQuestion;
import com.exam.entity.PaperManage;
import com.exam.mapper.ExamManageMapper;
import com.exam.service.ExamManageService;
import com.exam.service.FillQuestionService;
import com.exam.service.JudgeQuestionService;
import com.exam.service.MultiQuestionService;
import com.exam.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamManageServiceImpl implements ExamManageService {
    @Autowired
    private ExamManageMapper examManageMapper;
    @Autowired
    private PaperService paperService;
    @Autowired
    private FillQuestionService fillQuestionService;
    @Autowired
    private JudgeQuestionService judgeQuestionService;
    @Autowired
    private MultiQuestionService multiQuestionService;

    @Override
    public List<ExamManage> findAll() {
        List<ExamManage> all = examManageMapper.findAll();
        all.stream().forEach(
                paper ->{
                    Integer multiScore = multiQuestionService.findByIdAndType(paper.getPaperId()).stream().map(MultiQuestion::getScore).reduce(0, (a, b) -> a + b);
                    Integer judgeScore = judgeQuestionService.findByIdAndType(paper.getPaperId()).stream().map(JudgeQuestion::getScore).reduce(0, (a, b) -> a + b);
                    Integer fillScore = fillQuestionService.findByIdAndType(paper.getPaperId()).stream().map(FillQuestion::getScore).reduce(0, (a, b) -> a + b);
                    paper.setTotalScore(multiScore+judgeScore+fillScore);
                }
        );
        return all;
    }

    @Override
    public IPage<ExamManage> findAll(Page<ExamManage> page) {
        IPage<ExamManage> all = examManageMapper.findAll(page);
        all.getRecords().stream().forEach(
                paper ->{
                    Integer multiScore = multiQuestionService.findByIdAndType(paper.getPaperId()).stream().map(MultiQuestion::getScore).reduce(0, (a, b) -> a + b);
                    Integer judgeScore = judgeQuestionService.findByIdAndType(paper.getPaperId()).stream().map(JudgeQuestion::getScore).reduce(0, (a, b) -> a + b);
                    Integer fillScore = fillQuestionService.findByIdAndType(paper.getPaperId()).stream().map(FillQuestion::getScore).reduce(0, (a, b) -> a + b);
                    paper.setTotalScore(multiScore+judgeScore+fillScore);
                }
        );
        return all;
    }

    @Override
    public ExamManage findById(Integer examCode) {
        return examManageMapper.findById(examCode);
    }

    @Override
    public int delete(Integer examCode) {
        return examManageMapper.delete(examCode);
    }

    @Override
    public int update(ExamManage exammanage) {
        return examManageMapper.update(exammanage);
    }

    @Override
    public int add(ExamManage exammanage) {
        return examManageMapper.add(exammanage);
    }

    @Override
    public ExamManage findOnlyPaperId() {
        return examManageMapper.findOnlyPaperId();
    }
}
