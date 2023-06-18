package com.easybbs.service.impl;

import com.easybbs.entity.enums.PageSize;
import com.easybbs.entity.po.ComplaintHistoriesRecord;
import com.easybbs.entity.query.ComplaintHistoriesRecordQuery;
import com.easybbs.entity.query.SimplePage;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.mappers.ComplaintHistoriesRecordMapper;
import com.easybbs.service.ComplaintHistoriesRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/3/30 20:02
 */
@Service("complaintHistoriesRecordService")
public class ComplaintHistoriesRecordServiceImpl implements ComplaintHistoriesRecordService {

    @Resource
    private ComplaintHistoriesRecordMapper<ComplaintHistoriesRecord, ComplaintHistoriesRecordQuery> complaintHistoriesRecordMapper;

    /**
     * 按条件查询
     */
    @Override
    public List<ComplaintHistoriesRecord> findListByParam(ComplaintHistoriesRecordQuery param) {
        return this.complaintHistoriesRecordMapper.selectList(param);
    }

    /**
     * 根据条件查询总数
     *
     * @param param
     * @return
     */
    @Override
    public Integer findCountByParam(ComplaintHistoriesRecordQuery param) {
        return this.complaintHistoriesRecordMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     *
     * @param param
     * @return
     */
    @Override
    public PaginationResultVO<ComplaintHistoriesRecord> findListByPage(ComplaintHistoriesRecordQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<ComplaintHistoriesRecord> list = this.findListByParam(param);
        PaginationResultVO<ComplaintHistoriesRecord> result = new PaginationResultVO<>(count, page.getPageSize(),
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
    public Integer add(ComplaintHistoriesRecord bean) {
        return this.complaintHistoriesRecordMapper.insert(bean);
    }

    /**
     * 批量新增
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addBatch(List<ComplaintHistoriesRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.complaintHistoriesRecordMapper.insertBatch(listBean);
    }

    /**
     * 批量修改或者新增
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addOrUpdateBatch(List<ComplaintHistoriesRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.complaintHistoriesRecordMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    @Override
    public ComplaintHistoriesRecord getComplaintHistoriesById(Integer id) {
        return this.complaintHistoriesRecordMapper.getComplaintHistoriesById(id);
    }

    /**
     * 根据id更新
     *
     * @param bean
     * @param id
     * @return
     */
    @Override
    public Integer updateComplaintHistoriesById(ComplaintHistoriesRecord bean, Integer id) {
        return this.complaintHistoriesRecordMapper.updateComplaintHistoriesById(bean, id);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteComplaintHistoriesById(Integer id) {
        return this.complaintHistoriesRecordMapper.deleteComplaintHistoriesById(id);
    }
}
