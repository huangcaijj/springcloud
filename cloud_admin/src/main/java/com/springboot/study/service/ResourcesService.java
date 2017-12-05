package com.springboot.study.service;

import com.github.pagehelper.PageInfo;
import com.springboot.study.domain.Resources;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangqj on 2017/4/25.
 */
@Service
public interface ResourcesService extends IService<Resources> {
    PageInfo<Resources> selectByPage(Resources resources, int start, int length);

    public List<Resources> queryAll();

    public List<Resources> loadUserResources(Map<String, Object> map);

    public List<Resources> queryResourcesListWithSelected(Integer rid);
}
