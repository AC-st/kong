package com.gec.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.model.system.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2023-04-12
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	List<SysMenu> findMenuListByUserId(int userId);
}
