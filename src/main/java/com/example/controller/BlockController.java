package com.example.controller;

import com.example.model.Block;
import com.example.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blocks")
public class BlockController {

    private final BlockService blockService;

    @Autowired
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @PostMapping
    public ResponseEntity<Block> createBlock(@RequestBody Block block) {
        Block createdBlock = blockService.createBlock(block);
        return new ResponseEntity<>(createdBlock, HttpStatus.CREATED);
    }

    @DeleteMapping("/{blockId}")
    public ResponseEntity<Void> deleteBlock(@PathVariable String blockId) {
        blockService.deleteBlock(blockId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{blockId}")
    public ResponseEntity<Block> updateBlock(@PathVariable String blockId, @RequestBody Block block) {
        Block updatedBlock = blockService.updateBlock(blockId, block);
        return new ResponseEntity<>(updatedBlock, HttpStatus.OK);
    }
}
