package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "space_decoration")
public class SpaceDecoration {

    @Id
    @TableId(value = "space_decoration_id", type = IdType.AUTO)
    private Integer spaceDecorationId;

    // 与用户表的 userId 关联
    private Integer userId;

    private Integer theme; // 假设枚举类型以整型存储

    private Date updatedAt;

}