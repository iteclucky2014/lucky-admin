package cn.sambo.difference.platform.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.jpa.impl.JPAQueryFactory;

import cn.sambo.difference.platform.common.ApiResult;
import cn.sambo.difference.platform.common.ApiResultBuilder;
import cn.sambo.difference.platform.common.ApiResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "differenceAnalysis", tags = "产销差分析")
@RestController
@RequestMapping(value = "differenceAnalysis")
@SuppressWarnings("unchecked")
public class DifferenceAnalysisController {

	@Autowired
	JPAQueryFactory queryFactory;
	
    // 全部评价
	@ApiOperation(value = "MNF", notes = "MNF")
    @GetMapping(value = "getMnf")
    public ApiResult<Map<String, Object>> getMnf() {

        Map<String, Object> mnfInfo = new HashMap<>();

        String[] xAxisData = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        String[] yAxisData = {"820", "932", "901", "934", "1290", "1330", "1320"};
        
        mnfInfo.put("xAxisData", xAxisData);
        mnfInfo.put("yAxisData", yAxisData);

        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("查询成功").data(mnfInfo).build();
		
	}

}
