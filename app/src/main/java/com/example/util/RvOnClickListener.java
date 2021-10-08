package com.example.util;

import com.example.item.ItemCategory;
import com.example.item.ItemJob;

public interface RvOnClickListener {
    void onItemClick(int position);
    void onItemClick(int position, ItemJob itemJob);
    void onItemClick(int position, ItemCategory itemJob);
}
