package com.gec.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysMovie;
import com.gec.model.system.SysUser;
import com.gec.model.vo.SysMovieQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-04-18
 */
public interface SysMovieService extends IService<SysMovie> {


	IPage<SysMovie> selectMovesByPage(IPage<SysMovie> sysMoviePage, SysMovieQueryVo sysMovieQueryVo);

	List<SysMovie> findMovieByCateId(Integer id);
}
