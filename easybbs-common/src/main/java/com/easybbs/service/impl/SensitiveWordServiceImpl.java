package com.easybbs.service.impl;

import com.easybbs.entity.enums.PageSize;
import com.easybbs.entity.po.SensitiveWord;
import com.easybbs.entity.query.SensitiveWordQuery;
import com.easybbs.entity.query.SimplePage;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.mappers.SensitiveWordMapper;
import com.easybbs.service.SensitiveWordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/11 9:35
 */
@Service("sensitiveWordService")
public class SensitiveWordServiceImpl implements SensitiveWordService {

    @Resource
    private SensitiveWordMapper sensitiveWordMapper;

    /**
     * 按条件查询列表
     *
     * @param param
     * @return
     */
    @Override
    public List<SensitiveWord> findListByParam(SensitiveWordQuery param) {
        return this.sensitiveWordMapper.selectList(param);
    }

    /**
     * 按条件查询总数
     *
     * @param param
     * @return
     */
    @Override
    public Integer findCountByParam(SensitiveWordQuery param) {
        return this.sensitiveWordMapper.selectCount(param);
    }

    /**
     * 按条件分页查询
     *
     * @param param
     * @return
     */
    @Override
    public PaginationResultVO<SensitiveWord> findListByPage(SensitiveWordQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<SensitiveWord> list = this.findListByParam(param);
        PaginationResultVO<SensitiveWord> result = new PaginationResultVO<>(count, page.getPageSize(),
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
    public Integer add(SensitiveWord bean) {
        return this.sensitiveWordMapper.insert(bean);
    }

    /**
     * 批量新增
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addBatch(List<SensitiveWord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.sensitiveWordMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addOrUpdateBatch(List<SensitiveWord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.sensitiveWordMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    @Override
    public SensitiveWord getSensitiveWordById(Integer id) {
        return this.sensitiveWordMapper.getSensitiveWordById(id);
    }

    /**
     * 根据id更新对象
     *
     * @param bean
     * @param id
     * @return
     */
    @Override
    public Integer updateSensitiveWordById(SensitiveWord bean, Integer id) {
        return this.sensitiveWordMapper.updateSensitiveWordById(bean, id);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteSensitiveWordById(Integer id) {
        return this.sensitiveWordMapper.deleteSensitiveWordById(id);
    }

    /**
     * 根据id更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Integer updateSensitiveWordStatusById(Integer id, Integer status) {
        return this.sensitiveWordMapper.updateSensitiveWordStatusById(id, status);
    }
}
