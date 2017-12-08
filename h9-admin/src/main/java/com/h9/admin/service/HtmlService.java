package com.h9.admin.service;

import com.h9.admin.model.dto.html.HtmlContentDTO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.entity.HtmlContent;
import com.h9.common.db.repo.HtmlContentRepository;
import com.h9.common.modle.dto.PageDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by Gonyb on 2017/11/9.
 */
@Service
public class HtmlService {

    @Resource
    private HtmlContentRepository htmlContentRepository;
    @Resource
    private ConfigService configService;
    
    public Result<PageResult<HtmlContent>> list(PageDTO pageDTO) {
        Page<HtmlContent> all = htmlContentRepository.findAll(pageDTO.toPageRequest());
        Map preLink = this.configService.getMapConfig(ParamConstant.PRELINK);
        all.forEach(item -> item.setUrl(preLink == null ? null : preLink.get(ParamConstant.PRELINK_SINGLE).toString()+item.getCode()));
        return Result.success(new PageResult<>(all));
    }

    public Result<HtmlContent> getHtml(Long id) {
        HtmlContent one = htmlContentRepository.findOne(id);
        if(one==null){
            return Result.fail("您要查看的网页不存在");
        }
        return Result.success(one);
    }

    public Result<HtmlContent> getHtmlByCode(String code) {
        HtmlContent one = htmlContentRepository.findByCode(code);
        if(one == null){
            return Result.fail("您要查看的网页不存在");
        }
        return Result.success(one);
    }

    public Result deleteHtml(Long id) {
        HtmlContent one = htmlContentRepository.findOne(id);
        if(one==null){
            return Result.fail("您要删除的网页不存在");
        }
        one.setStatus(2);
        one.setUpdateTime(new Date());
        htmlContentRepository.save(one);
        return Result.success();
    }

    public Result<HtmlContent> addHtml(HtmlContentDTO htmlContentDTO) {
        HtmlContent htmlContent = new HtmlContent();
        BeanUtils.copyProperties(htmlContentDTO,htmlContent);
        htmlContent.setId(null);
        htmlContent.setCreateTime(new Date());
        htmlContentRepository.save(htmlContent);
        return Result.success(htmlContent);
    }

    public Result<HtmlContent> editHtml(HtmlContentDTO htmlContentDTO) {
        HtmlContent htmlContent = htmlContentRepository.findOne(htmlContentDTO.getId());
        if(htmlContent==null){
            return Result.fail("您要修改的网页不存在");
        }
        BeanUtils.copyProperties(htmlContentDTO,htmlContent);
        htmlContent.setUpdateTime(new Date());
        htmlContentRepository.save(htmlContent);
        return Result.success(htmlContent);
    }
}
