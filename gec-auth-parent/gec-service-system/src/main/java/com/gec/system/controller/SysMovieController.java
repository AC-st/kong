package com.gec.system.controller;

import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysMovie;
import com.gec.model.system.SysUser;
import com.gec.model.vo.SysMovieQueryVo;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.common.Result;
import com.gec.system.service.SysMovieService;
import com.gec.system.util.MD5Helper;
import com.gec.system.util.VodTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-04-18
 */
@RestController
@Api(tags = "影视分类")
@RequestMapping("/admin/system/sysMovie")
@CrossOrigin
public class SysMovieController {

	@Autowired
	private SysMovieService sysMovieService;
	@Autowired
	private VodTemplate vodTemplate;


	@ApiOperation("分页+条件查询")
	@GetMapping("/{page}/{limit}")
	public Result findMovieByPage(@PathVariable Long page, @PathVariable Long limit, SysMovieQueryVo sysMovieQueryVo){
		IPage<SysMovie> sysMoviePage= new Page<>(page,limit);
		sysMoviePage = this.sysMovieService.selectMovesByPage(sysMoviePage, sysMovieQueryVo);
		return Result.ok(sysMoviePage);
	}

	@ApiOperation("添加一个影视")
	@PostMapping("saveByMovie")
	public Result saveByMovie(@RequestBody SysMovie sysMovie){

		boolean save = this.sysMovieService.save(sysMovie);
		if (save)
		{
			return Result.ok();
		}
		else
		{
			return Result.fail();
		}
	}

	@ApiOperation("根据ID查询一个影视")
	@PostMapping("/findMovieById/{id}")
	public Result findSysRoleById(@PathVariable Long id){
		SysMovie result = this.sysMovieService.getById(id);
		return Result.ok(result);
	}

	@ApiOperation("根据ID删除影视")
	@DeleteMapping("removeMovieById/{id}")
	public Result removeRoleById(@PathVariable Long id){
		boolean b = this.sysMovieService.removeById(id);
		if(b){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	@ApiOperation("批量删除")
	@DeleteMapping("batchRemoveMovie")
	public Result batchRemove(@RequestBody List<Long> ids)
	{
		boolean isSuccess = this.sysMovieService.removeByIds(ids);
		if (isSuccess)
		{
			return Result.ok();
		}
		else
		{
			return Result.fail();
		}
	}


	@ApiOperation("根据ID修改影视")
	@PostMapping("/updateSysMovieById")
	public Result updateSysUserById(@RequestBody SysMovie sysMovie){
		boolean update = this.sysMovieService.updateById(sysMovie);
		if(update){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	@ApiOperation("查询全部影视")
	@GetMapping("/findMovieAll")
	public Result findMovieAll(){
		List<SysMovie> list = this.sysMovieService.list();
		return Result.ok(list);
	}

	@ApiOperation("根据分类ID查询影视资料")
	@PostMapping("/findMovieByCateId")
	public Result findMovieByCateId(@RequestBody Map<String, String> requestMap){
		String id = requestMap.get("id");
		List<SysMovie> list=this.sysMovieService.findMovieByCateId(Integer.parseInt(id));
		return Result.ok(list);
	}

	@ApiOperation("视频点播")
	@PostMapping("/getPlayAuth")
	public Result getPlayAuth(@RequestBody Map<String, String> requestMap) throws Exception {
		String id = requestMap.get("id");
		SysMovie sysMovie = this.sysMovieService.getById(Integer.parseInt(id));
		//2.根据movie获取播放id
		String playId = sysMovie.getPlayId();
		//3.获取 封面
		String image =  sysMovie.getImage();
		//4.根据播放id获取auth
//		String playAuth = this.vodTemplate.getVideoPlayAuth(playId).getPlayAuth();
		GetVideoPlayAuthResponse auth = this.vodTemplate.getVideoPlayAuth(playId);
		String playAuth = auth.getPlayAuth();
		Map<String,Object> map = new HashMap<>();
		map.put("image", image);
		map.put("playId",playId);
		map.put("playAuth",playAuth);
		return Result.ok(map);
	}
}

