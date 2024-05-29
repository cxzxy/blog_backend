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
@Table(name = "blog")
public class Blog {

    @Id
    @TableId(value = "blog_id", type = IdType.AUTO)
    private Integer blogId;

    private Integer userId;

    private String title;

    private String blogContent;

    private Date createdAt;

    private Date updatedAt;

}
