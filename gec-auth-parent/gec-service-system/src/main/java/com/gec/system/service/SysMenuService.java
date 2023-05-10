package com.gec.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysMenu;
import com.gec.model.vo.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author admin
 * @since 2023-04-12
 */
public interface SysMenuService extends IService<SysMenu> {

	List<SysMenu> findNodes();

	Boolean updateStatus(Long id, Integer status);

	List<RouterVo> findMenuListByUserId(int userId);

	List<String> findButtonListByUserId(int userId);
}
