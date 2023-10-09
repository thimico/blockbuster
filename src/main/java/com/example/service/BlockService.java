package com.example.service;

import com.example.model.Block;
import com.example.model.Property;
import com.example.repository.BlockRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class BlockService {

    private final BlockRepository blockRepository;

    @Autowired
    public BlockService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    public Block createBlock(Block block) {
        if (checkBlockOverlap(block, null)) {
            return blockRepository.saveBlock(block);
        } else {
            throw new ValidationException("Block overlaps with existing blocks");
        }
    }

    public void deleteBlock(String blockId) {
        Block block = blockRepository.findBlockById(blockId);
        if (block == null) {
            throw new EntityNotFoundException("Block with ID " + blockId + " not found");
        }
        blockRepository.deleteBlock(blockId);
    }

    public Block updateBlock(String blockId, Block block) {
        Block existingBlock = blockRepository.findBlockById(blockId);
        if (existingBlock == null) {
            throw new EntityNotFoundException("Block with ID " + blockId + " not found");
        }
        if (checkBlockOverlap(block, existingBlock.getId())) {
            return blockRepository.updateBlock(blockId, block);
        } else {
            throw new ValidationException("Block overlaps with existing blocks");
        }
    }

    boolean checkBlockOverlap(Block newBlock, String excludedBlockId) {
        List<Block> existingBlocks = blockRepository.findBlocksByProperty(newBlock.getProperty());
        for (Block existingBlock : existingBlocks) {
            if (!existingBlock.getId().equals(excludedBlockId) && doDatesOverlap(newBlock, existingBlock)) {
                return false;
            }
        }
        return true;
    }

    private boolean doDatesOverlap(Block block1, Block block2) {
        LocalDate startDate1 = block1.getStartDate();
        LocalDate endDate1 = block1.getEndDate();
        LocalDate startDate2 = block2.getStartDate();
        LocalDate endDate2 = block2.getEndDate();
        return !(endDate1.isBefore(startDate2) || startDate1.isAfter(endDate2));
    }
}

