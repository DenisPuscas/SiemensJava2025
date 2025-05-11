package com.siemens.internship.service;

import com.siemens.internship.model.Item;
import com.siemens.internship.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processItemsAsync() throws Exception {
        List<Long> ids = List.of(1L, 2L, 3L);
        Item item1 = new Item(1L, "Item1", "Desc1", null, "item1@email.com");
        Item item2 = new Item(2L, "Item2", "Desc2", null, "item2@email.com");
        Item item3 = new Item(3L, "Item3", "Desc3", null, "item3@email.com");

        when(itemRepository.findAllIds()).thenReturn(ids);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
        when(itemRepository.findById(2L)).thenReturn(Optional.of(item2));
        when(itemRepository.findById(3L)).thenReturn(Optional.of(item3));
        when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArgument(0));

        List<Item> result = itemService.processItemsAsync().get();

        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(item -> "PROCESSED".equals(item.getStatus())));
        verify(itemRepository, times(3)).save(any(Item.class));
    }
}
