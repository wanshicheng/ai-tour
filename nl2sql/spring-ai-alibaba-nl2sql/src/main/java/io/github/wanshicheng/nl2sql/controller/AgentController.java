package io.github.wanshicheng.nl2sql.controller;

import io.github.wanshicheng.nl2sql.entity.Agent;
import io.github.wanshicheng.nl2sql.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agent")
public class AgentController {
    @Autowired
    private AgentService agentService;

    /**
     * 获取智能体列表
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Agent>> list(@RequestParam(required = false) String status,
                                            @RequestParam(required = false) String keyword) {
        List<Agent> result;
        if (keyword != null && !keyword.trim().isEmpty()) {
            result = agentService.search(keyword);
        }
        else if (status != null && !status.trim().isEmpty()) {
            result = agentService.findByStatus(status);
        }
        else {
            result = agentService.findAll();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 根据ID获取智能体详情
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Agent> get(@PathVariable Long id) {
        Agent agent = agentService.findById(id);
        if (agent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(agent);
    }

    /**
     * 创建智能体
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Agent> create(@RequestBody Agent agent) {
        // 设置默认状态
        if (agent.getStatus() == null || agent.getStatus().trim().isEmpty()) {
            agent.setStatus("draft");
        }
        Agent saved = agentService.save(agent);
        return ResponseEntity.ok(saved);
    }

    /**
     * 更新智能体
     */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Agent> update(@PathVariable Long id, @RequestBody Agent agent) {
        if (agentService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        agent.setId(id);
        Agent updated = agentService.save(agent);
        return ResponseEntity.ok(updated);
    }

    /**
     * 删除智能体
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (agentService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        agentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 发布智能体
     */
    @PostMapping("/{id}/publish")
    @ResponseBody
    public ResponseEntity<Agent> publish(@PathVariable Long id) {
        Agent agent = agentService.findById(id);
        if (agent == null) {
            return ResponseEntity.notFound().build();
        }
        agent.setStatus("published");
        Agent updated = agentService.save(agent);
        return ResponseEntity.ok(updated);
    }

    /**
     * 下线智能体
     */
    @PostMapping("/{id}/offline")
    @ResponseBody
    public ResponseEntity<Agent> offline(@PathVariable Long id) {
        Agent agent = agentService.findById(id);
        if (agent == null) {
            return ResponseEntity.notFound().build();
        }
        agent.setStatus("offline");
        Agent updated = agentService.save(agent);
        return ResponseEntity.ok(updated);
    }
}
