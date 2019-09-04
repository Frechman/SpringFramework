package ru.gavrilov.libraryreact.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.gavrilov.libraryreact.dto.CommentDto;
import ru.gavrilov.libraryreact.model.Comment;

@Mapper(uses = BookMapper.class)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto commentToCommentDto(Comment comment);
}
