package com.easybbs.service.impl;

import com.easybbs.entity.enums.PageSize;
import com.easybbs.entity.po.ImgCategory;
import com.easybbs.entity.query.ImgCategoryQuery;
import com.easybbs.entity.query.SimplePage;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.mappers.ImgCategoryMapper;
import com.easybbs.service.ImgCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/24 11:50
 */
@Service("imgCategoryService")
public class ImgCategoryServiceImpl implements ImgCategoryService {

    @Resource
    private ImgCategoryMapper<ImgCategory, ImgCategoryQuery> imgCategoryMapper;

    /**
     * 查询集合
     *
     * @param param
     * @return
     */
    @Override
    public List<ImgCategory> findListByParam(ImgCategoryQuery param) {
        return this.imgCategoryMapper.selectList(param);
    }

    /**
     * 根据条件查询总数
     *
     * @param param
     * @return
     */
    @Override
    public Integer findCountByParam(ImgCategoryQuery param) {
        return this.imgCategoryMapper.selectCount(param);
    }

    /**
     * 根据条件分页查询
     *
     * @param param
     * @return
     */
    @Override
    public PaginationResultVO<ImgCategory> findListByPage(ImgCategoryQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<ImgCategory> list = this.findListByParam(param);
        PaginationResultVO<ImgCategory> result = new PaginationResultVO<>(count, page.getPageSize(),
                page.getPageTotal(), list);

        return result;
    }

    /**
     * 新增
     *
     * @param bean
     * @return
     */
    @Override
    public Integer add(ImgCategory bean) {
        return this.imgCategoryMapper.insert(bean);
    }

    /**
     * 插入或者更新
     *
     * @param bean
     * @return
     */
    @Override
    public Integer insertOrUpdate(ImgCategory bean) {
        return this.imgCategoryMapper.insertOrUpdate(bean);
    }


    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public ImgCategory getImgCategoryById(Integer id) {
        return this.imgCategoryMapper.getImgCategoryById(id);
    }

    /**
     * 根据id更新分类
     *
     * @param bean
     * @param id
     * @return
     */
    @Override
    public Integer updateImgCategoryById(ImgCategory bean, Integer id) {
        return this.imgCategoryMapper.updateImgCategoryById(bean, id);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteImgCategoryById(Integer id) {
        return this.imgCategoryMapper.deleteImgCategoryById(id);
    }
}
