package com.icss.demo.dao;

import com.icss.demo.bean.MaXiangYu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liu jun
 */
@Repository
public interface MaXiangYuDao extends CrudRepository<MaXiangYu, Long> {

    List<MaXiangYu> findMaXiangYusByName(String name);
}
