package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "access_control")
public class AccessControl {

    @Id
    @TableId(value = "access_id", type = IdType.AUTO)
    private Integer accessId;

    // 与用户表的 userId 关联
    private Integer userId;

    private String permissionType; // 枚举类型需要转换为String或使用枚举类
}