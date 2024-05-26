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
@Table(name = "friendship")
public class Friendship {

        @Id
        @TableId(value = "friendship_id", type = IdType.AUTO)
        private int friendUserId;

        private int userId;

        private int friendId;

        private String remarks;

        private Date createdAt;
}
