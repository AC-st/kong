package com.gec.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysCategory;
import com.gec.model.vo.SysCategoryQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2023-04-14
 */
public interface SysCategoryMapper extends BaseMapper<SysCategory> {

	IPage<SysCategory> selectCategoryByPage(IPage<SysCategory> sysCategoryPage, @Param("vo") SysCategoryQueryVo sysCategoryQueryVo);
}
