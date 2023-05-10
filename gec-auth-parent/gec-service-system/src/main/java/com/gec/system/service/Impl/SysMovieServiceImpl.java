package com.gec.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysMovie;
import com.gec.model.vo.SysMovieQueryVo;
import com.gec.system.mapper.SysMovieMapper;
import com.gec.system.service.SysMovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-04-18
 */
@Service
public class SysMovieServiceImpl extends ServiceImpl<SysMovieMapper, SysMovie> implements SysMovieService {

	@Override
	public IPage<SysMovie> selectMovesByPage(IPage<SysMovie> sysMoviePage, SysMovieQueryVo sysMovieQueryVo) {
		return this.baseMapper.selectMovesByPage(sysMoviePage,sysMovieQueryVo);
	}

	@Override
	public List<SysMovie> findMovieByCateId(Integer id) {
		QueryWrapper<SysMovie> wrapper = new QueryWrapper<>();
		wrapper.eq("cid",id);
		List<SysMovie> list = this.baseMapper.selectList(wrapper);
		return list;
	}
}
