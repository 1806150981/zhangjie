package com.icss.demo.controller;

import com.icss.demo.bean.MaXiangYu;
import com.icss.demo.service.MaXiangYuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author liu jun
 */
@RestController
@RequestMapping("/maxiangyu")
public class AddMaXiangYuController {

    @Autowired
    private MaXiangYuService maXiangYuService;

    @RequestMapping("/add")
    public String add(@RequestBody MaXiangYu maXiangYu) {

        return maXiangYuService.add(maXiangYu);
    }

    @RequestMapping("/query")
    public List<MaXiangYu> query(String name) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);
        for (int i = 0; i < integers.size(); i++) {
            integers.get(i);
        }
        for (Integer b : integers) {

        }
        return maXiangYuService.query(name);
    }
}
