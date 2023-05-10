package com.gec.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysMovie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.model.vo.SysMovieQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2023-04-18
 */
public interface SysMovieMapper extends BaseMapper<SysMovie> {

	IPage<SysMovie> selectMovesByPage(IPage<SysMovie> sysMoviePage, @Param("vo") SysMovieQueryVo sysMovieQueryVo);
}
