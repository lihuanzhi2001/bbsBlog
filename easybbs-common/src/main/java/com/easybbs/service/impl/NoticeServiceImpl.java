package com.easybbs.service.impl;

import com.easybbs.entity.enums.PageSize;
import com.easybbs.entity.po.Notice;
import com.easybbs.entity.query.NoticeQuery;
import com.easybbs.entity.query.SimplePage;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.mappers.NoticeMapper;
import com.easybbs.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/4 17:38
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    /**
     * 按条件查询列表
     *
     * @param param
     * @return
     */
    @Override
    public List<Notice> findListByParam(NoticeQuery param) {
        return this.noticeMapper.selectList(param);
    }

    /**
     * 按条件查询总数
     *
     * @param param
     * @return
     */
    @Override
    public Integer findCountByParam(NoticeQuery param) {
        return this.noticeMapper.selectCount(param);
    }

    /**
     * 按条件分页查询
     *
     * @param param
     * @return
     */
    @Override
    public PaginationResultVO<Notice> findListByPage(NoticeQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<Notice> list = this.findListByParam(param);
        PaginationResultVO<Notice> result = new PaginationResultVO<>(count, page.getPageSize(),
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
    public Integer add(Notice bean) {
        return this.noticeMapper.insert(bean);
    }

    /**
     * 批量新增
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addBatch(List<Notice> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.noticeMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addOrUpdateBatch(List<Notice> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.noticeMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    @Override
    public Notice getNoticeById(Integer id) {
        return this.noticeMapper.getNoticeById(id);
    }

    /**
     * 根据id更新对象
     *
     * @param bean
     * @param id
     * @return
     */
    @Override
    public Integer updateNoticeById(Notice bean, Integer id) {
        return this.noticeMapper.updateNoticeById(bean, id);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteNoticeById(Integer id) {
        return this.noticeMapper.deleteNoticeById(id);
    }

    /**
     * 根据id更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Integer updateNoticeStatusById(Integer id, Integer status) {
        return this.noticeMapper.updateNoticeStatusById(id, status);
    }
}
