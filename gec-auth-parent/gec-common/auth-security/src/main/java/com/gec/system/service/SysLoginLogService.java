package com.gec.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysLoginLog;
import com.gec.model.vo.SysLoginLogQueryVo;

/**
 * <p>
 * 系统访问记录 服务类
 * </p>
 *
 * @author admin
 * @since 2023-04-20
 */
public interface SysLoginLogService extends IService<SysLoginLog> {

//	IPage<SysLoginLog> findLogByPage(Long page, Long limit, SysLoginLogQueryVo sysLoginLogQueryVo);

	public void recordLoginLog(String username,Integer status,String ipaddr,String message);

	IPage<SysLoginLog> selectPage(Long page, Long limit, SysLoginLogQueryVo sysLoginLogQueryVo);
}
