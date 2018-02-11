package com.rejoice.active.console.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rejoice.active.console.common.bean.Result;
import com.rejoice.active.console.entity.RuleNumber;
import com.rejoice.active.console.service.RuleNumberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="排名模块 ")
@RequestMapping("/rule-number")
public class RuleNumberController extends BaseController<RuleNumber, RuleNumberService> {

	

	@GetMapping("/client/list-by-rank")
	@ApiOperation(value="排名赔率列表", notes="返回冠亚和的值+1~10名的车道")
	public Result<Map<Integer,List<RuleNumber>>> listByRank(){
		Map<Integer,List<RuleNumber>> data = new HashMap<>();
		RuleNumber ruleNumberCons = new RuleNumber();
		for(int i = 0; i <= 10; i++) {
			ruleNumberCons.setRank(i);
			data.put(i, this.getService().queryListByWhereOrderByClause(ruleNumberCons,"number asc"));
		}
		return Result.success(data);
	}
}
