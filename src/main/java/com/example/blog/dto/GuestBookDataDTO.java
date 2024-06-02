package com.example.blog.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuestBookDataDTO {
    private GuestBookDTO guestBook;
    private Boolean isOneSelf;
    private List<GuestBookReplyDTO> guestBookReplyList;

}
