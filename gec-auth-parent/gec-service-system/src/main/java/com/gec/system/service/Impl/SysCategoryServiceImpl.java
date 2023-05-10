package com.gec.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysCategory;
import com.gec.model.vo.SysCategoryQueryVo;
import com.gec.system.mapper.SysCategoryMapper;
import com.gec.system.service.SysCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-04-14
 */
@Service
public class SysCategoryServiceImpl extends ServiceImpl<SysCategoryMapper, SysCategory> implements SysCategoryService {

	@Override
	public IPage<SysCategory> selectCategoryByPage(IPage<SysCategory> sysCategoryPage, SysCategoryQueryVo sysCategoryQueryVo) {
		return this.baseMapper.selectCategoryByPage(sysCategoryPage,sysCategoryQueryVo);
	}
}
