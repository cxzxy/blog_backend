-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
                        `user_id` INT NOT NULL AUTO_INCREMENT,
                        `email` VARCHAR(255) NOT NULL,
                        `account` VARCHAR(255),
                        `password` VARCHAR(255) NOT NULL,
                        `created_at` DATE NOT NULL,
                        `updated_at` DATE NOT NULL,
                        PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 个人资料表
CREATE TABLE  IF NOT EXISTS `profile` (
                           `profile_id` INT NOT NULL AUTO_INCREMENT,
                           `sex` ENUM('unknown', 'female', 'male'),
                           `user_name` VARCHAR(255) NOT NULL,
                           `birthday` DATE,
                           `location` VARCHAR(255),
                           `signature` VARCHAR(255),
                           `user_id` INT NOT NULL,
                           `picture_url` VARCHAR(255),
                           `avatar_url` VARCHAR(255),
                           PRIMARY KEY (`profile_id`),
                           FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 好友请求表
CREATE TABLE  IF NOT EXISTS `friend_request` (
                          `request_id` INT NOT NULL AUTO_INCREMENT,
                          `user_id` INT NOT NULL,
                          `friend_id` INT NOT NULL,
                          `status` ENUM('pending', 'accepted', 'blocked') NOT NULL,
                          `created_at` DATE,
                          PRIMARY KEY (`request_id`),
                          FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
                          FOREIGN KEY (`friend_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 好友关系表
CREATE TABLE  IF NOT EXISTS `friendship` (
                         `friendship_id` INT NOT NULL AUTO_INCREMENT,
                         `user_id` INT NOT NULL,
                         `friend_id` INT NOT NULL,
                         `remarks` VARCHAR(255) NOT NULL,
                         `created_at` DATE,
                         PRIMARY KEY (`friendship_id`),
                         FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
                         FOREIGN KEY (`friend_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 空间访问权限表
CREATE TABLE  IF NOT EXISTS `access_control` (
                                  `access_id` INT NOT NULL AUTO_INCREMENT,
                                  `user_id` INT NOT NULL,
                                  `permission_type` ENUM('public', 'private', 'friends_only') NOT NULL,
                                  PRIMARY KEY (`access_id`),
                                  FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 日志表
CREATE TABLE  IF NOT EXISTS `blog` (
                        `blog_id` INT NOT NULL AUTO_INCREMENT,
                        `user_id` INT NOT NULL,
                        `title` VARCHAR(255) NOT NULL,
                        `blog_content` TEXT NOT NULL,
                        `created_at` DATE NOT NULL,
                        `updated_at` DATE NOT NULL,
                        PRIMARY KEY (`blog_id`),
                        FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 留言板表
CREATE TABLE  IF NOT EXISTS `guest_book` (
                             `guest_book_id` INT NOT NULL AUTO_INCREMENT,
                             `user_id` INT NOT NULL,
                             `send_user_id` INT NOT NULL,
                             `guest_book_content` TEXT NOT NULL,
                             `created_at` DATE NOT NULL,
                             PRIMARY KEY (`guest_book_id`),
                             FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
                             FOREIGN KEY (`send_user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 留言回复表
CREATE TABLE  IF NOT EXISTS `guest_book_reply` (
                                   `guest_book_reply_id` INT NOT NULL AUTO_INCREMENT,
                                   `guest_book_id` INT NOT NULL,
                                   `send_user_id` INT NOT NULL,
                                   `receive_user_id` INT NOT NULL,
                                   `reply_content` TEXT NOT NULL,
                                   `reply_time` DATE NOT NULL,
                                   PRIMARY KEY (`guest_book_reply_id`),
                                   FOREIGN KEY (`guest_book_id`) REFERENCES `guestbook`(`guestbook_id`) ON DELETE CASCADE,
                                   FOREIGN KEY (`send_user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
                                   FOREIGN KEY (`receive_user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 相册表
CREATE TABLE  IF NOT EXISTS `album` (
                          `album_id` INT NOT NULL AUTO_INCREMENT,
                          `user_id` INT NOT NULL,
                          `album_title` VARCHAR(255) NOT NULL,
                          `album_creat_time` DATE NOT NULL,
                          PRIMARY KEY (`album_id`),
                          FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 相册图片表
CREATE TABLE  IF NOT EXISTS `picture` (
                           `picture_id` INT NOT NULL AUTO_INCREMENT,
                           `albums_id` INT NOT NULL,
                           `picture_url` VARCHAR(255) NOT NULL,
                           `picture_creat_time` DATE NOT NULL,
                           PRIMARY KEY (`picture_id`),
                           FOREIGN KEY (`albums_id`) REFERENCES `albums`(`albums_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 心情状态表
CREATE TABLE  IF NOT EXISTS `mood` (
                        `mood_id` INT NOT NULL AUTO_INCREMENT,
                        `user_id` INT NOT NULL,
                        `mood_content` ENUM('忙忙忙','写博客ing','悠哉哉','emo中','元气满满','睡觉中') NOT NULL,
                        `updated_at` DATE NOT NULL,
                        PRIMARY KEY (`mood_id`),
                        FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 空间装扮表
CREATE TABLE  IF NOT EXISTS `space_decoration` (
                                    `space_decoration_id` INT NOT NULL AUTO_INCREMENT,
                                    `user_id` INT NOT NULL,
                                    `theme_index` ENUM('0', '1', '2') NOT NULL,
                                    `updated_at` DATE NOT NULL,
                                    PRIMARY KEY (`space_decoration_id`),
                                    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
