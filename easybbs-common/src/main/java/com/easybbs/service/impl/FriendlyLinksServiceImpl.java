package com.easybbs.service.impl;

import com.easybbs.entity.enums.PageSize;
import com.easybbs.entity.po.FriendlyLinks;
import com.easybbs.entity.query.FriendlyLinksQuery;
import com.easybbs.entity.query.SimplePage;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.mappers.FriendlyLinksMapper;
import com.easybbs.service.FriendlyLinksService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("friendlyLinksService")
public class FriendlyLinksServiceImpl implements FriendlyLinksService {

    @Resource
    private FriendlyLinksMapper<FriendlyLinks, FriendlyLinksQuery> friendlyLinksMapper;

    /**
     * 按条件查询列表
     */
    @Override
    public List<FriendlyLinks> findListByParam(FriendlyLinksQuery param) {
        return this.friendlyLinksMapper.selectList(param);
    }

    /**
     * 按条件查询总数
     *
     * @param param
     * @return
     */
    @Override
    public Integer findCountByParam(FriendlyLinksQuery param) {
        return this.friendlyLinksMapper.selectCount(param);
    }

    /**
     * 按条件分页查询
     *
     * @param param
     * @return
     */
    @Override
    public PaginationResultVO<FriendlyLinks> findListByPage(FriendlyLinksQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<FriendlyLinks> list = this.findListByParam(param);
        PaginationResultVO<FriendlyLinks> result = new PaginationResultVO<>(count, page.getPageSize(),
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
    public Integer add(FriendlyLinks bean) {
        return this.friendlyLinksMapper.insert(bean);
    }

    /**
     * 批量新增
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addBatch(List<FriendlyLinks> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.friendlyLinksMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addOrUpdateBatch(List<FriendlyLinks> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.friendlyLinksMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public FriendlyLinks getFriendlyLinksById(Integer id) {
        return this.friendlyLinksMapper.getFriendlyLinksById(id);
    }

    /**
     * 根据id更新
     *
     * @param bean
     * @param id
     * @return
     */
    @Override
    public Integer updateFriendlyLinksById(FriendlyLinks bean, Integer id) {
        return this.friendlyLinksMapper.updateFriendlyLinksById(bean, id);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteFriendlyLinksById(Integer id) {
        return this.friendlyLinksMapper.deleteFriendlyLinksById(id);
    }

    /**
     * 根据id修改状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Integer updateFriendlyLinksStatusById(Integer id, Integer status) {
        return this.friendlyLinksMapper.updateFriendlyLinksStatusById(id, status);
    }
}
