package com.example.llmdamo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.llmdamo.entity.History;
import com.example.llmdamo.mapper.HistoryMapper;
import com.example.llmdamo.service.HistoryService;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {
}
