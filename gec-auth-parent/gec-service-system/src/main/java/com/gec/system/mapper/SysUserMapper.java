package com.gec.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.model.vo.SysUserQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2023-04-11
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

	IPage<SysUser> selectUserByPage(IPage<SysUser> sysUserPage, @Param("vo") SysUserQueryVo sysUserQueryVo);
}
