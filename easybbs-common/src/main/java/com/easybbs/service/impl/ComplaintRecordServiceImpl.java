package com.easybbs.service.impl;

import com.easybbs.entity.enums.PageSize;
import com.easybbs.entity.po.ComplaintRecord;
import com.easybbs.entity.query.ComplaintRecordQuery;
import com.easybbs.entity.query.SimplePage;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.mappers.ComplaintRecordMapper;
import com.easybbs.service.ComplaintRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/3/30 20:02
 */
@Service("complaintRecordService")
public class ComplaintRecordServiceImpl implements ComplaintRecordService {

    @Resource
    private ComplaintRecordMapper<ComplaintRecord, ComplaintRecordQuery> complaintRecordMapper;

    /**
     * 按条件查询列表
     *
     * @param param
     * @return
     */
    @Override
    public List<ComplaintRecord> findListByParam(ComplaintRecordQuery param) {
        return this.complaintRecordMapper.selectList(param);
    }

    /**
     * 按条件查询总数
     *
     * @param param
     * @return
     */
    @Override
    public Integer findCountByParam(ComplaintRecordQuery param) {
        return this.complaintRecordMapper.selectCount(param);
    }

    /**
     * 按条件分页查询
     *
     * @param param
     * @return
     */
    @Override
    public PaginationResultVO<ComplaintRecord> findListByPage(ComplaintRecordQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<ComplaintRecord> list = this.findListByParam(param);
        PaginationResultVO<ComplaintRecord> result = new PaginationResultVO<>(count, page.getPageSize(),
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
    public Integer add(ComplaintRecord bean) {
        return this.complaintRecordMapper.insert(bean);
    }

    /**
     * 批量新增
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addBatch(List<ComplaintRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.complaintRecordMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addOrUpdateBatch(List<ComplaintRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.complaintRecordMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    @Override
    public ComplaintRecord getComplaintRecordById(Integer id) {
        return this.complaintRecordMapper.getComplaintRecordById(id);
    }

    /**
     * 根据id更新对象
     *
     * @param bean
     * @param id
     * @return
     */
    @Override
    public Integer updateComplaintRecordById(ComplaintRecord bean, Integer id) {
        return this.complaintRecordMapper.updateComplaintRecordById(bean, id);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteComplaintRecordById(Integer id) {
        return this.complaintRecordMapper.deleteComplaintRecordById(id);
    }

    /**
     * 根据id更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Integer updateComplaintStatusById(Integer id, Integer status) {
        return this.complaintRecordMapper.updateComplaintStatusById(id, status);
    }
}
