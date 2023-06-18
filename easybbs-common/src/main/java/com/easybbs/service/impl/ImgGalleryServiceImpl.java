package com.easybbs.service.impl;

import com.easybbs.entity.enums.PageSize;
import com.easybbs.entity.po.ImgGallery;
import com.easybbs.entity.query.ImgGalleryQuery;
import com.easybbs.entity.query.SimplePage;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.mappers.ImgGalleryMapper;
import com.easybbs.service.ImgGalleryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/24 11:58
 */
@Service("imgGalleryService")
public class ImgGalleryServiceImpl implements ImgGalleryService {

    @Resource
    private ImgGalleryMapper imgGalleryMapper;

    /**
     * 按条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<ImgGallery> findListByParam(ImgGalleryQuery param) {
        return this.imgGalleryMapper.selectList(param);
    }

    /**
     * 按条件查询总数
     *
     * @param param
     * @return
     */
    @Override
    public Integer findCountByParam(ImgGalleryQuery param) {
        return this.imgGalleryMapper.selectCount(param);
    }

    /**
     * 按条件分页查询
     *
     * @param param
     * @return
     */
    @Override
    public PaginationResultVO<ImgGallery> findListByPage(ImgGalleryQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<ImgGallery> list = this.findListByParam(param);
        PaginationResultVO<ImgGallery> result = new PaginationResultVO<>(count, page.getPageSize(), page.getPageNo(),
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
    public Integer add(ImgGallery bean) {
        return this.imgGalleryMapper.insert(bean);
    }

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public ImgGallery getImgGalleryById(Integer id) {
        return this.imgGalleryMapper.getImgGalleryById(id);
    }

    /**
     * 根据id修改
     *
     * @param bean
     * @param id
     * @return
     */
    @Override
    public Integer updateImgGalleryById(ImgGallery bean, Integer id) {
        return this.imgGalleryMapper.updateImgGalleryById(bean, id);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteImgGalleryById(Integer id) {
        return this.imgGalleryMapper.deleteImgGalleryById(id);
    }

    /**
     * 根据id更新分类
     *
     * @param id
     * @param cid
     * @param cname
     * @return
     */
    @Override
    public Integer updateImgGalleryCategoryById(Integer id, Integer cid, Integer cname) {
        return this.imgGalleryMapper.updateImgGalleryCategoryById(id, cid, cname);
    }

    /**
     * 根据id更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Integer updateImgGalleryStatusById(Integer id, Integer status) {
        return this.imgGalleryMapper.updateImgGalleryStatusById(id, status);
    }

    /**
     * 根据id更新信息状态
     *
     * @param id
     * @param status2
     * @return
     */
    @Override
    public Integer updateImgGalleryStatus2ById(Integer id, Integer status2) {
        return this.imgGalleryMapper.updateImgGalleryStatus2ById(id, status2);
    }

    /**
     * 根据id修改冻结状态
     *
     * @param id
     * @param status3
     * @return
     */
    @Override
    public Integer updateImgGalleryStatus3ById(Integer id, Integer status3) {
        return this.imgGalleryMapper.updateImgGalleryStatus3ById(id, status3);
    }
}
