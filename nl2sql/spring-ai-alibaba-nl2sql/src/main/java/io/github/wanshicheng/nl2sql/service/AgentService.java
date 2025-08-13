package io.github.wanshicheng.nl2sql.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.wanshicheng.nl2sql.entity.Agent;
import io.github.wanshicheng.nl2sql.mapper.AgentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AgentService {
    @Autowired
    private AgentMapper agentMapper;

    public List<Agent> findAll() {
        return agentMapper.selectList(new QueryWrapper<>());
    }

    public Agent findById(Long id) {
        return agentMapper.selectById(id);
    }

    public List<Agent> findByStatus(String status) {
        QueryWrapper<Agent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status).orderByDesc("create_time");
        return agentMapper.selectList(queryWrapper);
    }

    public List<Agent> search(String keyword) {
        String searchPattern = "%" + keyword + "%";
        QueryWrapper<Agent> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", searchPattern)
                .like("description", searchPattern)
                .like("tag", searchPattern)
                .orderByDesc("create_time");
        return agentMapper.selectList(queryWrapper);
    }

    public Agent save(Agent agent) {
        LocalDateTime now = LocalDateTime.now();


        if (agent.getId() == null) {
            // 新增
            agent.setCreateTime(now);
            agent.setUpdateTime(now);
            agentMapper.insert(agent);
        }
        else {
            // 更新
            agent.setUpdateTime(now);
            agentMapper.updateById(agent);
        }

        return agent;
    }

    public void deleteById(Long id) {
        try {
            // 删除数据库中的智能体记录
            agentMapper.deleteById(id);
            log.info("Successfully deleted agent: {}", id);
        }
        catch (Exception e) {
            log.error("Failed to delete agent: {}", id, e);
            throw e;
        }
    }
}
