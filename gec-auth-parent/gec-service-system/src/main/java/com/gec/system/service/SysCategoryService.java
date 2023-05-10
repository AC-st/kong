package com.gec.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysCategory;
import com.gec.model.vo.SysCategoryQueryVo;
import com.gec.model.vo.SysUserQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-04-14
 */
public interface SysCategoryService extends IService<SysCategory> {

	IPage<SysCategory> selectCategoryByPage(IPage<SysCategory> sysCategoryPage, SysCategoryQueryVo sysCategoryQueryVo);
}
