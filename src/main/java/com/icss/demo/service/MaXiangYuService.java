package com.icss.demo.service;

import com.icss.demo.bean.MaXiangYu;
import com.icss.demo.dao.MaXiangYuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liu jun
 */
@Service
public class MaXiangYuService {

    @Autowired
    private MaXiangYuDao maXiangYuDao;

    public String add(MaXiangYu maXiangYu) {
        maXiangYuDao.save(maXiangYu);
        return "success";
    }

    public List<MaXiangYu> query(String name) {

        ArrayList<MaXiangYu> maXiangYus = new ArrayList<>();
        maXiangYuDao.findAll().forEach(maXiangYus::add);
        return maXiangYus;
    }
}
