package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.db.entity.Sketches;
import com.h9.common.db.repo.SketchesRepository;
import com.transfer.db.entity.Tasting;
import com.transfer.db.model.TastingVo;
import com.transfer.db.repo.TastingRepository;
import com.transfer.service.base.BaseService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * TastingService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/4
 * Time: 18:43
 */
@Component
public class TastingService extends BaseService<Tasting>{

    @Resource
    private TastingRepository tastingRepository;

    @Resource
    private SketchesRepository sketchesRepository;



    @Override
    public String getPath() {
        return null;
    }

    @Override
    public PageResult get(int page, int limit) {
        return tastingRepository.findAll(page,limit);
    }

    @Override
    public void getSql(Tasting tastingVo, BufferedWriter userWtriter) throws IOException {
        Sketches sketches = new Sketches();
        sketches.setActivityName(tastingVo.getPlanName());
        sketches.setProductName(tastingVo.getJPMC());
        sketches.setCode(tastingVo.getBouns().getCodeId());
        sketches.setMd5Code(tastingVo.getBouns().getCodemd());
        sketches.setUnit(tastingVo.getBZ());
        sketchesRepository.save(sketches);
    }

    @Override
    public String getTitle() {
        return "小品会数据迁移进度";
    }


}
