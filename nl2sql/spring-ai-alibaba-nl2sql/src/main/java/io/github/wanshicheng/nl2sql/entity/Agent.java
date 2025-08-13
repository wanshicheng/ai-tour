package io.github.wanshicheng.nl2sql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("agent")
public class Agent {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name; // 智能体名称

    private String description; // 智能体描述

    private String avatar; // 头像URL

    private String status; // 状态：draft-待发布，published-已发布，offline-已下线

    private String prompt; // 自定义Prompt配置

    private String category; // 分类

    private Long adminId; // 管理员ID

    private String tags; // 标签，逗号分隔

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
