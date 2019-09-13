package ru.gavrilov.libraryreact.mappers;

import org.mapstruct.Mapper;
import ru.gavrilov.libraryreact.dto.CommentDto;
import ru.gavrilov.libraryreact.model.Comment;

@Mapper(uses = BookMapper.class)
public interface CommentMapper {

    CommentDto commentToCommentDto(Comment comment);

    Comment toComment(CommentDto dto);
}
