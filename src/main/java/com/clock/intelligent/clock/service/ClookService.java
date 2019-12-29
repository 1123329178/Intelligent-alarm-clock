package com.clock.intelligent.clock.service;

import com.clock.intelligent.clock.dto.ClookDTO;
import com.clock.intelligent.clock.exception.CustomizeErrorCode;
import com.clock.intelligent.clock.exception.CustomizeException;
import com.clock.intelligent.clock.mapper.ClockUserMapper;
import com.clock.intelligent.clock.mapper.ClookMapper;
import com.clock.intelligent.clock.model.ClockUser;
import com.clock.intelligent.clock.model.Clook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 音乐设置查询
 *
 * @author hp
 */
@Service
public class ClookService {
    @Autowired
    private ClookMapper clookMapper;
    @Autowired
    private ClockUserMapper clockUserMapper;

    public ClookDTO getId(int id) {
        Clook clook = clookMapper.findById(id);
        if (clook == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        ClookDTO clookDTO = new ClookDTO();
        BeanUtils.copyProperties(clook, clookDTO);
        ClockUser user = clockUserMapper.findClockUserBySerialNumber(clook.getSerialNumber());
        clookDTO.setClockUser(user);
        return clookDTO;
    }

    public void createOrUpdate(Clook clook) {
        if (clook.getCreator() != null) {
            // 创建
            clookMapper.insert(clook);
        } else {
            // 更新
//            Question updateQuestion = new Question();
//            updateQuestion.setGmtModified(System.currentTimeMillis());
//            updateQuestion.setTitle(question.getTitle());
//            updateQuestion.setDescription(question.getDescription());
//            updateQuestion.setTag(question.getTag());
//            QuestionExample example = new QuestionExample();
//            example.createCriteria()
//                    .andIdEqualTo(question.getId());
//            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
//            if (updated != 1) {
//                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
//            }
        }
    }
}
