package com.springbootjpa.board.service;

import com.springbootjpa.board.dto.BoardDTO;
import com.springbootjpa.board.controller.BoardController;
import com.springbootjpa.board.entity.BoardEntity;
import com.springbootjpa.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity (Entity Class)
// Entity -> DTO  (DTO Class)
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity: boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }
    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }
    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        }else{
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        // spring boot jpa에서는 save()가 update insert 둘다 해준다.
        // 구분하는 방법은 ? id값이 존재하면 update, 존재하지 않으면 insert
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }
}
