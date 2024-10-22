package co.kr.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.kr.shop.mapper.SalesMapper;
import co.kr.shop.model.SalesDataDTO;

@Service
public class SalesServiceImpl implements SalesService {
    @Autowired
    private SalesMapper salesMapper;

    @Override
    public List<SalesDataDTO> getSalesData() {
        return salesMapper.getSalesData();
    }
    
}
