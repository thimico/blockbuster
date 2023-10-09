package com.example.repository;

import com.example.model.Block;
import com.example.model.Property;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BlockRepository {

    private final List<Block> blocks = new ArrayList<>();

    public Block saveBlock(Block block) {
        block.setId(String.valueOf(blocks.size() + 1));
        blocks.add(block);
        return block;
    }

    public void deleteBlock(String blockId) {
        blocks.removeIf(block -> block.getId().equals(blockId));
    }

    public Block updateBlock(String blockId, Block updatedBlock) {
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (block.getId().equals(blockId)) {
                updatedBlock.setId(blockId);
                blocks.set(i, updatedBlock);
                return updatedBlock;
            }
        }
        return null;
    }

    public Block findBlockById(String blockId) {
        for (Block block : blocks) {
            if (block.getId().equals(blockId)) {
                return block;
            }
        }
        return null;
    }

    public List<Block> findBlocksByProperty(Property property) {
        List<Block> propertyBlocks = new ArrayList<>();
        for (Block block : blocks) {
            if (block.getProperty().equals(property)) {
                propertyBlocks.add(block);
            }
        }
        return propertyBlocks;
    }
}
