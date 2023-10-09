package com.example.service;

import com.example.model.Block;
import com.example.model.Property;
import com.example.repository.BlockRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlockServiceTest {

    @Mock
    private BlockRepository blockRepository;

    @InjectMocks
    private BlockService blockService;

    private Block block1;
    private Block block2;

    @BeforeEach
    public void setUp() {
        Property property = new Property("Property1", "Location1", "Description1");
        block1 = new Block(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 31), property);
        block2 = new Block(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 2, 28), property);
    }

    @Test
    public void testCreateBlockWhenNoOverlapThenBlockSaved() {
        when(blockRepository.saveBlock(any(Block.class))).thenReturn(block1);
        when(blockRepository.findBlocksByProperty(block1.getProperty())).thenReturn(Arrays.asList());

        Block result = blockService.createBlock(block1);

        verify(blockRepository, times(1)).saveBlock(block1);
        assertEquals(block1, result);
    }

    @Test
    public void testCreateBlockWhenOverlapThenValidationExceptionThrown() {
        when(blockRepository.findBlocksByProperty(block1.getProperty())).thenReturn(Arrays.asList(block2));

        assertThrows(ValidationException.class, () -> blockService.createBlock(block1));
    }

    @Test
    public void testDoDatesOverlapWhenDatesOverlapThenReturnTrue() throws Exception {
        block2.setStartDate(LocalDate.of(2022, 1, 15));
        Method method = BlockService.class.getDeclaredMethod("doDatesOverlap", Block.class, Block.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(blockService, block1, block2);
        assertTrue(result, "Dates should overlap");
    }

    @Test
    public void testDoDatesOverlapWhenDatesDoNotOverlapThenReturnFalse() throws Exception {
        Method method = BlockService.class.getDeclaredMethod("doDatesOverlap", Block.class, Block.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(blockService, block1, block2);
        assertFalse(result, "Dates should not overlap");
    }

    @Test
    public void testDoDatesOverlapWhenDatesAreAdjacentThenReturnFalse() throws Exception {
        block2.setStartDate(LocalDate.of(2022, 2, 1));
        block1.setEndDate(LocalDate.of(2022, 1, 31));
        Method method = BlockService.class.getDeclaredMethod("doDatesOverlap", Block.class, Block.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(blockService, block1, block2);
        assertFalse(result, "Dates should not overlap");
    }
}